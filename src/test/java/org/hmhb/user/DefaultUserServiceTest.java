package org.hmhb.user;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.services.plus.model.Person;
import org.hmhb.audit.AuditHelper;
import org.hmhb.authentication.JwtAuthenticationService;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.csv.CsvService;
import org.hmhb.exception.user.UserCannotDeleteSuperAdminException;
import org.hmhb.exception.user.UserEmailRequiredException;
import org.hmhb.exception.user.UserEmailTooLongException;
import org.hmhb.exception.user.UserNonAdminCannotEscalateToAdminException;
import org.hmhb.exception.user.UserNotAllowedToAccessOtherProfileException;
import org.hmhb.exception.user.UserNotFoundException;
import org.hmhb.exception.user.UserSuperAdminCannotBeModifiedByOthers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultUserService}.
 */
public class DefaultUserServiceTest {

    private static final Long USER_ID = 123L;
    private static final Long OTHER_USER_ID = 456L;
    private static final String DISPLAY_NAME = "John Doe";
    private static final String EMAIL = "john.doe@mailinator.com";
    private static final String OTHER_EMAIL = "other.person@mailinator.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String IMAGE_URL = "https://lh3.googleusercontent.com/-XdUIq/AAAI/AAAA/42cv5M/photo.jpg?sz=50";
    private static final String PROFILE_URL = "something";

    private static final Date BEFORE = new Date(1234567890L);
    private static final Date NOW = new Date(9876543210L);

    private static final String JWT_TOKEN = "test-jwt-tokent";

    private static final int MAX_LEN = 50;
    private static final String TOO_LONG = "1234567890-1234567890-1234567890-1234567890-1234567890";

    private AuditHelper auditHelper;
    private AuthorizationService authorizationService;
    private HttpServletRequest request;
    private JwtAuthenticationService jwtAuthService;
    private CsvService csvService;
    private UserDao dao;
    private DefaultUserService toTest;

    @Before
    public void setUp() throws Exception {
        auditHelper = mock(AuditHelper.class);
        authorizationService = mock(AuthorizationService.class);
        request = mock(HttpServletRequest.class);
        jwtAuthService = mock(JwtAuthenticationService.class);
        csvService = mock(CsvService.class);
        dao = mock(UserDao.class);

        Environment environment = mock(Environment.class);

        /* Train the config. */
        when(environment.getProperty("hmhb.email.maxLength", Integer.class)).thenReturn(MAX_LEN);

        PublicConfig publicConfig = new PublicConfig(environment);

        ConfigService configService = mock(ConfigService.class);
        when(configService.getPublicConfig()).thenReturn(publicConfig);

        toTest = new DefaultUserService(
                configService,
                auditHelper,
                authorizationService,
                request,
                jwtAuthService,
                csvService,
                dao
        );
    }

    @Test
    public void testSaveWithGoogleData() throws Exception {
        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setAdmin(true);
        userInDb.setEmail(EMAIL);
        userInDb.setDisplayName(DISPLAY_NAME + "-old");
        userInDb.setFirstName(FIRST_NAME + "-old");
        userInDb.setLastName(LAST_NAME + "-old");
        userInDb.setImageUrl(IMAGE_URL + "-old");
        userInDb.setProfileUrl(PROFILE_URL + "-old");
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setAdmin(true);
        expected.setEmail(EMAIL);
        expected.setDisplayName(DISPLAY_NAME);
        expected.setFirstName(FIRST_NAME);
        expected.setLastName(LAST_NAME);
        expected.setImageUrl(IMAGE_URL);
        expected.setProfileUrl(PROFILE_URL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy("system-updated-from-google-login");
        expected.setUpdatedOn(NOW);

        Person.Image image = new Person.Image();
        image.setUrl(IMAGE_URL);

        Person.Name name = new Person.Name();
        name.setFamilyName(LAST_NAME);
        name.setGivenName(FIRST_NAME);

        Person gPlusProfile = new Person();
        gPlusProfile.setUrl(PROFILE_URL);
        gPlusProfile.setDisplayName(DISPLAY_NAME);
        gPlusProfile.setImage(image);
        gPlusProfile.setName(name);

        /* Train the mocks. */
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.findByEmailIgnoreCase(EMAIL)).thenReturn(userInDb);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.saveWithGoogleData(EMAIL, gPlusProfile);

        /* Verify the results. */
        assertEquals(userInDb, actual);
    }

