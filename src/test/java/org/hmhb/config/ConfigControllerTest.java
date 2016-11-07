package org.hmhb.config;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

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
        int programStartYearMin = 1001;
        int programStreetAddressMaxLen = 1002;
        int programCityMaxLen = 1003;
        int programGoalMaxLen = 1004;
        int programOutcomeMaxLen = 1005;
        int programNameMaxLen = 1006;
        int programOtherExplanationMaxLen = 1007;
        int orgNameMaxLen = 1008;
        int urlMaxLen = 1009;
        int emailMaxLen = 1010;
        int phoneMaxLen = 1011;

        Environment environment = mock(Environment.class);

        /* Train the config. */
        when(environment.getProperty("google.oauth.client.id")).thenReturn(oauthClientId);
        when(environment.getProperty("hmhb.url.prefix")).thenReturn(urlPrefix);
        when(environment.getProperty("hmhb.program.startYear.minValue", Integer.class)).thenReturn(programStartYearMin);
        when(environment.getProperty("hmhb.program.streetAddress.maxLength", Integer.class))
                .thenReturn(programStreetAddressMaxLen);
        when(environment.getProperty("hmhb.program.city.maxLength", Integer.class)).thenReturn(programCityMaxLen);
        when(environment.getProperty("hmhb.program.goal.maxLength", Integer.class)).thenReturn(programGoalMaxLen);
        when(environment.getProperty("hmhb.program.outcome.maxLength", Integer.class)).thenReturn(programOutcomeMaxLen);
        when(environment.getProperty("hmhb.program.name.maxLength", Integer.class)).thenReturn(programNameMaxLen);
        when(environment.getProperty("hmhb.program.otherProgramArea.explanation.maxLength", Integer.class))
                .thenReturn(programOtherExplanationMaxLen);
        when(environment.getProperty("hmhb.organization.name.maxLength", Integer.class)).thenReturn(orgNameMaxLen);
        when(environment.getProperty("hmhb.url.maxLength", Integer.class)).thenReturn(urlMaxLen);
        when(environment.getProperty("hmhb.email.maxLength", Integer.class)).thenReturn(emailMaxLen);
        when(environment.getProperty("hmhb.phone.maxLength", Integer.class)).thenReturn(phoneMaxLen);

        PublicConfig publicConfig = new PublicConfig(environment);

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
        assertTrue(assertErrMsg, actual.contains("\"urlMaxLength\":" + urlMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"emailMaxLength\":" + emailMaxLen));
        assertTrue(assertErrMsg, actual.contains("\"phoneMaxLength\":" + phoneMaxLen));

        /* Verify the response wont be cached. */
        verify(response).setHeader("Cache-Control", "max-age=0, must-revalidate");
    }

}
