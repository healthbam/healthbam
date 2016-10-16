package org.hmhb.mapquery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hmhb.organization.Organization;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultMapQueryService}.
 */
public class DefaultMapQueryServiceTest {

    private static final Long PROGRAM_ID_1 = 1001L;
    private static final Long PROGRAM_ID_2 = 1002L;
    private static final Long ORG_ID = 2001L;

    private DefaultMapQueryService toTest;

    private ProgramService service;

    @Before
    public void setUp() throws Exception {
        service = mock(ProgramService.class);
        toTest = new DefaultMapQueryService(service);
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
        when(service.search(input)).thenReturn(programs);

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
                        .startsWith("https://hmhb.herokuapp.com/api/kml?programIds=1001,1002&time=")
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
        when(service.search(input)).thenReturn(programs);

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
                        .startsWith("https://hmhb.herokuapp.com/api/kml?programIds=&time=")
        );
        assertEquals(
                programs,
                actual.getResult().getPrograms()
        );
    }

}
