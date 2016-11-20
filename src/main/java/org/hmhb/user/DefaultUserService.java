package org.hmhb.user;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import com.google.api.services.plus.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.authentication.JwtAuthenticationService;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.csv.CsvService;
import org.hmhb.exception.user.UserCannotDeleteSuperAdminException;
import org.hmhb.exception.user.UserEmailRequiredException;
import org.hmhb.exception.user.UserEmailTooLongException;
import org.hmhb.exception.user.UserNonAdminCannotEscalateToAdminException;
import org.hmhb.exception.user.UserNotAllowedToAccessOtherProfileException;
import org.hmhb.exception.user.UserNotFoundException;
import org.hmhb.exception.user.UserSuperAdminCannotBeModifiedByOthers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link UserService}.
 */
@Service
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private final PublicConfig publicConfig;
    private final AuditHelper auditHelper;
    private final AuthorizationService authorizationService;
    private final HttpServletRequest request;
    private final JwtAuthenticationService jwtAuthService;
    private final CsvService csvService;
    private final UserDao dao;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} to get public config from
     * @param auditHelper the {@link AuditHelper} to get useful audit info
     * @param authorizationService the {@link AuthorizationService} to verify
     *                             that the logged in user is allowed to do
     *                             the operations attempted
     * @param request the {@link HttpServletRequest} so the JWT token can be
     *                validated for CSV download.
     * @param jwtAuthService the {@link JwtAuthenticationService} so the JWT
     *                       token can be validated for CSV download.
     * @param csvService the {@link CsvService} for exporting/importing to/from
     *                   CSV
     * @param dao the {@link UserDao} to save and retrieve {@link HmhbUser}s
     */
    @Autowired
    public DefaultUserService(
            @Nonnull ConfigService configService,
            @Nonnull AuditHelper auditHelper,
            @Nonnull AuthorizationService authorizationService,
            @Nonnull HttpServletRequest request,
            @Nonnull JwtAuthenticationService jwtAuthService,
            @Nonnull CsvService csvService,
            @Nonnull UserDao dao
    ) {
        requireNonNull(configService, "configService cannot be null");
        this.publicConfig = configService.getPublicConfig();
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.authorizationService = requireNonNull(authorizationService, "authorizationService cannot be null");
        this.request = requireNonNull(request, "request cannot be null");
        this.jwtAuthService = requireNonNull(jwtAuthService, "jwtAuthService cannot be null");
        this.csvService = requireNonNull(csvService, "csvService cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public HmhbUser saveWithGoogleData(
            @Nonnull String email,
            @Nonnull Person profile
    ) {
        LOGGER.debug("saveWithGoogleData called: email={}, profile={}", email, profile);
        requireNonNull(email, "email cannot be null");
        requireNonNull(profile, "profile cannot be null");

        /* This method isn't exposed and is only used by the system, so there isn't a need to check authorization. */

        HmhbUser user = dao.findByEmailIgnoreCase(email);

        if (user == null) {
            user = new HmhbUser();
            user.setSuperAdmin(false);
            user.setAdmin(false);
            user.setEmail(email);
            user.setCreatedBy("system-generated");
            user.setCreatedOn(auditHelper.getCurrentTime());
        } else {
            user.setUpdatedBy("system-updated-from-google-login");
            user.setUpdatedOn(auditHelper.getCurrentTime());
        }

        user.setProfileUrl(profile.getUrl());
        user.setDisplayName(profile.getDisplayName());
        user.setImageUrl(profile.getImage().getUrl());
        user.setLastName(profile.getName().getFamilyName());
        user.setFirstName(profile.getName().getGivenName());
        user.setMiddleName(profile.getName().getMiddleName());
        user.setPrefix(profile.getName().getHonorificPrefix());
        user.setSuffix(profile.getName().getHonorificSuffix());

        LOGGER.debug("updating user info: user={}", user);

        return dao.save(user);
    }

    /**
     * Checks whether the logged in user is an admin, or the logged in user is
     * trying to view / modify their own profile.
     *
     * @param userId the database ID of the {@link HmhbUser} that is going to
     *               be viewed / modified
     * @return true if the logged in user is allowed to view / change the
     * profile, false otherwise
     */
    private boolean isAllowed(Long userId) {
        HmhbUser currentUser = authorizationService.getLoggedInUser();

        /* Admins can see/edit all profiles and users can see/edit their own profile. */
        return authorizationService.isAdmin()
                || (currentUser != null && currentUser.getId().equals(userId));
    }

    @Timed
    @Override
    public HmhbUser getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");

        if (!isAllowed(id)) {
            throw new UserNotAllowedToAccessOtherProfileException();
        }

        HmhbUser result = dao.findOne(id);

        if (result == null) {
            throw new UserNotFoundException(id);
        }

        return result;
    }

    @Timed
    @Override
    public List<HmhbUser> getAll() {
        LOGGER.debug("getAll called");

        if (!authorizationService.isAdmin()) {
            throw new UserNotAllowedToAccessOtherProfileException();
        }

        return dao.findAllByOrderByDisplayNameAscEmailAsc();
    }

    @Timed
    @Override
    public String getAllAsCsv(
            @Nonnull String jwtToken
    ) {
        LOGGER.debug("getAllAsCsv called");

        /*
         * Since this was called with window.open, it doesn't have the auth
         * headers, so we must manually authenticate the user.
         */
        jwtAuthService.validateToken(request, jwtToken);

        return csvService.generateFromUsers(
                getAll()
        );
    }

    @Timed
    @Override
    public HmhbUser delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");

        if (!isAllowed(id)) {
            throw new UserNotAllowedToAccessOtherProfileException();
        }

        /* Verify the user exists. */
        HmhbUser user = getById(id);

        if (user.isSuperAdmin()) {
            throw new UserCannotDeleteSuperAdminException();
        }

        dao.delete(id);

        return user;
    }

    @Timed
    @Override
    public HmhbUser save(
            @Nonnull HmhbUser user
    ) {
        LOGGER.debug("save called: user={}", user);
        requireNonNull(user, "user cannot be null");

        if (StringUtils.isBlank(user.getEmail())) {
            throw new UserEmailRequiredException();
        }

        if (user.getEmail().length() > publicConfig.getEmailMaxLength()) {
            throw new UserEmailTooLongException();
        }

        HmhbUser userInDb = null;

        if (user.getId() != null) {
            /* Verify it exists and user has access. */
            userInDb = getById(user.getId());
        }

        if (userInDb == null) {

            if (!authorizationService.isAdmin()) {
                throw new UserNotAllowedToAccessOtherProfileException();
            }

            user.setCreatedBy(auditHelper.getCurrentUserEmail());
            user.setCreatedOn(auditHelper.getCurrentTime());
            user.setUpdatedBy(null);
            user.setUpdatedOn(null);

            /* I'm not letting anyone change super admin yet. */
            user.setSuperAdmin(false);
        } else {

            if (!authorizationService.isAdmin() && user.isAdmin()) {
                throw new UserNonAdminCannotEscalateToAdminException();
            }

            if (userInDb.isSuperAdmin()) {

                if (!authorizationService.getLoggedInUser().getId().equals(userInDb.getId())) {
                    throw new UserSuperAdminCannotBeModifiedByOthers();
                }

                /* super-admin implies admin */
                user.setAdmin(true);
            }

            user.setCreatedBy(userInDb.getCreatedBy());
            user.setCreatedOn(userInDb.getCreatedOn());
            user.setUpdatedBy(auditHelper.getCurrentUserEmail());
            user.setUpdatedOn(auditHelper.getCurrentTime());

            /* I'm not letting anyone change super admin yet. */
            user.setSuperAdmin(userInDb.isSuperAdmin());
        }

        return dao.save(user);
    }


}
