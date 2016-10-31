package org.hmhb.config;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultConfigService}.
 */
public class DefaultConfigServiceTest {

    private static final String OAUTH_ID = "test-oauth-client-id";
    private static final String OAUTH_SECRET = "test-oauth-client-secret";
    private static final String URL_PREFIX = "test-url-prefix";
    private static final String JWT_DOMAIN = "test-jwt-domain";
    private static final String JWT_SECRET = "test-jwt-secret";

    private DefaultConfigService toTest;

    @Before
    public void setUp() throws Exception {
        Environment environment = mock(Environment.class);

        /* Train the mocks. */
        when(environment.getProperty("google.oauth.client.id")).thenReturn(OAUTH_ID);
        when(environment.getProperty("google.oauth.client.secret")).thenReturn(OAUTH_SECRET);
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(URL_PREFIX);
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(JWT_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(JWT_SECRET);

        toTest = new DefaultConfigService(environment);
    }

    @Test
    public void testGetPublicConfig() throws Exception {
        PublicConfig expected = new PublicConfig(
                OAUTH_ID,
                URL_PREFIX
        );

        /* Make the call. */
        PublicConfig actual = toTest.getPublicConfig();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPrivateConfig() throws Exception {
        PrivateConfig expected = new PrivateConfig(
                OAUTH_SECRET,
                JWT_DOMAIN,
                JWT_SECRET
        );

        /* Make the call. */
        PrivateConfig actual = toTest.getPrivateConfig();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
