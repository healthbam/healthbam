package org.hmhb.config;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ConfigController}.
 */
public class ConfigControllerTest {

    private HttpServletResponse response;
    private ConfigService service;

    private ConfigController toTest;

    @Before
    public void setUp() throws Exception {
        response = mock(HttpServletResponse.class);
        service = mock(ConfigService.class);
        ObjectMapper mapper = new ObjectMapper();

        toTest = new ConfigController(
                response,
                service,
                mapper
        );
    }

    @Test
    public void testGetConfigJs() throws Exception {

        String oauthClientId = "test-oauth-client-id";
        String urlPrefix = "test-url-prefix";
        int programStartYearMin = 1111;
        int programStreetAddressMaxLen = 2222;
        int programCityMaxLen = 3333;
        int programGoalMaxLen = 4444;
        int programOutcomeMaxLen = 5555;
        int programNameMaxLen = 6666;
        int programOtherExplanationMaxLen = 7777;
        int orgNameMaxLen = 8888;

        PublicConfig publicConfig = new PublicConfig(
                oauthClientId,
                urlPrefix,
                programStartYearMin,
                programStreetAddressMaxLen,
                programCityMaxLen,
                programGoalMaxLen,
                programOutcomeMaxLen,
                programNameMaxLen,
                programOtherExplanationMaxLen,
                orgNameMaxLen
        );

        /* Train the mocks. */
        when(service.getPublicConfig()).thenReturn(publicConfig);

        /* Make the call. */
        String actual = toTest.getConfigJs();

        String assertErrMsg = "actual is: " + actual;

        /* Verify the results. */
        assertTrue(assertErrMsg, actual.contains("\"googleOauthClientId\":\"" + oauthClientId + "\""));
        assertTrue(assertErrMsg, actual.contains("\"urlPrefix\":\"" + urlPrefix + "\""));
        assertTrue(assertErrMsg, actual.contains("\"programStartYearMin\":" + programStartYearMin));
        assertTrue(assertErrMsg, actual.contains("\"programStreetAddressMaxLength\":" + programStreetAddressMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"programCityMaxLength\":" + programCityMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"programGoalMaxLength\":" + programGoalMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"programOutcomeMaxLength\":" + programOutcomeMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"programNameMaxLength\":" + programNameMaxLen));
        assertTrue(
                assertErrMsg,
                actual.contains("\"programAreaExplanationMaxLength\":" + programOtherExplanationMaxLen)
        );
        assertTrue(assertErrMsg, actual.contains("\"organizationNameMaxLength\":" + orgNameMaxLen));

        /* Verify the response wont be cached. */
        verify(response).setHeader("Cache-Control", "max-age=0, must-revalidate");
    }

}
