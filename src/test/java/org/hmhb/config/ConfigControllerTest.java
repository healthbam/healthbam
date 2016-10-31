package org.hmhb.config;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ConfigController}.
 */
public class ConfigControllerTest {

    private HttpServletResponse response;
    private ConfigService service;

    private ConfigController toTest;

    @Before
    public void setUp() throws Exception {
        response = mock(HttpServletResponse.class);
        service = mock(ConfigService.class);
        ObjectMapper mapper = new ObjectMapper();

        toTest = new ConfigController(
                response,
                service,
                mapper
        );
    }

    @Test
    public void testGetConfigJs() throws Exception {

        String oauthClientId = "test-oauth-client-id";
        String urlPrefix = "test-url-prefix";

        PublicConfig publicConfig = new PublicConfig(
                oauthClientId,
                urlPrefix
        );

        /* Train the mocks. */
        when(service.getPublicConfig()).thenReturn(publicConfig);

        /* Make the call. */
        String actual = toTest.getConfigJs();

        /* Verify the results. */
        assertTrue(actual.contains(oauthClientId));
        assertTrue(actual.contains(urlPrefix));

        /* Verify the response wont be cached. */
        verify(response).setHeader("Cache-Control", "max-age=0, must-revalidate");
    }

}
