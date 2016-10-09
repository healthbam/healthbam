package org.hmhb.mapquery;

import java.util.ArrayList;
import java.util.List;

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

    private DefaultMapQueryService toTest;

    private ProgramService service;

    @Before
    public void setUp() throws Exception {
        service = mock(ProgramService.class);
        toTest = new DefaultMapQueryService(service);
    }

    @Test
    public void testSearch() throws Exception {

        MapQuery input;
        MapQuery expected;

        input = new MapQuery();
        input.setSearch(new MapQuerySearch());

        expected = new MapQuery();
        expected.setSearch(input.getSearch());

        List<Long> programIds = new ArrayList<>();
        programIds.add(1L);
        programIds.add(2L);

        List<Program> programs = new ArrayList<>();

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);
        result.setMapLayerUrl("https://hmhb.herokuapp.com/api/kml?programIds=1,2");

        expected.setResult(result);

        /* Train the mocks. */
        when(service.getByIds(programIds)).thenReturn(programs);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
