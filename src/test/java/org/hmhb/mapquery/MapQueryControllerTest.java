package org.hmhb.mapquery;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MapQueryController}.
 */
public class MapQueryControllerTest {

    private MapQueryController toTest;
    private MapQueryService service;

    private MapQuery mapQuery;
    private MapQuery filledMapQuery;

    @Before
    public void setUp() throws Exception {

        service = mock(MapQueryService.class);

        toTest = new MapQueryController(service);

        mapQuery = new MapQuery();
        mapQuery.setSearch(new MapQuerySearch());

        filledMapQuery = new MapQuery();
        filledMapQuery.setSearch(new MapQuerySearch());
        filledMapQuery.setResult(new MapQueryResult());
    }

    @Test
    public void testSearch() throws Exception {
        MapQuery input = mapQuery;
        MapQuery expected = filledMapQuery;

        /* Train the mocks. */
        when(service.search(input)).thenReturn(expected);

        /* Make the call. */
        MapQuery actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
