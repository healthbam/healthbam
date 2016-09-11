package org.hmhb.users;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController toTest;
    private UserService service;

    private HmhbUser user;

    @Before
    public void setUp() {
        service = mock(UserService.class);

        toTest = new UserController();
        toTest.setService(service);

        user = new HmhbUser();
        user.setId(123L);
        user.setEmail("john.doe@gmail.com");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setBirthDate(new Date(1234567890L));
    }

    @Test
    public void getAll() {
        List<HmhbUser> expected = Collections.singletonList(user);

        /* Train the mocks. */
        when(service.getAll()).thenReturn(expected);

        /* Make the call. */
        List<HmhbUser> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void getById() {
        long input = 123L;

        HmhbUser expected = user;

        /* Train the mocks. */
        when(service.getById(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void create() {
        HmhbUser input = new HmhbUser();
        input.setEmail("blah");

        HmhbUser expected = user;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.create(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        HmhbUser input = new HmhbUser();
        input.setId(123L);
        input.setEmail("blah");

        HmhbUser expected = user;

        /* Train the mocks. */
        when(service.save(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.update(input.getId(), input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        long input = 123L;

        HmhbUser expected = user;

        /* Train the mocks. */
        when(service.delete(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
