package org.hmhb.programarea;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link ProgramAreaController}.
 */
public class ProgramAreaControllerTest {

    private ProgramAreaController toTest;
    private ProgramAreaService service;

    private ProgramArea programArea;

    @Before
    public void setUp() throws Exception {
        service = mock(ProgramAreaService.class);

        toTest = new ProgramAreaController(service);

        programArea = new ProgramArea();
        programArea.setId(123L);
        programArea.setName("test-name");
    }

    @Test
    public void testGetById() throws Exception {
        long input = 123L;

        ProgramArea expected = programArea;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        ProgramArea actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws Exception {
        List<ProgramArea> expected = Collections.singletonList(programArea);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<ProgramArea> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
