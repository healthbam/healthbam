package org.hmhb.authentication;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.SignatureException;
import org.hmhb.exception.authentication.AuthHeaderHasTooManyPartsException;
import org.hmhb.exception.authentication.AuthHeaderMissingTokenException;
import org.hmhb.exception.authentication.AuthHeaderUnknownException;
import org.hmhb.user.HmhbUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultJwtAuthenticationService}.
 */
public class DefaultJwtAuthenticationServiceTest {

    private static final String TEST_DOMAIN = "test-domain";
    private static final String TEST_SECRET = "test-secret";
    private static final Long USER_ID = 123L;
    private static final String USER_EMAIL = "john.doe@mailinator.com";

    private HttpServletRequest request;
    private Environment environment;

    private DefaultJwtAuthenticationService toTest;

    @Before
    public void setUp() throws Exception {
        environment = mock(Environment.class);
        request = mock(HttpServletRequest.class);
        toTest = new DefaultJwtAuthenticationService(environment);
    }

    @Test
    public void testJwtToken() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setAdmin(false);

        /* Train the config. */
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(TEST_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(TEST_SECRET);

        /* Make the generate call. */
        String token = toTest.generateJwtToken(user);

        /* Train the mocks. */
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(TEST_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(TEST_SECRET);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        /* Make the validate call. */
        toTest.validateAuthentication(request);

        /* Verify the results. */
        verify(request).setAttribute("loggedInUser", user);
    }

    @Test(expected = SignatureException.class)
    public void testJwtToken_Tampered() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setAdmin(false);

        /* Train the config. */
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(TEST_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(TEST_SECRET);

        /* Make the generate call. */
        String token = toTest.generateJwtToken(user);

        /* Train the mocks. */
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token + "tampered");

        /* Make the validate call. */
        toTest.validateAuthentication(request);
    }

    @Test
    public void testJwtToken_NoAuthHeader() throws Exception {
        /* Train the mocks. */
        when(request.getHeader("Authorization")).thenReturn(null);

        /* Make the validate call. */
        toTest.validateAuthentication(request);

        /* Verify nothing was done. */
        verify(request, never()).setAttribute(anyString(), anyObject());
    }

    @Test(expected = AuthHeaderMissingTokenException.class)
    public void testJwtToken_AuthHeaderNotEnoughParts() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setAdmin(false);

        /* Train the mocks. */
        when(request.getHeader("Authorization")).thenReturn("Bearer");

        /* Make the validate call. */
        toTest.validateAuthentication(request);
    }

    @Test(expected = AuthHeaderHasTooManyPartsException.class)
    public void testJwtToken_AuthHeaderTooManyParts() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setAdmin(false);

        /* Train the config. */
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(TEST_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(TEST_SECRET);

        /* Make the generate call. */
        String token = toTest.generateJwtToken(user);

        /* Train the mocks. */
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token + " " + token);

        /* Make the validate call. */
        toTest.validateAuthentication(request);
    }

    @Test(expected = AuthHeaderUnknownException.class)
    public void testJwtToken_UnknownAuthHeader() throws Exception {
        HmhbUser user = new HmhbUser();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setAdmin(false);

        /* Train the config. */
        when(environment.getProperty("hmhb.jwt.domain")).thenReturn(TEST_DOMAIN);
        when(environment.getProperty("hmhb.jwt.secret")).thenReturn(TEST_SECRET);

        /* Make the generate call. */
        String token = toTest.generateJwtToken(user);

        /* Train the mocks. */
        when(request.getHeader("Authorization")).thenReturn("test-unknown-auth-header " + token);

        /* Make the validate call. */
        toTest.validateAuthentication(request);
    }

}