    @Test
    public void testSaveWithGoogleData_NotFound() throws Exception {
        HmhbUser expected = new HmhbUser();
        expected.setAdmin(false);
        expected.setEmail(EMAIL);
        expected.setDisplayName(DISPLAY_NAME);
        expected.setFirstName(FIRST_NAME);
        expected.setLastName(LAST_NAME);
        expected.setImageUrl(IMAGE_URL);
        expected.setProfileUrl(PROFILE_URL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(NOW);

        Person.Image image = new Person.Image();
        image.setUrl(IMAGE_URL);

        Person.Name name = new Person.Name();
        name.setFamilyName(LAST_NAME);
        name.setGivenName(FIRST_NAME);

        Person gPlusProfile = new Person();
        gPlusProfile.setUrl(PROFILE_URL);
        gPlusProfile.setDisplayName(DISPLAY_NAME);
        gPlusProfile.setImage(image);
        gPlusProfile.setName(name);

        /* Train the mocks. */
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.findByEmailIgnoreCase(EMAIL)).thenReturn(null);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.saveWithGoogleData(EMAIL, gPlusProfile);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetById_Admin_FoundUser() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.getById(USER_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetById_Admin_UserNotFound() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(null);

        /* Make the call. */
        toTest.getById(USER_ID);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetById_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        toTest.getById(USER_ID);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetById_NonAdmin_AccessingOtherProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.getById(USER_ID);
    }

    @Test
    public void testGetById_NonAdmin_AccessingOwnProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(loggedInUser);

        /* Make the call. */
        HmhbUser actual = toTest.getById(USER_ID);

        /* Verify the results. */
        assertEquals(loggedInUser, actual);
    }

    @Test
    public void testGetAll_Admin() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser expectedUser = new HmhbUser();
        expectedUser.setId(USER_ID);

