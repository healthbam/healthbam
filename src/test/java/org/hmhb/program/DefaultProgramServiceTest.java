package org.hmhb.program;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.county.County;
import org.hmhb.csv.CsvService;
import org.hmhb.exception.program.OnlyAdminCanDeleteProgramException;
import org.hmhb.exception.program.OnlyAdminCanSaveProgramException;
import org.hmhb.exception.program.ProgramCityNameIsTooLongException;
import org.hmhb.exception.program.ProgramCityRequiredException;
import org.hmhb.exception.program.ProgramGoalIsTooLongException;
import org.hmhb.exception.program.ProgramMeasurableOutcome1RequiredException;
import org.hmhb.exception.program.ProgramNameIsTooLongException;
import org.hmhb.exception.program.ProgramNameRequiredException;
import org.hmhb.exception.program.ProgramNotFoundException;
import org.hmhb.exception.program.ProgramOrganizationRequiredException;
import org.hmhb.exception.program.ProgramOtherExplanationIsTooLongException;
import org.hmhb.exception.program.ProgramOutcomeIsTooLongException;
import org.hmhb.exception.program.ProgramPrimaryGoal1RequiredException;
import org.hmhb.exception.program.ProgramStartYearIsTooOldException;
import org.hmhb.exception.program.ProgramStateIsInvalidException;
import org.hmhb.exception.program.ProgramStateRequiredException;
import org.hmhb.exception.program.ProgramStreetAddressIsTooLongException;
import org.hmhb.exception.program.ProgramStreetAddressRequiredException;
import org.hmhb.exception.program.ProgramZipCodeIsInvalidException;
import org.hmhb.exception.program.ProgramZipCodeRequiredException;
import org.hmhb.geocode.GeocodeService;
import org.hmhb.geocode.LocationInfo;
import org.hmhb.mapquery.MapQuery;
import org.hmhb.mapquery.MapQuerySearch;
import org.hmhb.organization.Organization;
import org.hmhb.organization.OrganizationService;
import org.hmhb.programarea.ProgramArea;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultProgramService}.
 */
public class DefaultProgramServiceTest {

    private static final long PROGRAM_ID = 123L;
    private static final long ORG_ID = 456L;
    private static final long COUNTY_ID = 789L;
    private static final long PROGRAM_AREA_ID = 999L;
    private static final Date CREATED_ON = new Date(123456L);
    private static final String USERNAME_1 = "somebody@mailinator.com";
    private static final String USERNAME_2 = "somebody-else@mailinator.com";
    private static final Date UPDATED_ON = new Date(654321L);
    private static final String PROGRAM_NAME = "test-program-name";
    private static final String ORG_NAME = "test-org-name";
    private static final String COUNTY_NAME = "test-county-name";
    private static final String PROGRAM_AREA_NAME = "test-program-area-name";
    private static final int START_YEAR = 1999;
    private static final String STREET_ADDRESS = "test-street-address";
    private static final String CITY = "test-city";
    private static final String STATE = "GA";
    private static final String ZIP_CODE = "12345";
    private static final String PRIMARY_GOAL = "test-primary-goal-1";
    private static final String MEASURABLE_OUTCOME = "test-measurable-outcome-1";
    private static final String GEO_CODE = "-1.00000000,1.00000000";

    private static final int MIN_VALUE = 1500;
    private static final int TOO_OLD = 1;
    private static final int MAX_LEN = 50;
    private static final String TOO_LONG = "1234567890-1234567890-1234567890-1234567890-1234567890";

    private AuditHelper auditHelper;
    private AuthorizationService authorizationService;
    private CsvService csvService;
    private GeocodeService geocodeService;
    private OrganizationService organizationService;
    private ProgramDao dao;
    private DefaultProgramService toTest;

    @Before
    public void setUp() throws Exception {
        ConfigService configService = mock(ConfigService.class);
        auditHelper = mock(AuditHelper.class);
        authorizationService = mock(AuthorizationService.class);
        csvService = mock(CsvService.class);
        geocodeService = mock(GeocodeService.class);
        organizationService = mock(OrganizationService.class);
        dao = mock(ProgramDao.class);

        Environment environment = mock(Environment.class);

        when(environment.getProperty("hmhb.program.startYear.minValue", Integer.class)).thenReturn(MIN_VALUE);
        when(environment.getProperty("hmhb.program.streetAddress.maxLength", Integer.class))
                .thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.program.city.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.program.goal.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.program.outcome.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.program.name.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.program.otherProgramArea.explanation.maxLength", Integer.class))
                .thenReturn(MAX_LEN);

        PublicConfig publicConfig = new PublicConfig(environment);

        /* Train the config. */
        when(configService.getPublicConfig()).thenReturn(publicConfig);

        toTest = new DefaultProgramService(
                configService,
                auditHelper,
                authorizationService,
                csvService,
                geocodeService,
                organizationService,
                dao
        );
    }

