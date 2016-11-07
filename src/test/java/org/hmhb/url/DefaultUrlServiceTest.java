package org.hmhb.url;

import javax.servlet.http.HttpServletRequest;

import org.hmhb.config.ConfigService;
import org.hmhb.config.PrivateConfig;
import org.hmhb.config.PublicConfig;
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
    private ConfigService configService;
    private Environment environment;

    private DefaultUrlService toTest;

    @Before
    public void setUp() throws Exception {
        environment = mock(Environment.class);
        request = mock(HttpServletRequest.class);
        configService = mock(ConfigService.class);
        toTest = new DefaultUrlService(request, configService);

        PublicConfig publicConfig = new PublicConfig(environment);
        PrivateConfig privateConfig = new PrivateConfig(environment);

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);
    }

    @Test
    public void testGetUrlPrefix() throws Exception {
        String expected = "https://hmhb.herokuapp.com:443";

        /* Train the config. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(null);

        /* Train the mocks. */
        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("hmhb.herokuapp.com");
        when(request.getServerPort()).thenReturn(443);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefix_NoPortSpecified() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        /* Train the config. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(null);

        /* Train the mocks. */
        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("hmhb.herokuapp.com");
        when(request.getServerPort()).thenReturn(-1);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefix_ConfigOverride() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        /* Train the config. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(expected);

        /* Train the mocks. */
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefix_ConfigOverrideIsEmpty() throws Exception {
        String expected = "http://localhost:8080";

        /* Train the config. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn("");

        /* Train the mocks. */
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlPrefix_ConfigOverrideIsSpace() throws Exception {
        String expected = "http://localhost:8080";

        /* Train the config. */
        when(environment.getProperty("hmhb.url.prefix")).thenReturn("   ");

        /* Train the mocks. */
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        /* Make the call. */
        String actual = toTest.getUrlPrefix();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
