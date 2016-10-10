package org.hmhb.exception.oauth;

import org.hmhb.exception.NotAuthorizedException;

/**
 * Exception for when something went wrong when calling into google for
 * information.
 */
public class GoogleOauthException extends NotAuthorizedException {

    public GoogleOauthException(
            String message,
            Throwable causedBy
    ) {
        super(message, causedBy);
    }

}
