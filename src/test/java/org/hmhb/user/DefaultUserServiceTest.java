package org.hmhb.user;

import com.google.api.services.plus.model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultUserService}.
 */
public class DefaultUserServiceTest {

    private static final Long USER_ID = 123L;
    private static final String DISPLAY_NAME = "John Doe";
    private static final String EMAIL = "john.doe@mailinator.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String IMAGE_URL = "https://lh3.googleusercontent.com/-XdUIq/AAAI/AAAA/42cv5M/photo.jpg?sz=50";
    private static final String PROFILE_URL = "something";

    private UserDao dao;
    private DefaultUserService toTest;

    @Before
    public void setUp() throws Exception {
        dao = mock(UserDao.class);
        toTest = new DefaultUserService(dao);
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

        HmhbUser expected = new HmhbUser();
        expected.setId(USER_ID);
        expected.setAdmin(true);
        expected.setEmail(EMAIL);
        expected.setDisplayName(DISPLAY_NAME);
        expected.setFirstName(FIRST_NAME);
        expected.setLastName(LAST_NAME);
        expected.setImageUrl(IMAGE_URL);
        expected.setProfileUrl(PROFILE_URL);

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
        when(dao.findByEmailIgnoreCase(EMAIL)).thenReturn(null);
        when(dao.save(expected)).thenReturn(expected);

        /* Make the call. */
        HmhbUser actual = toTest.saveWithGoogleData(EMAIL, gPlusProfile);

        /* Verify the results. */
        assertEquals(expected, actual);
    }

}
