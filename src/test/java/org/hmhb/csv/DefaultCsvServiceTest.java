package org.hmhb.csv;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hmhb.county.County;
import org.hmhb.organization.Organization;
import org.hmhb.program.Program;
import org.hmhb.programarea.ProgramArea;
import org.hmhb.user.HmhbUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link DefaultCsvService}.
 */
public class DefaultCsvServiceTest {

    private static final String PROGRAM_HEADER = "org_name,org_email,org_phone,org_facebook,org_website,"
            + "prog_name,prog_year,prog_street,prog_city,prog_state,prog_zip,prog_coordinates,"
            + "prog_goal1,prog_goal2,prog_goal3,prog_outcome1,prog_outcome2,prog_outcome3,"
            + "prog_areas,prog_area_explanation,prog_counties\n";

    private static final String USER_HEADER = "email,super_admin,admin,display_name,"
            + "first_name,middle_name,last_name,prefix,suffix,"
            + "image_url,profile_url\n";

    private DefaultCsvService toTest;

    private Program program1;
    private Program program2;

    private HmhbUser user1;
    private HmhbUser user2;

    @Before
    public void setUp() {
        toTest = new DefaultCsvService();

        program1 = createFilledProgram("first-");

        program2 = createFilledProgram("second-");
        program2.setStartYear(2001);

        user1 = createdFilledUser("first-");

        user2 = createdFilledUser("second-");
        user2.setSuperAdmin(false);
        user2.setAdmin(false);
    }

    private HmhbUser createdFilledUser(String prefix) {
        HmhbUser user = new HmhbUser();

        user.setEmail(prefix + "email");
        user.setSuperAdmin(true);
        user.setAdmin(true);
        user.setDisplayName(prefix + "display-name");
        user.setFirstName(prefix + "first-name");
        user.setLastName(prefix + "last-name");
        user.setImageUrl(prefix + "image-url");

        return user;
    }

    private Program createFilledProgram(String prefix) {
        Program program = new Program();

        program.setCity(prefix + "prog-city");
        program.setCoordinates(prefix + "prog-coords");

        County county1 = new County();
        county1.setName(prefix + "county1");

        County county2 = new County();
        county2.setName(prefix + "county2");

        Set<County> counties = new LinkedHashSet<>();
        counties.add(county1);
        counties.add(county2);

        program.setCountiesServed(counties);
        program.setMeasurableOutcome1(prefix + "outcome1");
        program.setMeasurableOutcome2(prefix + "outcome2");
        program.setMeasurableOutcome3(prefix + "outcome3");
        program.setName(prefix + "prog-name");

        Organization org = new Organization();
        org.setName(prefix + "org-name");
        org.setContactEmail(prefix + "org-email");
        org.setContactPhone(prefix + "org-phone");
        org.setFacebookUrl(prefix + "org-facebook");
        org.setWebsiteUrl(prefix + "org-website");

        program.setOrganization(org);

        program.setOtherProgramAreaExplanation(prefix + "other");
        program.setPrimaryGoal1(prefix + "goal1");
        program.setPrimaryGoal2(prefix + "goal2");
        program.setPrimaryGoal3(prefix + "goal3");


        ProgramArea progArea1 = new ProgramArea();
        progArea1.setName(prefix + "prog-area1");

        ProgramArea progArea2 = new ProgramArea();
        progArea2.setName(prefix + "prog-area2");

        Set<ProgramArea> progAreas = new LinkedHashSet<>();
        progAreas.add(progArea1);
        progAreas.add(progArea2);

        program.setProgramAreas(progAreas);

        program.setOtherProgramAreaExplanation(prefix + "prog-area-other");

        program.setServesAllCounties(false);
        program.setStartYear(1999);
        program.setState("GA");
        program.setStreetAddress(prefix + "prog-street");
        program.setZipCode(prefix + "prog-zip");

        return program;
    }

    @Test(expected = NullPointerException.class)
    public void testGenerateFromPrograms_Null() throws Exception {
        /* Make the call. */
        toTest.generateFromPrograms(null, true, true);
    }

    @Test
    public void testGenerateFromPrograms_EmptyList() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.<Program>emptyList(), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER,
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_OneProgram() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_TwoPrograms() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Arrays.asList(program1, program2), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        /* first */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n"
                        /* second */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,2001,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "\"second-prog-area1,second-prog-area2\",second-prog-area-other,\"second-county1,second-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_CommaInNameField() throws Exception {
        program1.setName("Has a , in it");

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "\"Has a , in it\",1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_DoubleQuoteInNameField() throws Exception {
        program1.setName("Has a \" in it");

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "\"Has a \"\" in it\",1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_NullStartYear() throws Exception {
        program1.setStartYear(null);

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_SomeNullFields() throws Exception {
        program1.setPrimaryGoal2(null);
        program1.setPrimaryGoal3(null);
        program1.setMeasurableOutcome2(null);
        program1.setMeasurableOutcome3(null);

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,,,first-outcome1,,,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_ServesAllCounties() throws Exception {
        program1.setServesAllCounties(true);

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,ALL\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_ServesNoCounties() throws Exception {
        program1.getCountiesServed().clear();

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,\n",
                actual
        );
    }

    @Test
    public void testGenerateFromPrograms_NoProgramAreas() throws Exception {
        program1.getProgramAreas().clear();

        /* Make the call. */
        String actual = toTest.generateFromPrograms(Collections.singletonList(program1), null, null);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + ",first-prog-area-other,\"first-county1,first-county2\"\n",
                actual
        );
    }

