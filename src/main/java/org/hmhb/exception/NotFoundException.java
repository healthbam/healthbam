package org.hmhb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to indicate that a resource wasn't found.
 *
 * Exceptions extending this will produce a 404 {@link HttpStatus#NOT_FOUND}.
 */
@ResponseStatus(
        code = HttpStatus.NOT_FOUND
)
public abstract class NotFoundException extends BaseException {

    /**
     * Default constructor.
     */
    public NotFoundException() {
        super();
    }

    /**
     * Constructs a {@link NotFoundException} with a message.
     *
     * @param message the exception message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a {@link NotFoundException} with a message and a cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@link NotFoundException} with a cause.
     *
     * @param cause the exception cause
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
