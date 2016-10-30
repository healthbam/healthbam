package org.hmhb.programarea;

import java.util.Collections;
import java.util.List;

import org.hmhb.exception.programarea.ProgramAreaNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultProgramAreaService}.
 */
public class DefaultProgramAreaServiceTest {

    private static final long ID = 123L;
    private static final String NAME = "test-name";

    private ProgramAreaDao dao;
    private DefaultProgramAreaService toTest;

    @Before
    public void setUp() throws Exception {
        dao = mock(ProgramAreaDao.class);
        toTest = new DefaultProgramAreaService(dao);
    }

    private ProgramArea createFilledInProgramArea() {
        ProgramArea programArea = new ProgramArea();

        programArea.setId(ID);
        programArea.setName(NAME);

        return programArea;
    }

    @Test
    public void testGetById() throws Exception {
        ProgramArea expected = createFilledInProgramArea();

        /* Train the mocks. */
        when(dao.findOne(ID)).thenReturn(expected);

        /* Make the call. */
        ProgramArea actual = toTest.getById(ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = ProgramAreaNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(ID);
    }

    @Test
    public void testGetAll() throws Exception {
        List<ProgramArea> expected = Collections.singletonList(createFilledInProgramArea());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<ProgramArea> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
