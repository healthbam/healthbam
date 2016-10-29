package org.hmhb.exception.authentication;

import java.util.Date;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that the JWT token is expired and should not be
 * honored.
 */
public class JwtTokenExpiredException extends NotAuthorizedException {

    public JwtTokenExpiredException(Date expiredOn) {
        super("JWT token is expired! expiredOn=" + expiredOn);
    }

}
