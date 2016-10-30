package org.hmhb.exception;

/**
 * The top level exception for any known HMHB exception cases.
 */
public abstract class BaseException extends RuntimeException {

    /**
     * Default constructor.
     */
    public BaseException() {
        super();
    }

    /**
     * Constructs a {@link BaseException} with a message.
     *
     * @param message the exception message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Constructs a {@link BaseException} with a message and a cause.
     *
     * @param message the exception message
     * @param cause the exception cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@link BaseException} with a cause.
     *
     * @param cause the exception cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

}
