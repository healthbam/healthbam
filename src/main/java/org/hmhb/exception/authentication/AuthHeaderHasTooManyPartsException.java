package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception for an unknown authentication header due to having too many
 * tokens.  Our jwt authentication understands "Bearer token".
 */
public class AuthHeaderHasTooManyPartsException extends NotAuthorizedException {

    public AuthHeaderHasTooManyPartsException(String authHeader) {
        super("Invalid authorization header! " + authHeader);
    }

}