    private LocationInfo createLocationInfo() {
        LocationInfo info = new LocationInfo();

        info.setLngLat(GEO_CODE);
        info.setZipCode(ZIP_CODE);

        return info;
    }

    private County createCounty() {
        County county = new County();

        county.setId(COUNTY_ID);
        county.setName(COUNTY_NAME);

        return county;
    }

    private ProgramArea createProgramArea() {
        ProgramArea programArea = new ProgramArea();

        programArea.setId(PROGRAM_AREA_ID);
        programArea.setName(PROGRAM_AREA_NAME);

        return programArea;
    }

    private Organization createOrg() {
        Organization organization = new Organization();

        organization.setId(ORG_ID);
        organization.setName(ORG_NAME);

        return organization;
    }

    private Program createFilledInProgram() {
        Program program = new Program();

        program.setId(PROGRAM_ID);
        program.setName(PROGRAM_NAME);
        program.setOrganization(createOrg());
        program.setStartYear(START_YEAR);
        program.setStreetAddress(STREET_ADDRESS);
        program.setCity(CITY);
        program.setState(STATE);
        program.setZipCode(ZIP_CODE);
        program.setPrimaryGoal1(PRIMARY_GOAL);
        program.setMeasurableOutcome1(MEASURABLE_OUTCOME);
        program.setCountiesServed(Collections.singleton(createCounty()));
        program.setProgramAreas(Collections.singleton(createProgramArea()));

        return program;
    }

