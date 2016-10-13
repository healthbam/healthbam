package org.hmhb.county;

import java.util.Collections;
import java.util.List;

import org.hmhb.exception.county.CountyNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultCountyService}.
 */
public class DefaultCountyServiceTest {

    private static final long ID = 123L;
    private static final String NAME = "test-name";
    private static final String STATE = "test-state";
    private static final String SHAPE = "test-shape";

    private CountyDao dao;
    private DefaultCountyService toTest;

    @Before
    public void setUp() throws Exception {
        dao = mock(CountyDao.class);
        toTest = new DefaultCountyService(dao);
    }

    private County createFilledInCounty() {
        County county = new County();

        county.setId(ID);
        county.setName(NAME);
        county.setState(STATE);
        county.setShape(SHAPE);

        return county;
    }

    @Test
    public void testGetById() throws Exception {
        County expected = createFilledInCounty();

        /* Train the mocks. */
        when(dao.findOne(ID)).thenReturn(expected);

        /* Make the call. */
        County actual = toTest.getById(ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = CountyNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(ID);
    }

    @Test
    public void testGetAll() throws Exception {
        List<County> expected = Collections.singletonList(createFilledInCounty());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<County> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByNameStartingWithIgnoreCase() throws Exception {
        List<County> expected = Collections.singletonList(createFilledInCounty());

        /* Train the mocks. */
        when(dao.findByNameStartingWithIgnoreCase(NAME)).thenReturn(expected);

        /* Make the call. */
        List<County> actual = toTest.findByNameStartingWithIgnoreCase(NAME);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
