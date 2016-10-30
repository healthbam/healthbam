package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that the JWT token wasn't signed and cannot be
 * trusted.
 */
public class JwtTokenNotSignedException extends NotAuthorizedException {

    /**
     * Constructs a {@link JwtTokenNotSignedException}.
     */
    public JwtTokenNotSignedException() {
        super("JWT is not signed, do not trust it!");
    }

}