    @Ignore
    @Test
    public void testGenerateFromPrograms_TwoPrograms_ExpandCounties() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Arrays.asList(program1, program2), true, false);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        /* first: county1 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,first-county1\n"
                        /* first: county2 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "\"first-prog-area1,first-prog-area2\",first-prog-area-other,first-county2\n"
                        /* second: county1 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,2001,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "\"second-prog-area1,second-prog-area2\",second-prog-area-other,second-county1\n"
                        /* second: county2 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,2001,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "\"second-prog-area1,second-prog-area2\",second-prog-area-other,second-county2\n",
                actual
        );
    }

    @Ignore
    @Test
    public void testGenerateFromPrograms_TwoPrograms_ExpandProgramAreas() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Arrays.asList(program1, program2), false, true);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        /* first: progArea1 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area1,first-prog-area-other,\"first-county1,first-county2\"\n"
                        /* first: progArea2 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area2,first-prog-area-other,\"first-county1,first-county2\"\n"
                        /* second: progArea1 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,2001,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area1,second-prog-area-other,\"second-county1,second-county2\"\n"
                        /* second: progArea2 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,2001,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area2,second-prog-area-other,\"second-county1,second-county2\"\n",
                actual
        );
    }

    @Ignore
    @Test
    public void testGenerateFromPrograms_TwoPrograms_ExpandCountiesAndProgramAreas() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromPrograms(Arrays.asList(program1, program2), true, true);

        /* Verify the results. */
        assertEquals(
                PROGRAM_HEADER
                        /* first: progArea1, county1 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area1,first-prog-area-other,first-county1\n"
                        /* first: progArea1, county2 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area1,first-prog-area-other,first-county2\n"
                        /* first: progArea2, county1 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area2,first-prog-area-other,first-county1\n"
                        /* first: progArea2, county2 */
                        + "first-org-name,first-org-email,first-org-phone,first-org-facebook,first-org-website,"
                        + "first-prog-name,1999,first-prog-street,first-prog-city,GA,first-prog-zip,first-prog-coords,"
                        + "first-goal1,first-goal2,first-goal3,first-outcome1,first-outcome2,first-outcome3,"
                        + "first-prog-area2,first-prog-area-other,first-county2\n"
                        /* second: progArea1, county1 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,1999,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area1,second-prog-area-other,second-county1\n"
                        /* second: progArea1, county2 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,1999,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area1,second-prog-area-other,second-county2\n"
                        /* second: progArea2, county1 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,1999,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area2,second-prog-area-other,second-county1\n"
                        /* second: progArea2, county2 */
                        + "second-org-name,second-org-email,second-org-phone,second-org-facebook,second-org-website,"
                        + "second-prog-name,1999,second-prog-street,second-prog-city,GA,second-prog-zip,second-prog-coords,"
                        + "second-goal1,second-goal2,second-goal3,second-outcome1,second-outcome2,second-outcome3,"
                        + "second-prog-area2,second-prog-area-other,second-county2\n",
                actual
        );
    }

    @Test(expected = NullPointerException.class)
    public void testGenerateFromUsers_Null() throws Exception {
        /* Make the call. */
        toTest.generateFromUsers(null);
    }

    @Test
    public void testGenerateFromUsers_EmptyList() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromUsers(Collections.<HmhbUser>emptyList());

        /* Verify the results. */
        assertEquals(
                USER_HEADER,
                actual
        );
    }

    @Test
    public void testGenerateFromUsers_OneUser() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromUsers(Collections.singletonList(user1));

        /* Verify the results. */
        assertEquals(
                USER_HEADER
                        + "first-email,true,true,first-display-name,"
                        + "first-first-name,,first-last-name,,,"
                        + "first-image-url,\n",
                actual
        );
    }

    @Test
    public void testGenerateFromUsers_TwoUsers() throws Exception {
        /* Make the call. */
        String actual = toTest.generateFromUsers(Arrays.asList(user1, user2));

        /* Verify the results. */
        assertEquals(
                USER_HEADER
                        /* first */
                        + "first-email,true,true,first-display-name,"
                        + "first-first-name,,first-last-name,,,"
                        + "first-image-url,\n"
                        /* second */
                        + "second-email,false,false,second-display-name,"
                        + "second-first-name,,second-last-name,,,"
                        + "second-image-url,\n",
                actual
        );
    }

}
