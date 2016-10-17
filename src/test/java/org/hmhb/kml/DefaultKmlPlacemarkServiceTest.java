package org.hmhb.kml;

import org.hmhb.county.County;
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.organization.Organization;
import org.hmhb.program.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link DefaultKmlPlacemarkService}.
 */
public class DefaultKmlPlacemarkServiceTest {

    private DefaultKmlPlacemarkService toTest;

    @Before
    public void setUp() throws Exception {
        toTest = new DefaultKmlPlacemarkService();
    }

    @Test
    public void testCreateCountyPlacemarkNormal() throws Exception {
        String style = "test-style";
        Long id = 123L;
        String name = "test-name";
        String coordinates = "1.000,-1.000";

        County county = new County();
        county.setId(id);
        county.setName(name);
        county.setOuterBoundary1(coordinates);

        /* Make the call. */
        KmlPlacemark actual = toTest.createCountyPlacemark(style, county);

        assertEquals(
                "county-" + id,
                actual.getId()
        );
        assertEquals(
                name,
                actual.getName()
        );
        assertEquals(
                style,
                actual.getStyleUrl()
        );
        assertEquals(
                coordinates,
                actual.getPolygon().getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
    }

    @Test
    public void testCreateCountyPlacemark2Polygons() throws Exception {
        String style = "test-style";
        Long id = 123L;
        String name = "test-name";
        String coordinates1 = "1.000,-1.000";
        String coordinates2 = "2.000,-2.000";

        County county = new County();
        county.setId(id);
        county.setName(name);
        county.setOuterBoundary1(coordinates1);
        county.setOuterBoundary2(coordinates2);

        /* Make the call. */
        KmlPlacemark actual = toTest.createCountyPlacemark(style, county);

        assertEquals(
                "county-" + id,
                actual.getId()
        );
        assertEquals(
                name,
                actual.getName()
        );
        assertEquals(
                style,
                actual.getStyleUrl()
        );
        assertEquals(
                coordinates1,
                actual.getMuliGeometry().getPolygons().get(0).getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
        assertEquals(
                coordinates2,
                actual.getMuliGeometry().getPolygons().get(1).getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
    }

    @Test
    public void testCreateCountyPlacemark3Polygons() throws Exception {
        String style = "test-style";
        Long id = 123L;
        String name = "test-name";
        String coordinates1 = "1.000,-1.000";
        String coordinates2 = "2.000,-2.000";
        String coordinates3 = "3.000,-3.000";

        County county = new County();
        county.setId(id);
        county.setName(name);
        county.setOuterBoundary1(coordinates1);
        county.setOuterBoundary2(coordinates2);
        county.setOuterBoundary3(coordinates3);

        /* Make the call. */
        KmlPlacemark actual = toTest.createCountyPlacemark(style, county);

        assertEquals(
                "county-" + id,
                actual.getId()
        );
        assertEquals(
                name,
                actual.getName()
        );
        assertEquals(
                style,
                actual.getStyleUrl()
        );
        assertEquals(
                coordinates1,
                actual.getMuliGeometry().getPolygons().get(0).getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
        assertEquals(
                coordinates2,
                actual.getMuliGeometry().getPolygons().get(1).getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
        assertEquals(
                coordinates3,
                actual.getMuliGeometry().getPolygons().get(2).getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
    }

    @Test
    public void testCreateCountyPlacemarkHole() throws Exception {
        String style = "test-style";
        Long id = 123L;
        String name = "test-name";
        String coordinates1 = "1.000,-1.000";
        String coordinates2 = "2.000,-2.000";

        County county = new County();
        county.setId(id);
        county.setName(name);
        county.setOuterBoundary1(coordinates1);
        county.setInnerBoundary1(coordinates2);

        /* Make the call. */
        KmlPlacemark actual = toTest.createCountyPlacemark(style, county);

        assertEquals(
                "county-" + id,
                actual.getId()
        );
        assertEquals(
                name,
                actual.getName()
        );
        assertEquals(
                style,
                actual.getStyleUrl()
        );
        assertEquals(
                coordinates1,
                actual.getPolygon().getOuterBoundaryIs().getLinearRing().getCoordinates()
        );
        assertEquals(
                coordinates2,
                actual.getPolygon().getInnerBoundaryIs().getLinearRing().getCoordinates()
        );
    }

    @Test
    public void testCreateProgramPlacemark() throws Exception {
        String style = "#test-style";
        Long id = 123L;
        String name = "test-name";
        Integer startYear = 1999;
        String coordinates = "1.000,-1.000";

        Organization organization = new Organization();
        organization.setId(12345L);
        organization.setName("test-organization");

        Program program = new Program();
        program.setId(id);
        program.setName(name);
        program.setStartYear(startYear);
        program.setCoordinates(coordinates);
        program.setOrganization(organization);

        /* Make the call. */
        KmlPlacemark actual = toTest.createProgramPlacemark(program, style);

        /* Verify the results. */
        assertEquals(
                "program-" + id,
                actual.getId()
        );
        assertEquals(
                name,
                actual.getName()
        );
        assertEquals(
                style,
                actual.getStyleUrl()
        );
        assertEquals(
                coordinates,
                actual.getPoint().getCoordinates()
        );
        assertTrue(
                "description didn't contain startYear: " + program,
                actual.getDescription().contains(organization.getName())
        );
    }

    @Test(expected = NullPointerException.class)
    public void testCreateProgramPlacemarkNullProgram() throws Exception {
        toTest.createProgramPlacemark(null, "#any-style");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateProgramPlacemarkNullStyle() throws Exception {
        Long id = 123L;
        String name = "test-name";
        Integer startYear = 1999;
        String coordinates = "1.000,-1.000";

        Program program = new Program();
        program.setId(id);
        program.setName(name);
        program.setStartYear(startYear);
        program.setCoordinates(coordinates);

        toTest.createProgramPlacemark(program, null);
    }

}
