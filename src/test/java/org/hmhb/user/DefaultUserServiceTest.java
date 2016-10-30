package org.hmhb.user;

import org.hmhb.exception.user.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultUserService}.
 */
public class DefaultUserServiceTest {

    private static final String EMAIL = "john.doe@mailinator.com";

    private UserDao dao;
    private DefaultUserService toTest;

    @Before
    public void setUp() throws Exception {
        dao = mock(UserDao.class);
        toTest = new DefaultUserService(dao);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        HmhbUser expected = new HmhbUser();
        expected.setEmail(EMAIL);

        /* Train the mocks. */
        when(dao.findByEmailIgnoreCase(EMAIL)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.getUserByEmail(EMAIL);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByEmail_NotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findByEmailIgnoreCase(EMAIL)).thenReturn(null);

        /* Make the call. */
        toTest.getUserByEmail(EMAIL);
    }

    @Test
    public void testProvisionNewUser() throws Exception {

    }

}
