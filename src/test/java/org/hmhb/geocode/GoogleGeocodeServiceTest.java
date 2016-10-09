package org.hmhb.geocode;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.hmhb.program.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link GoogleGeocodeService}.
 */
public class GoogleGeocodeServiceTest {

    private GoogleGeocodeService toTest;

    private GoogleGeocodeClient googleGeocodeClient;

    @Before
    public void setUp() throws Exception {
        googleGeocodeClient = mock(GoogleGeocodeClient.class);
        toTest = new GoogleGeocodeService(googleGeocodeClient);
    }

    @Test
    public void testGetLngLat() throws Exception {
        Program program = new Program();
        program.setCity("Atlanta");
        program.setState("GA");
        program.setStreetAddress("123 Peachtree St.");
        program.setZipCode("30332");

        String expectedFormattedAddress = "123 Peachtree St. Atlanta, GA 30332";

        String expectedLngLat = "-1.23456789,9.87654321";

        LatLng latLng = new LatLng(9.87654321, -1.23456789);
        Geometry geometry = new Geometry();
        geometry.location = latLng;
        GeocodingResult geocodingResult = new GeocodingResult();
        geocodingResult.geometry = geometry;
        GeocodingResult[] expectedGeocodingResult = new GeocodingResult[] {geocodingResult};

        /* Train the mocks. */
        when(googleGeocodeClient.geocode(expectedFormattedAddress)).thenReturn(expectedGeocodingResult);

        /* Make the call. */
        String actual = toTest.getLngLat(program);

        /* Verify the results. */
        assertEquals(expectedLngLat, actual);
    }

}
