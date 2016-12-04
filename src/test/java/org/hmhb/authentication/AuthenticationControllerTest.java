package org.hmhb.authentication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthenticationController}.
 */
public class AuthenticationControllerTest {

    private AuthenticationService service;

    private AuthenticationController toTest;

    @Before
    public void setUp() throws Exception {
        service = mock(AuthenticationService.class);
        toTest = new AuthenticationController(service);
    }

    @Test
    public void testAuthenticate() throws Exception {
        GoogleOauthAccessRequestInfo input = new GoogleOauthAccessRequestInfo();
        input.setClientId("test-clientId");

        TokenResponse expected = new TokenResponse("test-token");

        /* Train the mocks. */
        when(service.authenticate(input)).thenReturn(expected);

        /* Make the call. */
        TokenResponse actual = toTest.authenticate(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
