package org.hmhb.kml;

import java.util.Arrays;

import org.hmhb.county.CountyService;
import org.hmhb.program.ProgramService;
import org.hmhb.url.UrlService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultKmlService}.
 */
public class DefaultKmlServiceTest {

    private CountyService countyService;
    private ProgramService programService;

    private DefaultKmlService toTest;

    @Before
    public void setUp() throws Exception {
        UrlService urlService = mock(UrlService.class);
        KmlPlacemarkService placemarkService = mock(KmlPlacemarkService.class);
        countyService = mock(CountyService.class);
        programService = mock(ProgramService.class);
        toTest = new DefaultKmlService(urlService, placemarkService, countyService, programService);
    }

    @Test
    public void testGetKml() throws Exception {
        /* Make the call. */
        assertNotNull(toTest.getKml("1,2,3"));

        /*
         * Verify the results.
         *
         * Due to the results being an xml string, I'm using Mockito's verify
         * (instead of trying to interpret or parse the xml string).
         */
        verify(programService).getByIds(Arrays.asList(1L, 2L, 3L));
        verify(countyService).getAll();
    }

    @Test(expected = NullPointerException.class)
    public void testGetKmlNull() throws Exception {
        toTest.getKml(null);
    }

    @Test
    public void testGetKmlEmptyString() throws Exception {
        /* Make the call. */
        assertNotNull(toTest.getKml(""));

        /*
         * Verify the results.
         *
         * Due to the results being an xml string, I'm using Mockito's verify
         * (instead of trying to interpret or parse the xml string).
         */
        verify(programService, never()).getByIds(any());
        verify(countyService).getAll();
    }

}
