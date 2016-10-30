package org.hmhb.exception;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link GlobalExceptionHandler}.
 */
public class GlobalExceptionHandlerTest {

    private static final String MESSAGE = "test-message";

    private HttpServletRequest request;
    private GlobalExceptionHandler toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        toTest = new GlobalExceptionHandler();
    }

    @ResponseStatus(
            code = HttpStatus.I_AM_A_TEAPOT
    )
    public static class DummyException extends RuntimeException {
        @Override
        public String getMessage() {
            return MESSAGE;
        }
    }

    @Test
    public void testDefaultHandler() throws Exception {
        JsonErrorResponse expectedBody = new JsonErrorResponse(
                HttpStatus.I_AM_A_TEAPOT.value(),
                MESSAGE,
                DummyException.class.getName()
        );

        /* Make the call. */
        ResponseEntity<JsonErrorResponse> actual = toTest.defaultHandler(
                request,
                new DummyException()
        );

        /* Verify the results. */
        assertEquals(expectedBody, actual.getBody());
        assertEquals(HttpStatus.I_AM_A_TEAPOT, actual.getStatusCode());
    }

}
