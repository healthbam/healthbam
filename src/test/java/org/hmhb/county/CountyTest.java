package org.hmhb.county;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for {@link County}.
 */
public class CountyTest {

    private static final Long ID = 12345678901L;
    private static final String NAME = "unit-test-name";
    private static final String STATE = "unit-test-state";
    private static final String GEO_SHAPE = "unit-test-coordinates";

    @Test
    public void testToString() throws Exception {
        County county = new County();
        county.setId(ID);
        county.setName(NAME);
        county.setState(STATE);
        county.setShape(GEO_SHAPE);

        assertTrue(county.toString().contains(ID.toString()));
        assertTrue(county.toString().contains(NAME));
        assertTrue(county.toString().contains(STATE));
        /* Don't include the shape, it is too spammy in the logs. */
        assertFalse(county.toString().contains(GEO_SHAPE));
    }

}
