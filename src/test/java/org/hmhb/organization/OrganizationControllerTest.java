package org.hmhb.organization;

import java.util.Collections;
import java.util.List;

import org.hmhb.exception.organization.OrganizationIdMismatchException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OrganizationController}.
 */
public class OrganizationControllerTest {

    private OrganizationController toTest;
    private OrganizationService service;

    private Organization organization;

    @Before
    public void setUp() {
        service = mock(OrganizationService.class);

        toTest = new OrganizationController(service);

        organization = new Organization();
        organization.setId(123L);
    }

    @Test
    public void testGetAll() {
        List<Organization> expected = Collections.singletonList(organization);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<Organization> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        long input = 123L;

        Organization expected = organization;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        Organization actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        Organization input = new Organization();
        input.setName("some org");

        Organization expected = organization;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        Organization actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Organization input = new Organization();
        input.setId(123L);
        input.setName("blah org");

        Organization expected = organization;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        Organization actual = toTest.update(input.getId(), input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = OrganizationIdMismatchException.class)
    public void testUpdate_IdsDoNotMatch() {
        Organization input = new Organization();
        input.setId(123L);
        input.setName("blah org");

        /* Make the call. */
        toTest.update(input.getId() + 1, input);
    }

    @Test
    public void testDelete() {
        long input = 123L;

        Organization expected = organization;

        /* Train the mocks. */
        when(service.delete(input)).thenReturn(expected);

        /* Make the call. */
        Organization actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
