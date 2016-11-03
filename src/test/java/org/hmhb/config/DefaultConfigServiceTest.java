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
    private static final Integer PROG_START_YEAR_MIN = 1500;
    private static final Integer PROG_STREET_ADDR_MAX_LEN = 70;
    private static final Integer PROG_CITY_MAX_LEN = 50;
    private static final Integer PROG_GOAL_MAX_LEN = 500;
    private static final Integer PROG_OUTCOME_MAX_LEN = 500;

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
        when(environment.getProperty("hmhb.program.startYear.minValue", Integer.class)).thenReturn(PROG_START_YEAR_MIN);
        when(environment.getProperty("hmhb.program.streetAddress.maxLength", Integer.class)).thenReturn(PROG_STREET_ADDR_MAX_LEN);
        when(environment.getProperty("hmhb.program.city.maxLength", Integer.class)).thenReturn(PROG_CITY_MAX_LEN);
        when(environment.getProperty("hmhb.program.goal.maxLength", Integer.class)).thenReturn(PROG_GOAL_MAX_LEN);
        when(environment.getProperty("hmhb.program.outcome.maxLength", Integer.class)).thenReturn(PROG_OUTCOME_MAX_LEN);

        toTest = new DefaultConfigService(environment);
    }

    @Test
    public void testGetPublicConfig() throws Exception {
        PublicConfig expected = new PublicConfig(
                OAUTH_ID,
                URL_PREFIX,
                PROG_START_YEAR_MIN,
                PROG_STREET_ADDR_MAX_LEN,
                PROG_CITY_MAX_LEN,
                PROG_GOAL_MAX_LEN,
                PROG_OUTCOME_MAX_LEN
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
