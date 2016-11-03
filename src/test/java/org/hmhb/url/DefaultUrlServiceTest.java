package org.hmhb.url;

import javax.servlet.http.HttpServletRequest;

import org.hmhb.config.ConfigService;
import org.hmhb.config.PrivateConfig;
import org.hmhb.config.PublicConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultUrlService}.
 */
public class DefaultUrlServiceTest {

    private HttpServletRequest request;
    private ConfigService configService;

    private DefaultUrlService toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        configService = mock(ConfigService.class);
        toTest = new DefaultUrlService(request, configService);
    }

    @Test
    public void testGetUrlPrefix() throws Exception {
        String expected = "https://hmhb.herokuapp.com:443";

        PublicConfig publicConfig = new PublicConfig("oauthClientId", null, 1, 2, 3, 4, 5);
        PrivateConfig privateConfig = new PrivateConfig("oauthClientSecret", "jwtDomain", "jwtSecret");

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);

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
    public void testGetUrlPrefixNoPortSpecified() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        PublicConfig publicConfig = new PublicConfig("oauthClientId", null, 1, 2, 3, 4, 5);
        PrivateConfig privateConfig = new PrivateConfig("oauthClientSecret", "jwtDomain", "jwtSecret");

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);

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
    public void testGetUrlPrefixConfigOverride() throws Exception {
        String expected = "https://hmhb.herokuapp.com";

        PublicConfig publicConfig = new PublicConfig("oauthClientId", expected, 1, 2, 3, 4, 5);
        PrivateConfig privateConfig = new PrivateConfig("oauthClientSecret", "jwtDomain", "jwtSecret");

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);

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
    public void testGetUrlPrefixConfigOverrideIsEmpty() throws Exception {
        String expected = "http://localhost:8080";

        PublicConfig publicConfig = new PublicConfig("oauthClientId", "", 1, 2, 3, 4, 5);
        PrivateConfig privateConfig = new PrivateConfig("oauthClientSecret", "jwtDomain", "jwtSecret");

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);

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
    public void testGetUrlPrefixConfigOverrideIsSpace() throws Exception {
        String expected = "http://localhost:8080";

        PublicConfig publicConfig = new PublicConfig("oauthClientId", "   ", 1, 2, 3, 4, 5);
        PrivateConfig privateConfig = new PrivateConfig("oauthClientSecret", "jwtDomain", "jwtSecret");

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);
        when(configService.getPrivateConfig()).thenReturn(privateConfig);

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
