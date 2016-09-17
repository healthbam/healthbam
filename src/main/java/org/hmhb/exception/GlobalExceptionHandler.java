package org.hmhb.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<JsonErrorResponse> defaultHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("{}.defaultHandler called:", GlobalExceptionHandler.class.getSimpleName(), e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            status = responseStatus.code();
        }

        JsonErrorResponse errorResponse = new JsonErrorResponse();
        errorResponse.setStatusCode(status.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setType(e.getClass().getName());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

}
