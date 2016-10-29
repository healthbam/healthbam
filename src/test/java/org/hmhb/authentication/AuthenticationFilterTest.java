package org.hmhb.authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthenticationFilter}.
 */
public class AuthenticationFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private JwtAuthenticationService service;

    private AuthenticationFilter toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        service = mock(JwtAuthenticationService.class);
        toTest = new AuthenticationFilter(service);
    }

    @Test
    public void testDoFilter() throws Exception {
        /* Make the call. */
        toTest.doFilter(request, response, chain);

        /* Verify the results. */
        verify(service).validateAuthentication(request);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterAuthFailure() throws Exception {
        /* Train the mocks. */
        doThrow(new RuntimeException("test-auth-failure")).when(service).validateAuthentication(request);

        /* Make the call. */
        toTest.doFilter(request, response, chain);

        /* Verify the results. */
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
    }

}
