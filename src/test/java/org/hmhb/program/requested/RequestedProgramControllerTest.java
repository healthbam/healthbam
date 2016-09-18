package org.hmhb.program.requested;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RequestedProgramController}.
 */
public class RequestedProgramControllerTest {

    private RequestedProgramController toTest;
    private RequestedProgramService service;

    private RequestedProgram request;

    @Before
    public void setUp() {
        service = mock(RequestedProgramService.class);

        toTest = new RequestedProgramController(service);

        request = new RequestedProgram();
        request.setId(123L);
    }

    @Test
    public void testGetAll() {
        List<RequestedProgram> expected = Collections.singletonList(request);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<RequestedProgram> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        long input = 123L;

        RequestedProgram expected = request;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        RequestedProgram input = new RequestedProgram();
        input.setOrganizationName("some org");

        RequestedProgram expected = request;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        RequestedProgram input = new RequestedProgram();
        input.setId(123L);
        input.setOrganizationName("blah org");

        RequestedProgram expected = request;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.update(input.getId(), input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testPublish() {
        RequestedProgram input = request;

        /* Make the call. */
        toTest.publish(input.getId(), input);

        /* Verify the results. */
        verify(service).publish(input);
    }

    @Test
    public void testDelete() {
        long input = 123L;

        RequestedProgram expected = request;

        /* Train the mocks. */
        when(service.delete(input)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
