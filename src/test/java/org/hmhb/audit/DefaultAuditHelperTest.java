package org.hmhb.audit;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link DefaultAuditHelper}.
 */
public class DefaultAuditHelperTest {

    private DefaultAuditHelper toTest;

    @Before
    public void setUp() throws Exception {
        toTest = new DefaultAuditHelper();
    }

    @Test
    public void testGetCallerIp() throws Exception {
        String expected = "127.0.0.1";

        /* Make the call. */
        String actual = toTest.getCallerIp();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        String expected = "someone";

        /* Make the call. */
        String actual = toTest.getCurrentUser();

        /* Verify the results. */
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentTime() throws Exception {
        Date now = new Date();

        /* Make the call. */
        Date actual = toTest.getCurrentTime();

        /* Verify the results. */
        assertTrue(actual.getTime() >= now.getTime());
    }

}