    @Test
    public void testGetById() throws Exception {
        Program expected = createFilledInProgram();

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(expected);

        /* Make the call. */
        Program actual = toTest.getById(PROGRAM_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testGetById_Null() throws Exception {
        /* Make the call. */
        toTest.getById(null);
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testGetById_NotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(PROGRAM_ID);
    }

    @Test
    public void testGetByIds() throws Exception {
        List<Long> input = Collections.singletonList(PROGRAM_ID);

        Program program = createFilledInProgram();
        program.setId(PROGRAM_ID);

        List<Program> expected = Collections.singletonList(program);

        /* Train the mocks. */
        when(dao.findAll(input)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.getByIds(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testGetByIds_Null() throws Exception {
        /* Make the call. */
        toTest.getByIds(null);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllAsCsv() throws Exception {
        List<Program> programsFound = Collections.singletonList(createFilledInProgram());

        String expected = "a,b,c\n1,2,3\n";

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(programsFound);
        when(csvService.generateFromPrograms(programsFound, true, true)).thenReturn(expected);

        /* Make the call. */
        String actual = toTest.getAllAsCsv(true, true);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws Exception {
        Program programInDb = createFilledInProgram();

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(PROGRAM_ID)).thenReturn(programInDb);

        /* Make the call. */
        Program actual = toTest.delete(PROGRAM_ID);

        /* Verify the results. */
        assertEquals(programInDb, actual);
        verify(dao).delete(PROGRAM_ID);
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testDelete_NotFound() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(PROGRAM_ID);
    }

    @Test(expected = OnlyAdminCanDeleteProgramException.class)
    public void testDelete_NotAdmin() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);

        /* Make the call. */
        toTest.delete(PROGRAM_ID);
    }

    @Test(expected = ProgramZipCodeIsInvalidException.class)
    public void testSaveCreateNew_ZipCodeIsInvalid() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("abc");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_ValidZipCode_Normal() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("12345");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* The call should not fail. */
    }

    @Test
    public void testSaveCreateNew_ValidZipCode_ExtendedWithSpace() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("12345 1234");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* The call should not fail. */
    }

    @Test
    public void testSaveCreateNew_ValidZipCode_ExtendedWithHyphen() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("12345-1234");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* The call should not fail. */
    }

    @Test(expected = ProgramStartYearIsTooOldException.class)
    public void testSaveCreateNew_StartYearTooOld() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStartYear(TOO_OLD);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameIsTooLongException.class)
    public void testSaveCreateNew_NameTooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOtherExplanationIsTooLongException.class)
    public void testSaveCreateNew_OtherExplanationTooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setOtherProgramAreaExplanation(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStreetAddressIsTooLongException.class)
    public void testSaveCreateNew_StreetAddressTooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStreetAddress(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramCityNameIsTooLongException.class)
    public void testSaveCreateNew_CityNameTooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setCity(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramGoalIsTooLongException.class)
    public void testSaveCreateNew_Goal1TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal1(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramGoalIsTooLongException.class)
    public void testSaveCreateNew_Goal2TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal2(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramGoalIsTooLongException.class)
    public void testSaveCreateNew_Goal3TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal3(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOutcomeIsTooLongException.class)
    public void testSaveCreateNew_Outcome1TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome1(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOutcomeIsTooLongException.class)
    public void testSaveCreateNew_Outcome2TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome2(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOutcomeIsTooLongException.class)
    public void testSaveCreateNew_Outcome3TooLong() {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome3(TOO_LONG);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramCityRequiredException.class)
    public void testSaveCreateNew_NullCity() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setCity(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramCityRequiredException.class)
    public void testSaveCreateNew_EmptyCity() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setCity("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramCityRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceCity() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setCity(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNew_NullName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNew_EmptyName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramPrimaryGoal1RequiredException.class)
    public void testSaveCreateNew_NullGoal() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal1(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramPrimaryGoal1RequiredException.class)
    public void testSaveCreateNew_EmptyGoal() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal1("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramPrimaryGoal1RequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceGoal() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setPrimaryGoal1(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramMeasurableOutcome1RequiredException.class)
    public void testSaveCreateNew_NullOutcome() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome1(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramMeasurableOutcome1RequiredException.class)
    public void testSaveCreateNew_EmptyOutcome() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome1("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramMeasurableOutcome1RequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceOutcome() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setMeasurableOutcome1(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStreetAddressRequiredException.class)
    public void testSaveCreateNew_NullStreetAddress() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStreetAddress(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStreetAddressRequiredException.class)
    public void testSaveCreateNew_EmptyStreetAddress() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStreetAddress("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStreetAddressRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceStreetAddress() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStreetAddress(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOrganizationRequiredException.class)
    public void testSaveCreateNew_NullOrg() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setOrganization(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNew_NullState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateIsInvalidException.class)
    public void testSaveCreateNew_InvalidState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState("invalid");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNew_EmptyState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_NullZipCodeButGoogleFoundIt() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());

        /* Make the call. */
        toTest.save(input);

        /* Should not blow up because google found the zipcode for us. */
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNew_NullZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(null);

        LocationInfo locationInfo = createLocationInfo();
        locationInfo.setZipCode(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(locationInfo);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_EmptyZipCodeButGoogleFoundIt() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());

        /* Make the call. */
        toTest.save(input);

        /* Should not blow up because google found the zipcode for us. */
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNew_EmptyZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("");

        LocationInfo locationInfo = createLocationInfo();
        locationInfo.setZipCode(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(locationInfo);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_OnlyWhitespaceZipCodeButGoogleFoundIt() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(" ");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());

        /* Make the call. */
        toTest.save(input);

        /* Should not blow up because google found the zipcode for us. */
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(" ");

        LocationInfo locationInfo = createLocationInfo();
        locationInfo.setZipCode(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(locationInfo);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_NullStartYear() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStartYear(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());

        /* Make the call. */
        toTest.save(input);

        /* Should not blow up.  After receiving some customer data, we discovered that this should be allowed. */
    }

    @Test
    public void testSaveCreateNew() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(PROGRAM_NAME);
        /*
         * I'm setting these to make sure the latest org changes go out
         * with the program and lessening the chance of cascade update
         * if someone refactors.
         */
        input.getOrganization().setName("old-" + ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));
        /* They also can't set the lat-long (google maps api will give us that. */
        input.setCoordinates("-5.00000,5.00000");

        Program inputWithCreatedAuditFilledIn = createFilledInProgram();
        inputWithCreatedAuditFilledIn.setId(null);
        inputWithCreatedAuditFilledIn.setName(PROGRAM_NAME);
        /* They can't set these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithCreatedAuditFilledIn.setCoordinates(GEO_CODE);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(inputWithCreatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithCreatedAuditFilledIn, actual);
    }

    @Test(expected = OnlyAdminCanSaveProgramException.class)
    public void testSaveCreateNew_NotAdmin() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(PROGRAM_NAME);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_ProgramWithNewOrg() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(PROGRAM_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));
        /* They also can't set the lat-long (google maps api will give us that. */
        input.setCoordinates("-5.00000,5.00000");

        Organization inputOrg = createOrg();
        inputOrg.setId(null); /* This will be a new org. */

        input.setOrganization(inputOrg);

        Program inputWithCreatedAuditFilledIn = createFilledInProgram();
        inputWithCreatedAuditFilledIn.setOrganization(createOrg());
        inputWithCreatedAuditFilledIn.setId(null);
        inputWithCreatedAuditFilledIn.setName(PROGRAM_NAME);
        /* They can't set these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithCreatedAuditFilledIn.setCoordinates(GEO_CODE);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.save(inputOrg)).thenReturn(createOrg());
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(inputWithCreatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithCreatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveUpdateExisting() throws Exception {
        Program input = createFilledInProgram();
        input.setId(PROGRAM_ID);
        input.setName(PROGRAM_NAME);
        /*
         * I'm setting these to make sure the latest org changes go out
         * with the program and lessening the chance of cascade update
         * if someone refactors.
         */
        input.getOrganization().setName("old-" + ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));
        /* They also can't set the lat-long (google maps api will give us that. */
        input.setCoordinates("-5.00000,5.00000");

        Program oldProgramInDb = createFilledInProgram();
        oldProgramInDb.setId(PROGRAM_ID);
        oldProgramInDb.setName("old-" + PROGRAM_NAME);
        /* They can't change these. */
        oldProgramInDb.setCreatedBy(USERNAME_1);
        oldProgramInDb.setCreatedOn(CREATED_ON);

        Program inputWithUpdatedAuditFilledIn = createFilledInProgram();
        inputWithUpdatedAuditFilledIn.setId(PROGRAM_ID);
        inputWithUpdatedAuditFilledIn.setName(PROGRAM_NAME);
        /* They can't set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_2);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);
        inputWithUpdatedAuditFilledIn.setCoordinates(GEO_CODE);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(dao.findOne(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveUpdateExisting_ProgramWithNewOrg() throws Exception {
        Program input = createFilledInProgram();
        input.setId(PROGRAM_ID);
        input.setName(PROGRAM_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));
        /* They also can't set the lat-long (google maps api will give us that. */
        input.setCoordinates("-5.00000,5.00000");

        Organization inputOrg = createOrg();
        inputOrg.setId(null); /* This will be a new org. */

        input.setOrganization(inputOrg);

        Program oldProgramInDb = createFilledInProgram();
        oldProgramInDb.setOrganization(createOrg());
        oldProgramInDb.setId(PROGRAM_ID);
        oldProgramInDb.setName("old-" + PROGRAM_NAME);
        /* They can't change these. */
        oldProgramInDb.setCreatedBy(USERNAME_1);
        oldProgramInDb.setCreatedOn(CREATED_ON);

        Program inputWithUpdatedAuditFilledIn = createFilledInProgram();
        inputWithUpdatedAuditFilledIn.setId(PROGRAM_ID);
        inputWithUpdatedAuditFilledIn.setName(PROGRAM_NAME);
        /* They can't set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_2);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);
        inputWithUpdatedAuditFilledIn.setCoordinates(GEO_CODE);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(organizationService.save(inputOrg)).thenReturn(createOrg());
        when(dao.findOne(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testSaveUpdateExisting_ProgramNotFound() throws Exception {
        Program input = createFilledInProgram();
        input.setId(PROGRAM_ID);
        input.setName(ORG_NAME);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(geocodeService.getLocationInfo(notNull(Program.class))).thenReturn(createLocationInfo());
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OnlyAdminCanSaveProgramException.class)
    public void testSaveUpdateExisting_ProgramNotAdmin() throws Exception {
        Program input = createFilledInProgram();
        input.setId(PROGRAM_ID);
        input.setName(ORG_NAME);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = NullPointerException.class)
    public void testSearch_NullQuerySpecified() throws Exception {
        /* Make the call. */
        toTest.search(null);
    }

    @Test
    public void testSearch_NullSearchSpecified() throws Exception {
        MapQuery input = new MapQuery();
        input.setSearch(null);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_EmptySearchSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_ProgramSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setProgram(createFilledInProgram());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(createFilledInProgram());

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OnlyOrgSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(createOrg());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByOrganizationId(ORG_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OnlyCountySpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setCounty(createCounty());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByCountiesServedId(COUNTY_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OnlyProgramAreaSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setProgramArea(createProgramArea());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByProgramAreasId(PROGRAM_AREA_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OrgAndCountySpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(createOrg());
        search.setCounty(createCounty());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByOrganizationIdAndCountiesServedId(ORG_ID, COUNTY_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OrgAndProgramAreaSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(createOrg());
        search.setProgramArea(createProgramArea());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByOrganizationIdAndProgramAreasId(ORG_ID, PROGRAM_AREA_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_CountyAndProgramAreaSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setCounty(createCounty());
        search.setProgramArea(createProgramArea());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findByCountiesServedIdAndProgramAreasId(COUNTY_ID, PROGRAM_AREA_ID)).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSearch_OrgAndCountyAndProgramAreaSpecified() throws Exception {
        MapQuerySearch search = new MapQuerySearch();
        search.setOrganization(createOrg());
        search.setCounty(createCounty());
        search.setProgramArea(createProgramArea());
        MapQuery input = new MapQuery();
        input.setSearch(search);

        List<Program> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(
                dao.findByOrganizationIdAndCountiesServedIdAndProgramAreasId(ORG_ID, COUNTY_ID, PROGRAM_AREA_ID)
        ).thenReturn(expected);

        /* Make the call. */
        List<Program> actual = toTest.search(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
