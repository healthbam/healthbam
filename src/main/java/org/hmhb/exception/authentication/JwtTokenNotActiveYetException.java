package org.hmhb.exception.authentication;

import java.util.Date;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that a JWT token isn't valid yet (issuedAt) and
 * shouldn't be honored.
 */
public class JwtTokenNotActiveYetException extends NotAuthorizedException {

    public JwtTokenNotActiveYetException(Date issuedAt) {
        super("JWT token isn't active yet! issuedAt=" + issuedAt);
    }

}
