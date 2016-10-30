package org.hmhb.audit;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import org.hmhb.authorization.AuthorizationService;
import org.hmhb.user.HmhbUser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultAuditHelper}.
 */
public class DefaultAuditHelperTest {

    private AuthorizationService authorizationService;
    private HttpServletRequest request;
    private DefaultAuditHelper toTest;

    @Before
    public void setUp() throws Exception {
        authorizationService = mock(AuthorizationService.class);
        request = mock(HttpServletRequest.class);

        toTest = new DefaultAuditHelper(
                authorizationService,
                request
        );
    }

    @Test
    public void testGetCallerIp() throws Exception {
        String expected = "127.0.0.1";

        /* Train the mocks. */
        when(request.getRemoteAddr()).thenReturn(expected);

        /* Make the call. */
        String actual = toTest.getCallerIp();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentUserEmail() throws Exception {
        String expected = "someone@mailinator.com";

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setEmail(expected);

        /* Train the mocks. */
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        String actual = toTest.getCurrentUserEmail();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentUserEmail_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        String actual = toTest.getCurrentUserEmail();

        /* Verify the results. */
        assertEquals(null, actual);
    }

    @Test
    public void testGetCurrentTime() throws Exception {
        Date now = new Date();

        /* Make the call. */
        Date actual = toTest.getCurrentTime();

        /* Verify the results. */
        assertTrue(actual.getTime() >= now.getTime());
    }

}
