package org.hmhb.program.published;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.program.published.PublishedProgramGeoRequiredException;
import org.hmhb.exception.program.published.PublishedProgramNotFoundException;
import org.hmhb.exception.program.published.PublishedProgramOrgNameRequiredException;
import org.hmhb.exception.program.published.UpdateRequestsExistException;
import org.hmhb.program.requested.RequestedProgram;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultPublishedProgramService}.
 */
public class DefaultPublishedProgramServiceTest {

    private static final long PROGRAM_ID = 123L;
    private static final Date CREATED_ON = new Date(123456L);
    private static final String USERNAME_1 = "somebody";
    private static final String USERNAME_2 = "somebody-else";
    private static final Date UPDATED_ON = new Date(654321L);
    private static final String GEO = "test-geo";
    private static final String MISSION = "test-mission";
    public static final String ORG_NAME = "test-org-name";
    public static final String OUTCOMES = "test-outcomes";
    public static final String PRIVATE_EMAIL = "priv.email@gmail.com";
    public static final String PRIVATE_PHONE = "404-555-0000";
    public static final String PUBLIC_EMAIL = "pub.email@mailinator.com";
    public static final String PUBLIC_PHONE = "770-555-0000";
    public static final String SUMMARY = "test-summary";

    private AuditHelper auditHelper;
    private PublishedProgramDao dao;
    private DefaultPublishedProgramService toTest;

    @Before
    public void setUp() throws Exception {
        auditHelper = mock(AuditHelper.class);
        dao = mock(PublishedProgramDao.class);

        toTest = new DefaultPublishedProgramService(auditHelper, dao);

    }

    private PublishedProgram createFilledInProgram() {
        PublishedProgram program = new PublishedProgram();

        program.setId(PROGRAM_ID);
        program.setGeo(GEO);
        program.setMission(MISSION);
        program.setOrganizationName(ORG_NAME);
        program.setOutcomes(OUTCOMES);
        program.setPrivateEmail(PRIVATE_EMAIL);
        program.setPrivatePhone(PRIVATE_PHONE);
        program.setPublicEmail(PUBLIC_EMAIL);
        program.setPublicPhone(PUBLIC_PHONE);
        program.setSummary(SUMMARY);

        return program;
    }

    @Test
    public void testGetById() throws Exception {
        PublishedProgram expected = createFilledInProgram();

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(expected);

        /* Make the call. */
        PublishedProgram actual = toTest.getById(PROGRAM_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = PublishedProgramNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(PROGRAM_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        List<PublishedProgram> expected = Collections.singletonList(createFilledInProgram());

        /* Train the mocks. */
        when(dao.findAll()).thenReturn(expected);

        /* Make the call. */
        List<PublishedProgram> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws Exception {
        PublishedProgram programInDb = createFilledInProgram();

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(programInDb);

        /* Make the call. */
        PublishedProgram actual = toTest.delete(PROGRAM_ID);

        /* Verify the results. */
        assertEquals(programInDb, actual);
        verify(dao).delete(PROGRAM_ID);
    }

    @Test(expected = PublishedProgramNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(PROGRAM_ID);
    }

    @Test(expected = UpdateRequestsExistException.class)
    public void testDeleteForeignKeyViolation() throws Exception {
        PublishedProgram programInDb = createFilledInProgram();
        programInDb.setRequestedPrograms(Collections.singletonList(new RequestedProgram()));

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(programInDb);

        /* Make the call. */
        toTest.delete(PROGRAM_ID);
    }

    @Test(expected = PublishedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewNullOrgName() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewEmptyOrgName() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(GEO);
        input.setOrganizationName("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceOrgName() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramGeoRequiredException.class)
    public void testSaveCreateNewNullGeo() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(null);
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramGeoRequiredException.class)
    public void testSaveCreateNewEmptyGeo() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo("");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramGeoRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceGeo() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(" ");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        PublishedProgram inputWithCreatedAuditFilledIn = new PublishedProgram();
        inputWithCreatedAuditFilledIn.setGeo(GEO);
        inputWithCreatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They can't set these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);

        /* Train the mocks. */
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(inputWithCreatedAuditFilledIn);

        /* Make the call. */
        PublishedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithCreatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveUpdateExisting() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setId(PROGRAM_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        PublishedProgram oldProgramInDb = createFilledInProgram();
        oldProgramInDb.setId(PROGRAM_ID);
        oldProgramInDb.setGeo("old-" + GEO);
        oldProgramInDb.setOrganizationName("old-" + ORG_NAME);
        /* They can't change these. */
        oldProgramInDb.setCreatedBy(USERNAME_1);
        oldProgramInDb.setCreatedOn(CREATED_ON);

        PublishedProgram inputWithUpdatedAuditFilledIn = new PublishedProgram();
        inputWithUpdatedAuditFilledIn.setId(PROGRAM_ID);
        inputWithUpdatedAuditFilledIn.setGeo(GEO);
        inputWithUpdatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They can't set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_2);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        PublishedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test(expected = PublishedProgramNotFoundException.class)
    public void testSaveUpdateExistingProgramNotFound() throws Exception {
        PublishedProgram input = new PublishedProgram();
        input.setId(PROGRAM_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);

        /* Train the mocks. */
        when(dao.findOne(PROGRAM_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

}
