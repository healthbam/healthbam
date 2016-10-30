package org.hmhb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to indicate that the requester isn't authorized for the
 * resource/operation that was requested.
 *
 * Exceptions extending this will produce a 401 {@link HttpStatus#UNAUTHORIZED}.
 */
@ResponseStatus(
        code = HttpStatus.UNAUTHORIZED
)
public abstract class NotAuthorizedException extends BaseException {

    /**
     * Default constructor.
     */
    public NotAuthorizedException() {
        super();
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a message.
     *
     * @param message the exception message
     */
    public NotAuthorizedException(String message) {
        super(message);
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a message and a cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a cause.
     *
     * @param cause the exception cause
     */
    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }

}
