package org.hmhb.program.requested;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.program.published.PublishedProgramNotFoundException;
import org.hmhb.exception.program.requested.RequestedProgramGeoRequiredException;
import org.hmhb.exception.program.requested.RequestedProgramNotFoundException;
import org.hmhb.exception.program.requested.RequestedProgramOrgNameRequiredException;
import org.hmhb.program.published.PublishedProgram;
import org.hmhb.program.published.PublishedProgramService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultRequestedProgramService}.
 */
public class DefaultRequestedProgramServiceTest {

    private static final long REQUEST_ID = 123L;
    private static final long PROGRAM_ID = 456L;
    private static final String IP_ADDRESS = "127.0.0.1";
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
    private PublishedProgramService programService;
    private RequestedProgramDao dao;
    private DefaultRequestedProgramService toTest;

    @Before
    public void setUp() throws Exception {
        auditHelper = mock(AuditHelper.class);
        programService = mock(PublishedProgramService.class);
        dao = mock(RequestedProgramDao.class);

        toTest = new DefaultRequestedProgramService(
                auditHelper,
                programService,
                dao
        );
    }

    private RequestedProgram createFilledInRequest() {
        RequestedProgram request = new RequestedProgram();

        request.setId(REQUEST_ID);
        request.setGeo(GEO);
        request.setMission(MISSION);
        request.setOrganizationName(ORG_NAME);
        request.setOutcomes(OUTCOMES);
        request.setPrivateEmail(PRIVATE_EMAIL);
        request.setPrivatePhone(PRIVATE_PHONE);
        request.setPublicEmail(PUBLIC_EMAIL);
        request.setPublicPhone(PUBLIC_PHONE);
        request.setSummary(SUMMARY);

        return request;
    }

