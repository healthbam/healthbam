package org.hmhb.exception.oauth;

import org.hmhb.exception.NotAuthorizedException;

/**
 * Exception for when something went wrong when calling into google for
 * information.
 */
public class GoogleOauthException extends NotAuthorizedException {

    /**
     * Constructs a {@link GoogleOauthException}.
     *
     * @param message the exception's message
     * @param causedBy the cause exception
     */
    public GoogleOauthException(
            String message,
            Throwable causedBy
    ) {
        super(message, causedBy);
    }

}
