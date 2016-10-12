package org.hmhb.https;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PreferHttpsFilter}.
 */
public class PreferHttpsFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    private PreferHttpsFilter toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);

        toTest = new PreferHttpsFilter();
    }

    @Test
    public void testDoFilter_ServerRunningOnLocalhost() throws Exception {
        StringBuffer sb = new StringBuffer("http://localhost:8080/api/blah");

        /* Train the mocks. */
        when(request.getRequestURL()).thenReturn(sb);
        when(request.isSecure()).thenReturn(false);

        /* Make the call. */
        toTest.doFilter(request, response, chain);

        /* Verify the results. */
        verify(response, never()).sendRedirect(anyString());
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilter_ServerRunningOnHeroku() throws Exception {
        StringBuffer sb = new StringBuffer("http://hmhb.herokuapp.com/api/blah");

        /* Train the mocks. */
        when(request.getRequestURL()).thenReturn(sb);
        when(request.isSecure()).thenReturn(false);

        /* Make the call. */
        toTest.doFilter(request, response, chain);

        /* Verify the results. */
        verify(response).sendRedirect("https://hmhb.herokuapp.com/api/blah");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void testDoFilter_ServerRunningOnHerokuButAlreadyHttps() throws Exception {
        StringBuffer sb = new StringBuffer("https://localhost:8080/api/blah");

        /* Train the mocks. */
        when(request.getRequestURL()).thenReturn(sb);
        when(request.isSecure()).thenReturn(true);

        /* Make the call. */
        toTest.doFilter(request, response, chain);

        /* Verify the results. */
        verify(response, never()).sendRedirect(anyString());
        verify(chain).doFilter(request, response);
    }

}
