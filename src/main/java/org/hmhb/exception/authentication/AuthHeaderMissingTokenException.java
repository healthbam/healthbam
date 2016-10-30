package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception for an unknown authentication header due to not having a
 * token.  Our jwt authentication understands "Bearer token".
 */
public class AuthHeaderMissingTokenException extends NotAuthorizedException {

    /**
     * Constructs an {@link AuthHeaderMissingTokenException}.
     *
     * @param authHeader the invalid auth header
     */
    public AuthHeaderMissingTokenException(String authHeader) {
        super("Authorization header is missing token! " + authHeader);
    }

}
