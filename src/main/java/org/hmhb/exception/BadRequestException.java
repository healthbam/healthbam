package org.hmhb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST
)
public abstract class BadRequestException extends BaseException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
