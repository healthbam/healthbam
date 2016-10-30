package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception for an unknown authentication header.  Our jwt authentication
 * understands "Bearer token".
 */
public class AuthHeaderUnknownException extends NotAuthorizedException {

    /**
     * Constructs an {@link AuthHeaderUnknownException}.
     * @param authHeader the invalid auth header
     */
    public AuthHeaderUnknownException(String authHeader) {
        super("Unknown authorization header! " + authHeader);
    }

}
