package org.hmhb.exception.authentication;

import java.util.Date;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that the JWT token is expired and should not be
 * honored.
 */
public class JwtTokenExpiredException extends NotAuthorizedException {

    /**
     * Constructs a {@link JwtTokenExpiredException}.
     *
     * @param expiredOn the {@link Date} the token expired on
     */
    public JwtTokenExpiredException(Date expiredOn) {
        super("JWT token is expired! expiredOn=" + expiredOn);
    }

}