        List<HmhbUser> expected = Collections.singletonList(expectedUser);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findAllByOrderByDisplayNameAscEmailAsc()).thenReturn(expected);

        /* Make the call. */
        List<HmhbUser> actual = toTest.getAll();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetAll_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        toTest.getAll();
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetAll_NonAdmin_AccessingOtherProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.getAll();
    }

    @Test
    public void testGetAllAsCsv_Admin() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser expectedUser = new HmhbUser();
        expectedUser.setId(USER_ID);

        List<HmhbUser> usersFound = Collections.singletonList(expectedUser);

        String expected = "a,b,c\n1,2,3\n";

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findAllByOrderByDisplayNameAscEmailAsc()).thenReturn(usersFound);
        when(csvService.generateFromUsers(usersFound)).thenReturn(expected);

        /* Make the call. */
        String actual = toTest.getAllAsCsv(JWT_TOKEN);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(jwtAuthService).validateToken(request, JWT_TOKEN);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetAllAsCsv_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        toTest.getAllAsCsv(JWT_TOKEN);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testGetAllAsCsv_NonAdmin_AccessingOtherProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.getAllAsCsv(JWT_TOKEN);
    }

    @Test
    public void testSave_SuperAdmin_UpdatesTheirProfile() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setSuperAdmin(false); /* this should be ignored */
        input.setAdmin(false); /* this should be ignored */
        input.setEmail(EMAIL);
        /* Try setting audit info; the system should ignore it. */
        input.setCreatedBy("try to change this");
        input.setCreatedOn(new Date(-1));
        input.setUpdatedBy("trying to set this");
        input.setUpdatedOn(new Date(-2));

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(true);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setSuperAdmin(true);
        userInDb.setAdmin(true);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setSuperAdmin(true); /* super admin cannot be changed */
        expected.setAdmin(true); /* super admin implies admin */
        expected.setEmail(EMAIL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy(OTHER_EMAIL);
        expected.setUpdatedOn(NOW);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(OTHER_EMAIL);
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserSuperAdminCannotBeModifiedByOthers.class)
    public void testSave_Admin_TriesToUpdateSuperAdmin() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setSuperAdmin(true);
        input.setAdmin(true);
        input.setEmail(EMAIL);
        /* Try setting audit info; the system should ignore it. */
        input.setCreatedBy("try to change this");
        input.setCreatedOn(new Date(-1));
        input.setUpdatedBy("trying to set this");
        input.setUpdatedOn(new Date(-2));

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(true);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setSuperAdmin(true);
        userInDb.setAdmin(true);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSave_Admin_UpdateExistingUser() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setAdmin(true);
        input.setEmail(EMAIL);
        /* Try setting audit info; the system should ignore it. */
        input.setCreatedBy("try to change this");
        input.setCreatedOn(new Date(-1));
        input.setUpdatedBy("trying to set this");
        input.setUpdatedOn(new Date(-2));

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setAdmin(false);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setAdmin(true);
        expected.setEmail(EMAIL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy(OTHER_EMAIL);
        expected.setUpdatedOn(NOW);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(OTHER_EMAIL);
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testSave_Admin_CreateNewUser() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setAdmin(true);
        input.setEmail(EMAIL);
        /* Try setting audit info; the system should ignore it. */
        input.setCreatedBy("try to change this");
        input.setCreatedOn(new Date(-1));
        input.setUpdatedBy("trying to set this");
        input.setUpdatedOn(new Date(-2));

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser expected = new HmhbUser();
        expected.setAdmin(true);
        expected.setEmail(EMAIL);
        expected.setCreatedBy(OTHER_EMAIL);
        expected.setCreatedOn(NOW);
        expected.setUpdatedBy(null);
        expected.setUpdatedOn(null);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(auditHelper.getCurrentUserEmail()).thenReturn(OTHER_EMAIL);
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserEmailTooLongException.class)
    public void testSave_Admin_CreateNewUser_EmailTooLong() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setAdmin(true);
        input.setEmail(TOO_LONG);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserEmailRequiredException.class)
    public void testSave_Admin_CreateNewUser_NullEmail() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setAdmin(true);
        input.setEmail(null);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserEmailRequiredException.class)
    public void testSave_Admin_CreateNewUser_EmptyEmail() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setAdmin(true);
        input.setEmail("");

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserEmailRequiredException.class)
    public void testSave_Admin_CreateNewUser_WhitespaceEmail() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setAdmin(true);
        input.setEmail("   ");

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testSave_NotLoggedIn() throws Exception {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setEmail(EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testSave_NonAdmin_ModifyingOtherProfile() {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setEmail(EMAIL);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testSave_NonAdmin_CreatingNewProfile() {
        HmhbUser input = new HmhbUser();
        input.setId(null);
        input.setEmail(EMAIL);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSave_NonAdmin_ModifyingOwnProfile() {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setEmail(EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setAdmin(false);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setAdmin(false);
        expected.setEmail(EMAIL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy(EMAIL);
        expected.setUpdatedOn(NOW);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(EMAIL);
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test(expected = UserNonAdminCannotEscalateToAdminException.class)
    public void testSave_NonAdmin_ModifyingOwnProfile_PrivilegeEscalation() {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setAdmin(true); /* non-admin is trying to escalate privileges to admin */
        input.setEmail(EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setAdmin(false);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setAdmin(false);
        expected.setEmail(EMAIL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy(EMAIL);
        expected.setUpdatedOn(NOW);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);

        /* Make the call. */
        toTest.save(input);
    }

    @Test
    public void testSave_Admin_PrivilegeEscalation() {
        HmhbUser input = new HmhbUser();
        input.setId(USER_ID);
        input.setSuperAdmin(true); /* admin is trying to escalate privileges to super-admin */
        input.setAdmin(true);
        input.setEmail(EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setSuperAdmin(false);
        userInDb.setAdmin(true);
        userInDb.setEmail(EMAIL);
        userInDb.setCreatedBy("system-generated");
        userInDb.setCreatedOn(BEFORE);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setSuperAdmin(false); /* the system shouldn't let them change super-admin */
        expected.setAdmin(true);
        expected.setEmail(EMAIL);
        expected.setCreatedBy("system-generated");
        expected.setCreatedOn(BEFORE);
        expected.setUpdatedBy(EMAIL);
        expected.setUpdatedOn(NOW);

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);
        when(auditHelper.getCurrentUserEmail()).thenReturn(EMAIL);
        when(auditHelper.getCurrentTime()).thenReturn(NOW);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.save(input);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_Admin_FoundUser() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.delete(USER_ID);

        /* Verify the results. */
        assertEquals(expected, actual);
        verify(dao).delete(USER_ID);
    }

    @Test(expected = UserCannotDeleteSuperAdminException.class)
    public void testDelete_Admin_SuperAdminsCannotBeDeleted() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        HmhbUser userInDb = new HmhbUser();
        userInDb.setId(USER_ID);
        userInDb.setAdmin(true);
        userInDb.setSuperAdmin(true);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(userInDb);

        /* Make the call. */
        toTest.delete(USER_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDelete_Admin_UserNotFound() throws Exception {

        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(true);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(true);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(null);

        /* Make the call. */
        toTest.delete(USER_ID);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testDelete_NotLoggedIn() throws Exception {
        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(null);

        /* Make the call. */
        toTest.delete(USER_ID);
    }

    @Test(expected = UserNotAllowedToAccessOtherProfileException.class)
    public void testDelete_NonAdmin_AccessingOtherProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(OTHER_USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(OTHER_EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);

        /* Make the call. */
        toTest.delete(USER_ID);
    }

    @Test
    public void testDelete_NonAdmin_AccessingOwnProfile() {
        HmhbUser loggedInUser = new HmhbUser();
        loggedInUser.setId(USER_ID);
        loggedInUser.setSuperAdmin(false);
        loggedInUser.setAdmin(false);
        loggedInUser.setEmail(EMAIL);

        /* Train the mocks. */
        when(authorizationService.isAdmin()).thenReturn(false);
        when(authorizationService.getLoggedInUser()).thenReturn(loggedInUser);
        when(dao.findOne(USER_ID)).thenReturn(loggedInUser);

        /* Make the call. */
        HmhbUser actual = toTest.delete(USER_ID);

        /* Verify the results. */
        assertEquals(loggedInUser, actual);
        verify(dao).delete(USER_ID);
    }

}
