package org.hmhb.authorization;

import javax.servlet.http.HttpServletRequest;

import org.hmhb.user.HmhbUser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultAuthorizationService}.
 */
public class DefaultAuthorizationServiceTest {

    private HttpServletRequest request;

    private DefaultAuthorizationService toTest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        toTest = new DefaultAuthorizationService(request);
    }

    @Test
    public void testIsLoggedIn_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(request.getAttribute("loggedInUser")).thenReturn(null);

        /* Make the call. */
        boolean actual = toTest.isLoggedIn();

        /* Verify the results. */
        assertFalse(actual);
    }

    @Test
    public void testIsLoggedIn_LoggedIn() throws Exception {
        HmhbUser user = new HmhbUser();

        /* Train the mocks. */
        when(request.getAttribute("loggedInUser")).thenReturn(user);

        /* Make the call. */
        boolean actual = toTest.isLoggedIn();

        /* Verify the results. */
        assertTrue(actual);
    }

    @Test
    public void testIsAdmin_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(request.getAttribute("loggedInUser")).thenReturn(null);

        /* Make the call. */
        boolean actual = toTest.isAdmin();

        /* Verify the results. */
        assertFalse(actual);
    }

    @Test
    public void testIsAdmin_PartnerLoggedIn() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setAdmin(false);

        /* Train the mocks. */
        when(request.getAttribute("loggedInUser")).thenReturn(user);

        /* Make the call. */
        boolean actual = toTest.isAdmin();

        /* Verify the results. */
        assertFalse(actual);
    }

    @Test
    public void testIsAdmin_AdminLoggedIn() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setAdmin(true);

        /* Train the mocks. */
        when(request.getAttribute("loggedInUser")).thenReturn(user);

        /* Make the call. */
        boolean actual = toTest.isAdmin();

        /* Verify the results. */
        assertTrue(actual);
    }

}
