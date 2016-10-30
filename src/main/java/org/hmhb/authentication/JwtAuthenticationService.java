package org.hmhb.authentication;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import org.hmhb.user.HmhbUser;

/**
 * Service to generate and validate our JWT tokens.
 */
public interface JwtAuthenticationService {

    /**
     * Generate a JWT token from the passed in {@link HmhbUser} info.
     *
     * @param user the {@link HmhbUser} info to put into a JWT token
     * @return the JWT token
     */
    String generateJwtToken(
            @Nonnull HmhbUser user
    );

    /**
     * Validate the JWT token in the request's Authorization header and put the
     * {@link HmhbUser}'s info in the {@link HttpServletRequest}.
     *
     * Noop if no auth header is present.
     *
     * @param request the {@link HttpServletRequest} to look for auth header
     *                and potentially put the {@link HmhbUser} into
     */
    void validateAuthentication(
            @Nonnull HttpServletRequest request
    );

}
