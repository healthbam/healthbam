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

    private Environment environment;

    private DefaultConfigService toTest;

    @Before
    public void setUp() throws Exception {
        environment = mock(Environment.class);

        toTest = new DefaultConfigService(environment);
    }

    @Test
    public void testGetPublicConfig() throws Exception {
        PublicConfig expected = new PublicConfig(environment);

        /* Make the call. */
        PublicConfig actual = toTest.getPublicConfig();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPrivateConfig() throws Exception {
        PrivateConfig expected = new PrivateConfig(environment);

        /* Make the call. */
        PrivateConfig actual = toTest.getPrivateConfig();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