    @Test
    public void testGetById() throws Exception {
        RequestedProgram expected = createFilledInRequest();

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.getById(REQUEST_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = RequestedProgramNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(REQUEST_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        List<RequestedProgram> expected = Collections.singletonList(createFilledInRequest());

        /* Train the mocks. */
        when(dao.findAll()).thenReturn(expected);

        /* Make the call. */
        List<RequestedProgram> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws Exception {
        RequestedProgram requestInDb = createFilledInRequest();

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(requestInDb);

        /* Make the call. */
        RequestedProgram actual = toTest.delete(REQUEST_ID);

        /* Verify the results. */
        assertEquals(requestInDb, actual);
        verify(dao).delete(REQUEST_ID);
    }

    @Test(expected = RequestedProgramNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(REQUEST_ID);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewNullOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewEmptyOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testSaveCreateNewNullGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(null);
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testSaveCreateNewEmptyGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo("");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(" ");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        RequestedProgram inputWithCreatedAuditFilledIn = new RequestedProgram();
        inputWithCreatedAuditFilledIn.setGeo(GEO);
        inputWithCreatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They cannot change these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(IP_ADDRESS);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);

        RequestedProgram expected = new RequestedProgram();
        expected.setId(REQUEST_ID);
        expected.setGeo(GEO);
        expected.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        expected.setCreatedBy(IP_ADDRESS);
        expected.setCreatedOn(CREATED_ON);

        /* Train the mocks. */
        when(auditHelper.getCallerIp()).thenReturn(IP_ADDRESS);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(programService, never()).getById(anyLong());
    }

    @Test
    public void testSaveCreateNewProgramSpecified() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setProgramId(PROGRAM_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        RequestedProgram inputWithCreatedAuditFilledIn = new RequestedProgram();
        inputWithCreatedAuditFilledIn.setProgramId(PROGRAM_ID);
        inputWithCreatedAuditFilledIn.setGeo(GEO);
        inputWithCreatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They cannot change these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(IP_ADDRESS);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);

        RequestedProgram expected = new RequestedProgram();
        expected.setId(REQUEST_ID);
        expected.setProgramId(PROGRAM_ID);
        expected.setGeo(GEO);
        expected.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        expected.setCreatedBy(IP_ADDRESS);
        expected.setCreatedOn(CREATED_ON);

        /* Train the mocks. */
        when(auditHelper.getCallerIp()).thenReturn(IP_ADDRESS);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(expected);

        /* Make the call. */
        RequestedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(programService).getById(PROGRAM_ID);
    }

    @Test
    public void testSaveUpdateExisting() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setId(REQUEST_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        RequestedProgram oldRequestInDb = createFilledInRequest();
        oldRequestInDb.setId(REQUEST_ID);
        oldRequestInDb.setGeo("old-" + GEO);
        oldRequestInDb.setOrganizationName("old-" + ORG_NAME);
        /* They cannot change these. */
        oldRequestInDb.setCreatedBy(IP_ADDRESS);
        oldRequestInDb.setCreatedOn(CREATED_ON);

        RequestedProgram inputWithUpdatedAuditFilledIn = new RequestedProgram();
        inputWithUpdatedAuditFilledIn.setId(REQUEST_ID);
        inputWithUpdatedAuditFilledIn.setGeo(GEO);
        inputWithUpdatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(IP_ADDRESS);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(oldRequestInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        RequestedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
        verify(programService, never()).getById(anyLong());
    }

    @Test
    public void testSaveUpdateExistingProgramSpecified() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setId(REQUEST_ID);
        input.setProgramId(PROGRAM_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        RequestedProgram oldRequestInDb = createFilledInRequest();
        oldRequestInDb.setId(REQUEST_ID);
        oldRequestInDb.setProgramId(PROGRAM_ID);
        oldRequestInDb.setGeo("old-" + GEO);
        oldRequestInDb.setOrganizationName("old-" + ORG_NAME);
        /* They cannot change these. */
        oldRequestInDb.setCreatedBy(IP_ADDRESS);
        oldRequestInDb.setCreatedOn(CREATED_ON);

        RequestedProgram inputWithUpdatedAuditFilledIn = new RequestedProgram();
        inputWithUpdatedAuditFilledIn.setId(REQUEST_ID);
        inputWithUpdatedAuditFilledIn.setProgramId(PROGRAM_ID);
        inputWithUpdatedAuditFilledIn.setGeo(GEO);
        inputWithUpdatedAuditFilledIn.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(IP_ADDRESS);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(oldRequestInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        RequestedProgram actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
        verify(programService).getById(PROGRAM_ID);
    }

    @Test(expected = RequestedProgramNotFoundException.class)
    public void testSaveUpdateExistingRequestNotFound() throws Exception {
        RequestedProgram input = createFilledInRequest();
        input.setId(REQUEST_ID);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = PublishedProgramNotFoundException.class)
    public void testSaveProgramNotFound() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        input.setProgramId(PROGRAM_ID);

        /* Train the mocks. */
        when(programService.getById(PROGRAM_ID)).thenThrow(new PublishedProgramNotFoundException(PROGRAM_ID));

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testPublishRequestAsNewProgram() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setId(REQUEST_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        RequestedProgram oldRequestInDb = createFilledInRequest();
        oldRequestInDb.setId(REQUEST_ID);
        oldRequestInDb.setGeo("old-" + GEO);
        oldRequestInDb.setOrganizationName("old-" + ORG_NAME);
        /* The REST call input is not setting programId, so this connection to existing program is being separated. */
        oldRequestInDb.setProgramId(PROGRAM_ID);
        /* They cannot change these. */
        oldRequestInDb.setCreatedBy(IP_ADDRESS);
        oldRequestInDb.setCreatedOn(CREATED_ON);

        PublishedProgram expectedProgramToSave = new PublishedProgram();
        expectedProgramToSave.setGeo(GEO);
        expectedProgramToSave.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        expectedProgramToSave.setCreatedBy(USERNAME_1);
        expectedProgramToSave.setCreatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(oldRequestInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);

        /* Make the call. */
        toTest.publish(input);

        /* Verify the results. */
        ArgumentCaptor<PublishedProgram> argumentCaptor = ArgumentCaptor.forClass(PublishedProgram.class);
        verify(programService).save(argumentCaptor.capture());
        assertEquals(expectedProgramToSave, argumentCaptor.getValue());
    }

    @Test
    public void testPublishRequestAsExistingProgram() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setId(REQUEST_ID);
        input.setProgramId(PROGRAM_ID);
        input.setGeo(GEO);
        input.setOrganizationName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        PublishedProgram oldProgramInDb = new PublishedProgram();
        oldProgramInDb.setId(PROGRAM_ID);
        oldProgramInDb.setGeo("very-old-" + GEO);
        oldProgramInDb.setOrganizationName("very-old-" + ORG_NAME);
        /* They cannot change these. */
        oldProgramInDb.setCreatedBy(USERNAME_1);
        oldProgramInDb.setCreatedOn(CREATED_ON);

        RequestedProgram oldRequestInDb = createFilledInRequest();
        oldRequestInDb.setGeo("old-" + GEO);
        oldRequestInDb.setOrganizationName("old-" + ORG_NAME);
        /* They cannot change these. */
        oldRequestInDb.setCreatedBy(IP_ADDRESS);
        oldRequestInDb.setCreatedOn(CREATED_ON);

        PublishedProgram expectedProgramToSave = new PublishedProgram();
        expectedProgramToSave.setId(PROGRAM_ID);
        expectedProgramToSave.setGeo(GEO);
        expectedProgramToSave.setOrganizationName(ORG_NAME);
        /* They cannot set these. */
        expectedProgramToSave.setCreatedBy(USERNAME_1);
        expectedProgramToSave.setCreatedOn(CREATED_ON);
        expectedProgramToSave.setUpdatedBy(USERNAME_2);
        expectedProgramToSave.setUpdatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(oldRequestInDb);
        when(programService.getById(PROGRAM_ID)).thenReturn(oldProgramInDb);
        when(auditHelper.getCurrentUser()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);

        /* Make the call. */
        toTest.publish(input);

        /* Verify the results. */
        ArgumentCaptor<PublishedProgram> argumentCaptor = ArgumentCaptor.forClass(PublishedProgram.class);
        verify(programService).save(argumentCaptor.capture());
        assertEquals(expectedProgramToSave, argumentCaptor.getValue());
    }

    @Test(expected = RequestedProgramNotFoundException.class)
    public void testPublishRequestNotFound() throws Exception {
        RequestedProgram input = createFilledInRequest();
        input.setId(REQUEST_ID);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(null);

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = PublishedProgramNotFoundException.class)
    public void testPublishProgramNotFound() throws Exception {
        RequestedProgram input = createFilledInRequest();
        input.setId(REQUEST_ID);
        input.setProgramId(PROGRAM_ID);

        RequestedProgram oldRequestInDb = createFilledInRequest();
        oldRequestInDb.setGeo("old-" + GEO);
        oldRequestInDb.setOrganizationName("old-" + ORG_NAME);
        /* They cannot change these. */
        oldRequestInDb.setCreatedBy(IP_ADDRESS);
        oldRequestInDb.setCreatedOn(CREATED_ON);

        /* Train the mocks. */
        when(dao.findOne(REQUEST_ID)).thenReturn(oldRequestInDb);
        when(programService.getById(PROGRAM_ID)).thenThrow(new PublishedProgramNotFoundException(PROGRAM_ID));

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testPublishNullOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(null);

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testPublishEmptyOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName("");

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramOrgNameRequiredException.class)
    public void testPublishOnlyWhitespaceOrgName() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(GEO);
        input.setOrganizationName(" ");

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testPublishNullGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(null);
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testPublishEmptyGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo("");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.publish(input);
    }

    @Test(expected = RequestedProgramGeoRequiredException.class)
    public void testPublishOnlyWhitespaceGeo() throws Exception {
        RequestedProgram input = new RequestedProgram();
        input.setGeo(" ");
        input.setOrganizationName(ORG_NAME);

        /* Make the call. */
        toTest.publish(input);
    }

}
