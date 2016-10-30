package org.hmhb.audit;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.user.HmhbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link AuditHelper}.
 */
@Service
public class DefaultAuditHelper implements AuditHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuditHelper.class);

    private final AuthorizationService authorizationService;
    private final HttpServletRequest request;

    /**
     * An injectable constructor.
     *
     * @param authorizationService the {@link AuthorizationService} to get the
     *                             logged in user
     * @param request the {@link HttpServletRequest} to get the ip address
     */
    @Autowired
    public DefaultAuditHelper(
            @Nonnull AuthorizationService authorizationService,
            @Nonnull HttpServletRequest request
    ) {
        LOGGER.debug("constructed");
        this.authorizationService = requireNonNull(authorizationService, "authorizationService cannot be null");
        this.request = requireNonNull(request, "request cannot be null");
    }

    @Timed
    @Override
    public String getCallerIp() {
        LOGGER.debug("getCallerIp called");
        return request.getRemoteAddr();
    }

    @Timed
    @Override
    public String getCurrentUserEmail() {
        LOGGER.debug("getCurrentUserEmail called");

        HmhbUser loggedInUser = authorizationService.getLoggedInUser();

        String email = null;

        if (loggedInUser != null) {
            email = loggedInUser.getEmail();
        }

        return email;
    }

    @Timed
    @Override
    public Date getCurrentTime() {
        LOGGER.debug("getCurrentTime called");
        return new Date();
    }

}
