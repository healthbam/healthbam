package org.hmhb.program;

import java.util.Collections;
import java.util.List;

import org.hmhb.exception.program.ProgramIdMismatchException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ProgramController}.
 */
public class ProgramControllerTest {

    private ProgramController toTest;
    private ProgramService service;

    private Program program;

    @Before
    public void setUp() {
        service = mock(ProgramService.class);

        toTest = new ProgramController(service);

        program = new Program();
        program.setId(123L);
    }

    @Test
    public void testGetAll() {
        List<Program> expected = Collections.singletonList(program);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        long input = 123L;

        Program expected = program;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        Program actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        Program input = new Program();
        input.setName("some program");

        Program expected = program;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        Program actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Program input = new Program();
        input.setId(123L);
        input.setName("blah program");

        Program expected = program;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        Program actual = toTest.update(input.getId(), input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = ProgramIdMismatchException.class)
    public void testUpdate_IdsDoNotMatch() {
        Program input = new Program();
        input.setId(123L);
        input.setName("blah program");

        /* Make the call. */
        toTest.update(input.getId() + 1, input);
    }

    @Test
    public void testDelete() {
        long input = 123L;

        Program expected = program;

        /* Train the mocks. */
        when(service.delete(input)).thenReturn(expected);

        /* Make the call. */
        Program actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
