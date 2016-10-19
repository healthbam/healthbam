package org.hmhb.mapquery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hmhb.county.County;
import org.hmhb.organization.Organization;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
import org.hmhb.url.UrlService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultMapQueryService}.
 */
public class DefaultMapQueryServiceTest {

    private static final Long COUNTY_ID = 999L;
    private static final Long PROGRAM_ID_1 = 1001L;
    private static final Long PROGRAM_ID_2 = 1002L;
    private static final Long ORG_ID = 2001L;
    private static final String URL_PREFIX = "http://localhost:8080";

    private DefaultMapQueryService toTest;

    private UrlService urlService;
    private ProgramService programService;

    @Before
    public void setUp() throws Exception {
        urlService = mock(UrlService.class);
        programService = mock(ProgramService.class);
        toTest = new DefaultMapQueryService(urlService, programService);
    }

    @Test
    public void testSearch_ResultsFound() throws Exception {
        Organization organization = new Organization();
        organization.setId(ORG_ID);

        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(organization);

        MapQuery input = new MapQuery();
        input.setSearch(search);

        MapQuery expected = new MapQuery();
        expected.setSearch(input.getSearch());

        Program program1 = new Program();
        program1.setId(PROGRAM_ID_1);

        Program program2 = new Program();
        program2.setId(PROGRAM_ID_2);

        List<Program> programs = Arrays.asList(
                program1,
                program2
        );

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);

        expected.setResult(result);

        /* Train the mocks. */
        when(programService.search(input)).thenReturn(programs);
        when(urlService.getUrlPrefix()).thenReturn(URL_PREFIX);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(
                input.getSearch(),
                actual.getSearch()
        );
        assertTrue(
                "Didn't start with expected: " + actual.getResult().getMapLayerUrl(),
                actual.getResult()
                        .getMapLayerUrl()
                        .startsWith(URL_PREFIX + "/api/kml?countyId=&programIds=1001,1002&time=")
        );
        assertEquals(
                programs,
                actual.getResult().getPrograms()
        );
    }

    @Test
    public void testSearch_NoResultsFound() throws Exception {
        Organization organization = new Organization();
        organization.setId(ORG_ID);

        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(organization);

        MapQuery input = new MapQuery();
        input.setSearch(search);

        MapQuery expected = new MapQuery();
        expected.setSearch(input.getSearch());

        List<Program> programs = Collections.emptyList();

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);

        expected.setResult(result);

        /* Train the mocks. */
        when(programService.search(input)).thenReturn(programs);
        when(urlService.getUrlPrefix()).thenReturn(URL_PREFIX);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(
                input.getSearch(),
                actual.getSearch()
        );
        assertTrue(
                "Didn't start with expected: " + actual.getResult().getMapLayerUrl(),
                actual.getResult()
                        .getMapLayerUrl()
                        .startsWith(URL_PREFIX + "/api/kml?countyId=&programIds=&time=")
        );
        assertEquals(
                programs,
                actual.getResult().getPrograms()
        );
    }

    @Test
    public void testSearch_SearchIsNull() throws Exception {
        MapQuery input = new MapQuery();

        MapQuery expected = new MapQuery();
        expected.setSearch(input.getSearch());

        List<Program> programs = Collections.emptyList();

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);

        expected.setResult(result);

        /* Train the mocks. */
        when(urlService.getUrlPrefix()).thenReturn(URL_PREFIX);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(
                input.getSearch(),
                actual.getSearch()
        );
        assertTrue(
                "Didn't start with expected: " + actual.getResult().getMapLayerUrl(),
                actual.getResult()
                        .getMapLayerUrl()
                        .startsWith(URL_PREFIX + "/api/kml?countyId=&programIds=&time=")
        );
        assertEquals(
                programs,
                actual.getResult().getPrograms()
        );
    }

    @Test
    public void testSearch_CountyIdSpecified() throws Exception {
        County county = new County();
        county.setId(COUNTY_ID);

        Organization organization = new Organization();
        organization.setId(ORG_ID);

        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(organization);
        search.setCounty(county);

        MapQuery input = new MapQuery();
        input.setSearch(search);

        MapQuery expected = new MapQuery();
        expected.setSearch(input.getSearch());

        List<Program> programs = Collections.emptyList();

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);

        expected.setResult(result);

        /* Train the mocks. */
        when(programService.search(input)).thenReturn(programs);
        when(urlService.getUrlPrefix()).thenReturn(URL_PREFIX);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(
                input.getSearch(),
                actual.getSearch()
        );
        assertTrue(
                "Didn't start with expected: " + actual.getResult().getMapLayerUrl(),
                actual.getResult()
                        .getMapLayerUrl()
                        .startsWith(URL_PREFIX + "/api/kml?countyId=999&programIds=&time=")
        );
        assertEquals(
                programs,
                actual.getResult().getPrograms()
        );
    }

}
