package org.hmhb.authorization;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.user.HmhbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link AuthorizationService}.
 */
@Service
public class DefaultAuthorizationService implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthorizationService.class);

    private final HttpServletRequest request;

    /**
     * An injectable constructor.
     *
     * @param request the {@link HttpServletRequest} to get the logged in user
     */
    @Autowired
    public DefaultAuthorizationService(
            @Nonnull HttpServletRequest request
    ) {
        this.request = requireNonNull(request, "request cannot be null");
    }

    @Timed
    @Override
    public HmhbUser getLoggedInUser() {
        LOGGER.debug("getLoggedInUser called");

        HmhbUser loggedInUser = (HmhbUser) request.getAttribute("loggedInUser");
        LOGGER.debug("currently logged in user: user={}", loggedInUser);

        return loggedInUser;
    }

    @Timed
    @Override
    public boolean isLoggedIn() {
        LOGGER.debug("isLoggedIn called");

        HmhbUser loggedInUser = getLoggedInUser();
        LOGGER.debug("currently logged in user: loggedInUser={}", loggedInUser);

        boolean isLoggedIn = loggedInUser != null;
        LOGGER.debug("isLoggedIn={}", isLoggedIn);

        return isLoggedIn;
    }

    @Timed
    @Override
    public boolean isAdmin() {
        LOGGER.debug("isAdmin called");

        HmhbUser loggedInUser = getLoggedInUser();
        boolean isAdmin = false;

        if (loggedInUser != null) {
            isAdmin = loggedInUser.isAdmin();
        }

        LOGGER.debug("currently logged in user: isAdmin={}", isAdmin);

        return isAdmin;
    }

}
