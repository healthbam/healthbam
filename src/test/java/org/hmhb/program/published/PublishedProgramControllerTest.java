package org.hmhb.program.published;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PublishedProgramController}.
 */
public class PublishedProgramControllerTest {

    private PublishedProgramController toTest;
    private PublishedProgramService service;

    private PublishedProgram program;

    @Before
    public void setUp() {
        service = mock(PublishedProgramService.class);

        toTest = new PublishedProgramController(service);

        program = new PublishedProgram();
        program.setId(123L);
    }

    @Test
    public void testGetAll() {
        List<PublishedProgram> expected = Collections.singletonList(program);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<PublishedProgram> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        long input = 123L;

        PublishedProgram expected = program;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        PublishedProgram actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        PublishedProgram input = new PublishedProgram();
        input.setOrganizationName("some org");

        PublishedProgram expected = program;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        PublishedProgram actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        PublishedProgram input = new PublishedProgram();
        input.setId(123L);
        input.setOrganizationName("blah org");

        PublishedProgram expected = program;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        PublishedProgram actual = toTest.update(input.getId(), input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        long input = 123L;

        PublishedProgram expected = program;

        /* Train the mocks. */
        when(service.delete(input)).thenReturn(expected);

        /* Make the call. */
        PublishedProgram actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
