package org.hmhb.user;

import java.util.Collections;
import java.util.List;

import org.hmhb.exception.user.UserIdMismatchException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserController}.
 */
public class UserControllerTest {

    private static final Long USER_ID = 123L;

    private UserService service;
    private UserController toTest;

    @Before
    public void setUp() throws Exception {
        service = mock(UserService.class);
        toTest = new UserController(service);
    }

    @Test
    public void testGetAll() throws Exception {
        List<HmhbUser> expected = Collections.singletonList(new HmhbUser());

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<HmhbUser> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() throws Exception {
        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(service.getById(USER_ID)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.getById(USER_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
   }

    @Test
    public void testUpdate() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.update(USER_ID, input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserIdMismatchException.class)
    public void testUpdate_IdMismatch() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);

        /* Make the call. */
        toTest.update(USER_ID + 1, input);
    }

    @Test
    public void testDelete() throws Exception {
        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(service.delete(USER_ID)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.delete(USER_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
