package org.hmhb.organization;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.exception.organization.CannotDeleteOrganizationWithProgramsException;
import org.hmhb.exception.organization.OnlyAdminCanDeleteOrgException;
import org.hmhb.exception.organization.OnlyAdminCanUpdateOrgException;
import org.hmhb.exception.organization.OrganizationNameRequiredException;
import org.hmhb.exception.organization.OrganizationNotFoundException;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramDao;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultOrganizationService}.
 */
public class DefaultOrganizationServiceTest {

    private static final long ORG_ID = 123L;
    private static final Date CREATED_ON = new Date(123456L);
    private static final String USERNAME_1 = "somebody@mailinator.com";
    private static final String USERNAME_2 = "somebody-else@mailinator.com";
    private static final Date UPDATED_ON = new Date(654321L);
    private static final String ORG_NAME = "test-org-name";
    private static final String CONTACT_EMAIL = "pub.email@mailinator.com";
    private static final String CONTACT_PHONE = "770-555-0000";
    private static final String WEBSITE_URL = "http://www.test-website-url.com";
    private static final String FACEBOOK_URL = "http://www.facebook.com/TestFacebook";

    private AuditHelper auditHelper;
    private AuthorizationService authorizationService;
    private ProgramDao programDao;
    private OrganizationDao dao;
    private DefaultOrganizationService toTest;

    @Before
    public void setUp() throws Exception {
        auditHelper = mock(AuditHelper.class);
        authorizationService = mock(AuthorizationService.class);
        programDao = mock(ProgramDao.class);
        dao = mock(OrganizationDao.class);

        toTest = new DefaultOrganizationService(
                auditHelper,
                authorizationService,
                programDao,
                dao
        );
    }

    private Organization createFilledInOrg() {
        Organization organization = new Organization();

        organization.setId(ORG_ID);
        organization.setName(ORG_NAME);
        organization.setContactEmail(CONTACT_EMAIL);
        organization.setContactPhone(CONTACT_PHONE);
        organization.setWebsiteUrl(WEBSITE_URL);
        organization.setFacebookUrl(FACEBOOK_URL);

        return organization;
    }

    @Test
    public void testGetById() throws Exception {
        Organization expected = createFilledInOrg();

        /* Train the mocks. */
        when(dao.findOne(ORG_ID)).thenReturn(expected);

        /* Make the call. */
        Organization actual = toTest.getById(ORG_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testGetByIdNotFound() throws Exception {
        /* Train the mocks. */
        when(dao.findOne(ORG_ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(ORG_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Organization> expected = Collections.singletonList(createFilledInOrg());

        /* Train the mocks. */
        when(dao.findAllByOrderByNameAsc()).thenReturn(expected);

        /* Make the call. */
        List<Organization> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws Exception {
        Organization orgInDb = createFilledInOrg();

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(orgInDb);
        when(programDao.findByOrganizationId(ORG_ID)).thenReturn(Collections.emptyList());

        /* Make the call. */
        Organization actual = toTest.delete(ORG_ID);

        /* Verify the results. */
        assertEquals(orgInDb, actual);
        verify(dao).delete(ORG_ID);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = OnlyAdminCanDeleteOrgException.class)
    public void testDeleteNotAdmin() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = CannotDeleteOrganizationWithProgramsException.class)
    public void testDeleteForeignKeyViolation() throws Exception {
        Organization orgInDb = createFilledInOrg();

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(orgInDb);
        when(programDao.findByOrganizationId(ORG_ID)).thenReturn(Collections.singletonList(new Program()));

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNewNullOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNewEmptyOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNewOnlyWhitespaceOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(null);
        input.setName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        Organization inputWithCreatedAuditFilledIn = createFilledInOrg();
        inputWithCreatedAuditFilledIn.setId(null);
        inputWithCreatedAuditFilledIn.setName(ORG_NAME);
        /* They can't set these. */
        inputWithCreatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithCreatedAuditFilledIn.setCreatedOn(CREATED_ON);

        /* Train the mocks. */
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_1);
        when(auditHelper.getCurrentTime()).thenReturn(CREATED_ON);
        when(dao.save(inputWithCreatedAuditFilledIn)).thenReturn(inputWithCreatedAuditFilledIn);

        /* Make the call. */
        Organization actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithCreatedAuditFilledIn, actual);
    }

    @Test
    public void testSaveUpdateExisting() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(ORG_ID);
        input.setName(ORG_NAME);
        /* I'm setting these fields to prove that they aren't respected. */
        input.setCreatedBy("input-from-rest-call");
        input.setCreatedOn(new Date(System.currentTimeMillis() + 60000));
        input.setUpdatedBy("input-from-rest-call");
        input.setUpdatedOn(new Date(System.currentTimeMillis() + 60000));

        Organization oldOrgInDb = createFilledInOrg();
        oldOrgInDb.setId(ORG_ID);
        oldOrgInDb.setName("old-" + ORG_NAME);
        /* They can't change these. */
        oldOrgInDb.setCreatedBy(USERNAME_1);
        oldOrgInDb.setCreatedOn(CREATED_ON);

        Organization inputWithUpdatedAuditFilledIn = createFilledInOrg();
        inputWithUpdatedAuditFilledIn.setId(ORG_ID);
        inputWithUpdatedAuditFilledIn.setName(ORG_NAME);
        /* They can't set these. */
        inputWithUpdatedAuditFilledIn.setCreatedBy(USERNAME_1);
        inputWithUpdatedAuditFilledIn.setCreatedOn(CREATED_ON);
        inputWithUpdatedAuditFilledIn.setUpdatedBy(USERNAME_2);
        inputWithUpdatedAuditFilledIn.setUpdatedOn(UPDATED_ON);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(oldOrgInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(USERNAME_2);
        when(auditHelper.getCurrentTime()).thenReturn(UPDATED_ON);
        when(dao.save(inputWithUpdatedAuditFilledIn)).thenReturn(inputWithUpdatedAuditFilledIn);

        /* Make the call. */
        Organization actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(inputWithUpdatedAuditFilledIn, actual);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testSaveUpdateExistingOrgNotFound() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(ORG_ID);
        input.setName(ORG_NAME);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OnlyAdminCanUpdateOrgException.class)
    public void testSaveUpdateExistingOrgNotAdmin() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(ORG_ID);
        input.setName(ORG_NAME);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(dao.findOne(ORG_ID)).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

}
