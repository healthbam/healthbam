package org.hmhb.organization;

import java.util.List;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.exception.organization.CannotDeleteOrganizationWithProgramsException;
import org.hmhb.exception.organization.OnlyAdminCanDeleteOrgException;
import org.hmhb.exception.organization.OnlyAdminCanUpdateOrgException;
import org.hmhb.exception.organization.OrganizationNameIsTooLongException;
import org.hmhb.exception.organization.OrganizationNameRequiredException;
import org.hmhb.exception.organization.OrganizationNotFoundException;
import org.hmhb.exception.organization.OrganizationUrlIsTooLongException;
import org.hmhb.program.ProgramDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link OrganizationService}.
 */
@Service
public class DefaultOrganizationService implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrganizationService.class);

    private static final int URL_MAX_LEN = 2000;

    private final PublicConfig publicConfig;
    private final AuditHelper auditHelper;
    private final AuthorizationService authorizationService;
    private final OrganizationDao dao;
    private final ProgramDao programDao;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} to get public config from
     * @param auditHelper the {@link AuditHelper} to get audit information
     * @param authorizationService the {@link AuthorizationService} to verify a
     *                             user is allowed to do certain operations
     * @param programDao the {@link ProgramDao} to check if an
     *                   {@link Organization} has
     *                   {@link org.hmhb.program.Program}s and cannot be
     *                   deleted
     * @param dao the {@link OrganizationDao} for saving, deleting, and
     *            retrieving {@link Organization}s
     */
    @Autowired
    public DefaultOrganizationService(
            @Nonnull ConfigService configService,
            @Nonnull AuditHelper auditHelper,
            @Nonnull AuthorizationService authorizationService,
            /* I'm injecting ProgramDao instead of ProgramService to avoid a circular dependency. */
            @Nonnull ProgramDao programDao,
            @Nonnull OrganizationDao dao
    ) {
        LOGGER.debug("constructed");
        requireNonNull(configService, "configService cannot be null");
        this.publicConfig = configService.getPublicConfig();
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.authorizationService = requireNonNull(authorizationService, "authorizationService cannot be null");
        this.programDao = requireNonNull(programDao, "programDao cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public Organization getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        Organization result = dao.findOne(id);
        if (result == null) {
            throw new OrganizationNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<Organization> getAll() {
        LOGGER.debug("getAll called");
        return dao.findAllByOrderByNameAsc();
    }

    @Transactional
    @Timed
    @Override
    public Organization delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");

        if (!authorizationService.isAdmin()) {
            throw new OnlyAdminCanDeleteOrgException();
        }

        /* Verify it exists. */
        Organization organization = getById(id);

        if (programDao.findByOrganizationId(id).size() > 0) {
            throw new CannotDeleteOrganizationWithProgramsException(id);
        }

        dao.delete(id);

        return organization;
    }

    @Transactional
    @Timed
    @Override
    public Organization save(
            @Nonnull Organization organization
    ) {
        LOGGER.debug("save called: organization={}", organization);
        requireNonNull(organization, "organization cannot be null");

        if (StringUtils.isBlank(organization.getName())) {
            throw new OrganizationNameRequiredException();
        }

        if (organization.getName().length() > publicConfig.getOrganizationNameMaxLength()) {
            throw new OrganizationNameIsTooLongException();
        }

        if (organization.getFacebookUrl() != null && organization.getFacebookUrl().length() > URL_MAX_LEN) {
            throw new OrganizationUrlIsTooLongException();
        }

        if (organization.getWebsiteUrl() != null && organization.getWebsiteUrl().length() > URL_MAX_LEN) {
            throw new OrganizationUrlIsTooLongException();
        }

        if (organization.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            organization.setCreatedBy(auditHelper.getCurrentUserEmail());
            organization.setCreatedOn(auditHelper.getCurrentTime());
            organization.setUpdatedBy(null);
            organization.setUpdatedOn(null);
        } else {

            if (!authorizationService.isAdmin()) {
                throw new OnlyAdminCanUpdateOrgException();
            }

            /* Verify it exists and get the original createdBy and createdOn. */
            Organization organizationInDb = getById(organization.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            organization.setCreatedBy(organizationInDb.getCreatedBy());
            organization.setCreatedOn(organizationInDb.getCreatedOn());
            organization.setUpdatedBy(auditHelper.getCurrentUserEmail());
            organization.setUpdatedOn(auditHelper.getCurrentTime());
        }

        return dao.save(organization);
    }

}
