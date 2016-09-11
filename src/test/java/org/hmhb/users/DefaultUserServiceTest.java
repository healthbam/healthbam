package org.hmhb.users;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.exceptions.users.EmailTooLongException;
import org.hmhb.exceptions.users.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultUserServiceTest {

    private UserDao dao;
    private DefaultUserService toTest;

    private HmhbUser user;

    @Before
    public void setUp() throws Exception {
        dao = mock(UserDao.class);

        toTest = new DefaultUserService(dao);

        user = new HmhbUser();
        user.setId(123L);
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("john.smith@yahoo.com");
        user.setBirthDate(new Date(123L));
    }

    @Test
    public void testGetAll() throws Exception {
        List<HmhbUser> expected = Collections.singletonList(user);

        /* Train the mocks. */
        when(dao.findAll()).thenReturn(expected);

        /* Make the call. */
        List<HmhbUser> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() throws Exception {
        long input = 123L;

        HmhbUser expected = user;

        /* Train the mocks. */
        when(dao.findOne(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.getById(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        long input = 123L;

        /* Train the mocks. */
        when(dao.findOne(input)).thenReturn(null);

        /* Make the call. */
        toTest.getById(input);
    }

    @Test
    public void testDelete() throws Exception {
        long input = 123L;

        HmhbUser expected = user;

        /* Train the mocks. */
        when(dao.findOne(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.delete(input);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(dao).delete(input);
   }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        long input = 123L;

        /* Train the mocks. */
        when(dao.findOne(input)).thenReturn(null);

        /* Make the call. */
        toTest.delete(input);
   }

    @Test
    public void testSaveNew() throws Exception {
        HmhbUser input = user;
        input.setId(null);

        HmhbUser expected = user;

        /* Train the mocks. */
        when(dao.save(input)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
   }

    @Test(expected = EmailTooLongException.class)
    public void testSaveNewEmailTooLong() throws Exception {
        HmhbUser input = user;
        input.setId(null);
        input.setEmail("ToooooooooooooooooooooooooooooooooLooooooooooooooooooooongEmail@gggggggggggggggggggggggmaaaaaaaaaiiil.com");

        /* Make the call. */
        toTest.save(input);
   }

    @Test
    public void testSaveExisting() throws Exception {
        HmhbUser input = user;
        input.setId(123L);

        HmhbUser expected = user;

        /* Train the mocks. */
        when(dao.findOne(input.getId())).thenReturn(expected);
        when(dao.save(input)).thenReturn(expected);


        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
   }

    @Test(expected = UserNotFoundException.class)
    public void testSaveExistingNotFound() throws Exception {
        HmhbUser input = user;
        input.setId(123L);

        /* Train the mocks. */
        when(dao.findOne(input.getId())).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
   }

    @Test(expected = EmailTooLongException.class)
    public void testSaveExistingEmailTooLong() throws Exception {
        HmhbUser input = user;
        input.setId(123L);
        input.setEmail("ToooooooooooooooooooooooooooooooooLooooooooooooooooooooongEmail@gggggggggggggggggggggggmaaaaaaaaaiiil.com");

        /* Make the call. */
        toTest.save(input);
    }

}
