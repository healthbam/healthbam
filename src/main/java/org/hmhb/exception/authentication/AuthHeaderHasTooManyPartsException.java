package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception for an unknown authentication header due to having too many
 * tokens.  Our jwt authentication understands "Bearer token".
 */
public class AuthHeaderHasTooManyPartsException extends NotAuthorizedException {

    /**
     * Constructs an {@link AuthHeaderHasTooManyPartsException}.
     *
     * @param authHeader the invalid auth header
     */
    public AuthHeaderHasTooManyPartsException(String authHeader) {
        super("Invalid authorization header! " + authHeader);
    }

}
