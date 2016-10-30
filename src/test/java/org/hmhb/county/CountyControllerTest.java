package org.hmhb.county;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CountyController}.
 */
public class CountyControllerTest {

    private static final String NAME = "test-name";

    private CountyController toTest;
    private CountyService service;

    private County county;

    @Before
    public void setUp() throws Exception {
        service = mock(CountyService.class);

        toTest = new CountyController(service);

        county = new County();
        county.setId(123L);
    }

    @Test
    public void testGetById() throws Exception {
        long input = 123L;

        County expected = county;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        County actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws Exception {
        List<County> expected = Collections.singletonList(county);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<County> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchByName() throws Exception {
        List<County> expected = Collections.singletonList(county);

        /* Train the mocks. */
        when(service.searchByName(NAME)).thenReturn(expected);

        /* Make the call. */
        List<County> actual = toTest.searchByName(NAME);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
