package org.hmhb.program;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.county.County;
import org.hmhb.exception.program.ProgramNameRequiredException;
import org.hmhb.exception.program.ProgramNotFoundException;
import org.hmhb.exception.program.ProgramOrganizationRequiredException;
import org.hmhb.exception.program.ProgramStateRequiredException;
import org.hmhb.exception.program.ProgramZipCodeRequiredException;
import org.hmhb.geocode.GeocodeService;
import org.hmhb.mapquery.MapQuery;
import org.hmhb.mapquery.MapQuerySearch;
import org.hmhb.organization.Organization;
import org.hmhb.organization.OrganizationService;
import org.hmhb.programarea.ProgramArea;
import org.junit.Before;
import org.junit.Test;

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
    private static final String USERNAME_1 = "somebody";
    private static final String USERNAME_2 = "somebody-else";
    private static final Date UPDATED_ON = new Date(654321L);
    private static final String PROGRAM_NAME = "test-program-name";
    private static final String ORG_NAME = "test-org-name";
    private static final String COUNTY_NAME = "test-county-name";
    private static final String PROGRAM_AREA_NAME = "test-program-area-name";
    private static final int START_YEAR = 1999;
    private static final String STREET_ADDRESS = "test-street-address";
    private static final String CITY = "test-city";
    private static final String STATE = "test-state";
    private static final String ZIP_CODE = "test-zip-code";
    private static final String GEO_CODE = "-1.00000000,1.00000000";

    private AuditHelper auditHelper;
    private GeocodeService geocodeService;
    private OrganizationService organizationService;
    private ProgramDao dao;
    private DefaultProgramService toTest;

    @Before
    public void setUp() throws Exception {
        auditHelper = mock(AuditHelper.class);
        geocodeService = mock(GeocodeService.class);
        organizationService = mock(OrganizationService.class);
        dao = mock(ProgramDao.class);

        toTest = new DefaultProgramService(auditHelper, geocodeService, organizationService, dao);
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

    @Test(expected = ProgramNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
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
    public void testGetByIdsNull() throws Exception {
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
    public void testDelete() throws Exception {
        Program programInDb = createFilledInProgram();

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(programInDb);

        /* Make the call. */
        Program actual = toTest.delete(PROGRAM_ID);

        /* Verify the results. */
        assertEquals(programInDb, actual);
        verify(dao).delete(PROGRAM_ID);
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(PROGRAM_ID);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNewNullName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNewEmptyName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramNameRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceName() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setName(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramOrganizationRequiredException.class)
    public void testSaveCreateNewNullOrg() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setOrganization(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNewNullStartYear() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setStartYear(null);

        /* Make the call. */
        toTest.save(input);

        /* Should not blow up.  After receiving some customer data, we discovered that this should be allowed. */
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNewNullState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNewEmptyState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramStateRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceState() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setState(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNewNullZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNewEmptyZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = ProgramZipCodeRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceZipCode() throws Exception {
        Program input = createFilledInProgram();
        input.setId(null);
        input.setZipCode(" ");

        /* Make the call. */
        toTest.save(input);
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
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLngLat(notNull(Program.class))).thenReturn(GEO_CODE);
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(inputWithCreatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithCreatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveCreateNewProgramWithNewOrg() throws Exception {
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
        when(organizationService.save(inputOrg)).thenReturn(createOrg());
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(geocodeService.getLngLat(notNull(Program.class))).thenReturn(GEO_CODE);
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
        when(organizationService.getById(ORG_ID)).thenReturn(createOrg());
        when(dao.findOne(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(geocodeService.getLngLat(notNull(Program.class))).thenReturn(GEO_CODE);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveUpdateExistingProgramWithNewOrg() throws Exception {
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
        when(organizationService.save(inputOrg)).thenReturn(createOrg());
        when(dao.findOne(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(geocodeService.getLngLat(notNull(Program.class))).thenReturn(GEO_CODE);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        Program actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testSaveUpdateExistingProgramNotFound() throws Exception {
        Program input = createFilledInProgram();
        input.setId(PROGRAM_ID);
        input.setName(ORG_NAME);

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = NullPointerException.class)
    public void testSearchNullQuerySpecified() throws Exception {
        /* Make the call. */
        toTest.search(null);
    }

    @Test
    public void testSearchNullSearchSpecified() throws Exception {
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
    public void testSearchEmptySearchSpecified() throws Exception {
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
    public void testSearchProgramSpecified() throws Exception {
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
    public void testSearchOnlyOrgSpecified() throws Exception {
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
    public void testSearchOnlyCountySpecified() throws Exception {
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
    public void testSearchOnlyProgramAreaSpecified() throws Exception {
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
    public void testSearchOrgAndCountySpecified() throws Exception {
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
    public void testSearchOrgAndProgramAreaSpecified() throws Exception {
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
    public void testSearchCountyAndProgramAreaSpecified() throws Exception {
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
    public void testSearchOrgAndCountyAndProgramAreaSpecified() throws Exception {
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
