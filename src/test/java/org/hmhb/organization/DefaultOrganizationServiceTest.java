package org.hmhb.organization;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hmhb.audit.AuditHelper;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.exception.organization.CannotDeleteOrganizationWithProgramsException;
import org.hmhb.exception.organization.OnlyAdminCanDeleteOrgException;
import org.hmhb.exception.organization.OnlyAdminCanUpdateOrgException;
import org.hmhb.exception.organization.OrganizationEmailIsTooLongException;
import org.hmhb.exception.organization.OrganizationNameIsTooLongException;
import org.hmhb.exception.organization.OrganizationNameRequiredException;
import org.hmhb.exception.organization.OrganizationNotFoundException;
import org.hmhb.exception.organization.OrganizationPhoneIsTooLongException;
import org.hmhb.exception.organization.OrganizationUrlIsInvalidException;
import org.hmhb.exception.organization.OrganizationUrlIsTooLongException;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

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

    private static final int MAX_LEN = 50;
    private static final String TOO_LONG = "1234567890-1234567890-1234567890-1234567890-1234567890";

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

        Environment environment = mock(Environment.class);

        /* Train the config. */
        when(environment.getProperty("hmhb.organization.name.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.phone.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.email.maxLength", Integer.class)).thenReturn(MAX_LEN);
        when(environment.getProperty("hmhb.url.maxLength", Integer.class)).thenReturn(MAX_LEN);

        PublicConfig publicConfig = new PublicConfig(environment);

        ConfigService configService = mock(ConfigService.class);
        when(configService.getPublicConfig()).thenReturn(publicConfig);

        toTest = new DefaultOrganizationService(
                configService,
                auditHelper,
                authorizationService,
                programDao,
                dao
        );
    }

    private String generateString(int numChars) {
        StringBuilder sb = new StringBuilder("http://www.");
        for (int i = 0; i < numChars; i++) {
            sb.append('z');
        }
        sb.append(".com");
        return sb.toString();
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
    public void testGetById_NotFound() throws Exception {
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
        when(programDao.findByOrganizationId(ORG_ID)).thenReturn(Collections.<Program>emptyList());

        /* Make the call. */
        Organization actual = toTest.delete(ORG_ID);

        /* Verify the results. */
        assertEquals(orgInDb, actual);
        verify(dao).delete(ORG_ID);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testDelete_NotFound() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = OnlyAdminCanDeleteOrgException.class)
    public void testDelete_NotAdmin() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = CannotDeleteOrganizationWithProgramsException.class)
    public void testDelete_ForeignKeyViolation() throws Exception {
        Organization orgInDb = createFilledInOrg();

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(dao.findOne(ORG_ID)).thenReturn(orgInDb);
        when(programDao.findByOrganizationId(ORG_ID)).thenReturn(Collections.singletonList(new Program()));

        /* Make the call. */
        toTest.delete(ORG_ID);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNew_NullOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNew_EmptyOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName("");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationNameRequiredException.class)
    public void testSaveCreateNew_OnlyWhitespaceOrgName() throws Exception {
        Organization input = createFilledInOrg();
        input.setName(" ");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationNameIsTooLongException.class)
    public void testSaveCreateNew_OrgNameTooLong() throws Exception {
        Organization input = createFilledInOrg();
        input.setName(TOO_LONG);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationPhoneIsTooLongException.class)
    public void testSaveCreateNew_PhoneTooLong() throws Exception {
        Organization input = createFilledInOrg();
        input.setContactPhone(TOO_LONG);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationEmailIsTooLongException.class)
    public void testSaveCreateNew_EmailTooLong() throws Exception {
        Organization input = createFilledInOrg();
        input.setContactEmail(TOO_LONG);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationUrlIsTooLongException.class)
    public void testSaveCreateNew_FacebookUrlTooLong() throws Exception {
        Organization input = createFilledInOrg();
        input.setFacebookUrl(generateString(2001));

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_NullFacebookUrl() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(null);
        input.setFacebookUrl(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* This should not blow up. */
    }

    @Test
    public void testSaveCreateNew_EmptyStringFacebookUrl() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(null);
        input.setFacebookUrl("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* This should not blow up. */
    }

    @Test(expected = OrganizationUrlIsInvalidException.class)
    public void testSaveCreateNew_FacebookUrlInvalid() throws Exception {
        Organization input = createFilledInOrg();
        input.setFacebookUrl("not-a-url");

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationUrlIsTooLongException.class)
    public void testSaveCreateNew_WebsiteUrlTooLong() throws Exception {
        Organization input = createFilledInOrg();
        input.setWebsiteUrl(generateString(2001));

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = OrganizationUrlIsInvalidException.class)
    public void testSaveCreateNew_WebsiteUrlInvalid() throws Exception {
        Organization input = createFilledInOrg();
        input.setWebsiteUrl("not-a-url");

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSaveCreateNew_NullWebsiteUrl() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(null);
        input.setWebsiteUrl(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* This should not blow up. */
    }

    @Test
    public void testSaveCreateNew_EmptyStringWebsiteUrl() throws Exception {
        Organization input = createFilledInOrg();
        input.setId(null);
        input.setWebsiteUrl("");

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);

        /* Make the call. */
        toTest.save(input);

        /* This should not blow up. */
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
    public void testSaveUpdateExisting_OrgNotFound() throws Exception {
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
    public void testSaveUpdateExisting_OrgNotAdmin() throws Exception {
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
