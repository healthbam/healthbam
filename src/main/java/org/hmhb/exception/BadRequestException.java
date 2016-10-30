package org.hmhb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to indicate that the request was invalid.
 *
 * Exceptions extending this will produce a 400 {@link HttpStatus#BAD_REQUEST}.
 */
@ResponseStatus(
        code = HttpStatus.BAD_REQUEST
)
public abstract class BadRequestException extends BaseException {

    /**
     * Default constructor.
     */
    public BadRequestException() {
        super();
    }

    /**
     * Constructs a {@link BadRequestException} with a message.
     *
     * @param message the exception message
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Constructs a {@link BadRequestException} with a message and a cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@link BadRequestException} with a cause.
     *
     * @param cause the exception cause
     */
    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
