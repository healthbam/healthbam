package org.hmhb.kml;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link KmlController}.
 */
public class KmlControllerTest {

    private KmlController toTest;

    private KmlService service;

    @Before
    public void setUp() throws Exception {
        service = mock(KmlService.class);
        toTest = new KmlController(service);
    }

    @Test
    public void testGetKml() throws Exception {
        String input = "1,2,3";
        String expected = "<kml></kml>";

        /* Train the mocks. */
        when(service.getKml(input)).thenReturn(expected);

        /* Make the call. */
        String actual = toTest.getKml(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
