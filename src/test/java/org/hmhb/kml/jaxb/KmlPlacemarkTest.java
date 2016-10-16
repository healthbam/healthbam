package org.hmhb.kml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link KmlPlacemark} to prove we can handle enough of a
 * subset of KML to support all GA counties.
 */
public class KmlPlacemarkTest {

    private Marshaller jaxbMarshaller;
    private StringWriter stringWriter;

    @Before
    public void setUp() throws Exception{
        System.setProperty(
                "javax.xml.bind.context.factory",
                JAXBContextFactory.class.getName()
        );
        JAXBContext jaxbContext = JAXBContext.newInstance(KmlRoot.class);

        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        stringWriter = new StringWriter();
    }

    @Test
    public void testPin() throws Exception {
        String id = "test-id";
        String name = "test-name";
        String styleUrl = "test-style";
        String description = "test-description";
        String coordinates = "1.000,-1.000";

        KmlPlacemark placemark = new KmlPlacemark(
                id,
                name,
                styleUrl,
                description,
                new KmlPoint(coordinates)
        );

        KmlRoot root = new KmlRoot(
                new KmlDocument(
                        "HMHB",
                        "Making an impact on the Health of Babies And Mothers!",
                        Collections.<KmlStyle>emptyList(),
                        Collections.singletonList(placemark)
                )
        );

        /* Make the call. */
        jaxbMarshaller.marshal(root, stringWriter);
        String actual = stringWriter.toString();

        /* Verify the results. */

        /* id */
        String expectedId = String.format("<Placemark id=\"%s\">", id);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedId, actual),
                actual.contains(expectedId)
        );

        /* style */
        String expectedStyle = String.format("<styleUrl>%s</styleUrl", styleUrl);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedStyle, actual),
                actual.contains(expectedStyle)
        );

        /* name */
        String expectedName = String.format("<name>%s</name>", name);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedName, actual),
                actual.contains(expectedName)
        );

        /* description */
        String expectedDescription = String.format("<description><![CDATA[%s]]></description>", description);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedDescription, actual),
                actual.contains(expectedDescription)
        );

        /* coordinates */
        String expectedCoordinates = String.format("<coordinates>%s</coordinates>", coordinates);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates, actual),
                actual.contains(expectedCoordinates)
        );

        /* type */
        assertTrue(
                String.format("actual didn't contain \"<Point>\": actual=%s", actual),
                actual.contains("<Point>")
        );
    }

    @Test
    public void testNormalShadedPolygon() throws Exception {
        String id = "test-id";
        String name = "test-name";
        String styleUrl = "test-style";
        String coordinates = "1.000,-1.000";

        KmlPlacemark placemark =  new KmlPlacemark(
                id,
                name,
                styleUrl,
                new KmlPolygon(
                        new KmlOuterBoundary(
                                new KmlLinearRing(coordinates)
                        )
                )
        );

        KmlRoot root = new KmlRoot(
                new KmlDocument(
                        "HMHB",
                        "Making an impact on the Health of Babies And Mothers!",
                        Collections.<KmlStyle>emptyList(),
                        Collections.singletonList(placemark)
                )
        );

        /* Make the call. */
        jaxbMarshaller.marshal(root, stringWriter);
        String actual = stringWriter.toString();

        /* Verify the results. */

        /* id */
        String expectedId = String.format("<Placemark id=\"%s\">", id);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedId, actual),
                actual.contains(expectedId)
        );

        /* style */
        String expectedStyle = String.format("<styleUrl>%s</styleUrl", styleUrl);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedStyle, actual),
                actual.contains(expectedStyle)
        );

        /* name */
        String expectedName = String.format("<name>%s</name>", name);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedName, actual),
                actual.contains(expectedName)
        );

        /* coordinates */
        String expectedCoordinates = String.format("<coordinates>%s</coordinates>", coordinates);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates, actual),
                actual.contains(expectedCoordinates)
        );

        /* type */
        assertTrue(
                String.format("actual didn't contain \"<Polygon>\": actual=%s", actual),
                actual.contains("<Polygon>")
        );
        assertTrue(
                String.format("actual didn't contain \"<LinearRing>\": actual=%s", actual),
                actual.contains("<LinearRing>")
        );
        assertTrue(
                String.format("actual didn't contain \"<outerBoundaryIs>\": actual=%s", actual),
                actual.contains("<outerBoundaryIs>")
        );
        assertFalse(
                String.format("actual didn't contain \"<innerBoundaryIs>\": actual=%s", actual),
                actual.contains("<innerBoundaryIs>")
        );
        assertFalse(
                String.format("actual didn't contain \"<MultiGeometry>\": actual=%s", actual),
                actual.contains("<MultiGeometry>")
        );
    }

    @Test
    public void testShadedPolygonWithHole() throws Exception {
        String id = "test-id";
        String name = "test-name";
        String styleUrl = "test-style";
        String coordinates1 = "1.000,-1.000";
        String coordinates2 = "2.000,-2.000";

        KmlPlacemark placemark =  new KmlPlacemark(
                id,
                name,
                styleUrl,
                new KmlPolygon(
                        new KmlOuterBoundary(
                                new KmlLinearRing(coordinates1)
                        ),
                        new KmlInnerBoundary(
                                new KmlLinearRing(coordinates2)
                        )
                )
        );

        KmlRoot root = new KmlRoot(
                new KmlDocument(
                        "HMHB",
                        "Making an impact on the Health of Babies And Mothers!",
                        Collections.<KmlStyle>emptyList(),
                        Collections.singletonList(placemark)
                )
        );

        /* Make the call. */
        jaxbMarshaller.marshal(root, stringWriter);
        String actual = stringWriter.toString();

        /* Verify the results. */

        /* id */
        String expectedId = String.format("<Placemark id=\"%s\">", id);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedId, actual),
                actual.contains(expectedId)
        );

        /* style */
        String expectedStyle = String.format("<styleUrl>%s</styleUrl", styleUrl);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedStyle, actual),
                actual.contains(expectedStyle)
        );

        /* name */
        String expectedName = String.format("<name>%s</name>", name);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedName, actual),
                actual.contains(expectedName)
        );

        /* coordinates1 */
        String expectedCoordinates1 = String.format("<coordinates>%s</coordinates>", coordinates1);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates1, actual),
                actual.contains(expectedCoordinates1)
        );

        /* coordinates2 */
        String expectedCoordinates2 = String.format("<coordinates>%s</coordinates>", coordinates2);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates2, actual),
                actual.contains(expectedCoordinates2)
        );

        /* type */
        assertTrue(
                String.format("actual didn't contain \"<Polygon>\": actual=%s", actual),
                actual.contains("<Polygon>")
        );
        assertTrue(
                String.format("actual didn't contain \"<LinearRing>\": actual=%s", actual),
                actual.contains("<LinearRing>")
        );
        assertTrue(
                String.format("actual didn't contain \"<outerBoundaryIs>\": actual=%s", actual),
                actual.contains("<outerBoundaryIs>")
        );
        assertTrue(
                String.format("actual didn't contain \"<innerBoundaryIs>\": actual=%s", actual),
                actual.contains("<innerBoundaryIs>")
        );
        assertFalse(
                String.format("actual didn't contain \"<MultiGeometry>\": actual=%s", actual),
                actual.contains("<MultiGeometry>")
        );
    }

    @Test
    public void testMultipleShadedPolygons() throws Exception {
        String id = "test-id";
        String name = "test-name";
        String styleUrl = "test-style";
        String coordinates1 = "1.000,-1.000";
        String coordinates2 = "2.000,-2.000";
        String coordinates3 = "3.000,-3.000";

        KmlPlacemark placemark =  new KmlPlacemark(
                id,
                name,
                styleUrl,
                new KmlMuliGeometry(
                        Arrays.asList(
                                new KmlPolygon(
                                        new KmlOuterBoundary(
                                                new KmlLinearRing(coordinates1)
                                        )
                                ),
                                new KmlPolygon(
                                        new KmlOuterBoundary(
                                                new KmlLinearRing(coordinates2)
                                        )
                                ),
                                new KmlPolygon(
                                        new KmlOuterBoundary(
                                                new KmlLinearRing(coordinates3)
                                        )
                                )
                        )
                )
        );

        KmlRoot root = new KmlRoot(
                new KmlDocument(
                        "HMHB",
                        "Making an impact on the Health of Babies And Mothers!",
                        Collections.<KmlStyle>emptyList(),
                        Collections.singletonList(placemark)
                )
        );

        /* Make the call. */
        jaxbMarshaller.marshal(root, stringWriter);
        String actual = stringWriter.toString();

        /* Verify the results. */

        /* id */
        String expectedId = String.format("<Placemark id=\"%s\">", id);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedId, actual),
                actual.contains(expectedId)
        );

        /* style */
        String expectedStyle = String.format("<styleUrl>%s</styleUrl", styleUrl);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedStyle, actual),
                actual.contains(expectedStyle)
        );

        /* name */
        String expectedName = String.format("<name>%s</name>", name);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedName, actual),
                actual.contains(expectedName)
        );

        /* coordinates1 */
        String expectedCoordinates1 = String.format("<coordinates>%s</coordinates>", coordinates1);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates1, actual),
                actual.contains(expectedCoordinates1)
        );

        /* coordinates2 */
        String expectedCoordinates2 = String.format("<coordinates>%s</coordinates>", coordinates2);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates2, actual),
                actual.contains(expectedCoordinates2)
        );

        /* coordinates2 */
        String expectedCoordinates3 = String.format("<coordinates>%s</coordinates>", coordinates3);
        assertTrue(
                String.format("actual didn't contain \"%s\": actual=%s", expectedCoordinates3, actual),
                actual.contains(expectedCoordinates3)
        );

        /* type */
        assertTrue(
                String.format("actual didn't contain \"<Polygon>\": actual=%s", actual),
                actual.contains("<Polygon>")
        );
        assertTrue(
                String.format("actual didn't contain \"<LinearRing>\": actual=%s", actual),
                actual.contains("<LinearRing>")
        );
        assertTrue(
                String.format("actual didn't contain \"<outerBoundaryIs>\": actual=%s", actual),
                actual.contains("<outerBoundaryIs>")
        );
        assertFalse(
                String.format("actual didn't contain \"<innerBoundaryIs>\": actual=%s", actual),
                actual.contains("<innerBoundaryIs>")
        );
        assertTrue(
                String.format("actual didn't contain \"<MultiGeometry>\": actual=%s", actual),
                actual.contains("<MultiGeometry>")
        );
    }

}
