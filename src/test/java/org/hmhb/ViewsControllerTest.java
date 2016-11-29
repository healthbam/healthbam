package org.hmhb;

import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ViewsController}.
 */
public class ViewsControllerTest {

    private static final String EXPECTED_INDEX_PATH = "index";
    private static final String GOOGLE_MAPS_API_KEY = "test-google-maps-api-key";

    private Model model;
    private ViewsController toTest;

    @Before
    public void setUp() throws Exception {

        Environment environment = mock(Environment.class);
        when(environment.getProperty("google.geocode.client.id")).thenReturn(GOOGLE_MAPS_API_KEY);

        PublicConfig publicConfig = new PublicConfig(environment);

        ConfigService configService = mock(ConfigService.class);
        when(configService.getPublicConfig()).thenReturn(publicConfig);

        toTest = new ViewsController(configService);

        model = mock(Model.class);
    }

    @Test
    public void testHandleViewsSubpaths() throws Exception {
        String expected = EXPECTED_INDEX_PATH;

        /* Make the call. */
        String actual = toTest.handleViewsSubpaths(model);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(model).addAttribute("googleMapsApiKey", GOOGLE_MAPS_API_KEY);
    }

    @Test
    public void testHandleViews() throws Exception {
        String expected = EXPECTED_INDEX_PATH;

        /* Make the call. */
        String actual = toTest.handleViews(model);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(model).addAttribute("googleMapsApiKey", GOOGLE_MAPS_API_KEY);
    }

    @Test
    public void testHandleRoot() throws Exception {
        String expected = EXPECTED_INDEX_PATH;

        /* Make the call. */
        String actual = toTest.handleRoot(model);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(model).addAttribute("googleMapsApiKey", GOOGLE_MAPS_API_KEY);
    }

}
