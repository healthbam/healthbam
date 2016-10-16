package org.hmhb.url;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultUrlService}.
 */
public class DefaultUrlServiceTest {

    private HttpServletRequest request;
    private Environment environment;

    private DefaultUrlService toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        environment = mock(Environment.class);
        toTest = new DefaultUrlService(request, environment);
    }

    @Test
    public void testGetUrlPrefix() throws Exception {
        String expected = "https://hmhb.herokuapp.com:443";

        /* Train the mocks. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(null);
        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("hmhb.herokuapp.com");
        when(request.getServerPort()).thenReturn(443);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefixNoPortSpecified() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        /* Train the mocks. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(null);
        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("hmhb.herokuapp.com");
        when(request.getServerPort()).thenReturn(-1);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefixConfigOverride() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        /* Train the mocks. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(expected);
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefixConfigOverrideIsEmpty() throws Exception {
        String expected = "http://localhost:8080";

        /* Train the mocks. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn("");
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefixConfigOverrideIsSpace() throws Exception {
        String expected = "http://localhost:8080";

        /* Train the mocks. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn("   ");
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
