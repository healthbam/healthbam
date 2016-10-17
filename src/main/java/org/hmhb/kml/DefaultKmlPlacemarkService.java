package org.hmhb.kml;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

import org.hmhb.county.County;
import org.hmhb.kml.jaxb.KmlInnerBoundary;
import org.hmhb.kml.jaxb.KmlLinearRing;
import org.hmhb.kml.jaxb.KmlMuliGeometry;
import org.hmhb.kml.jaxb.KmlOuterBoundary;
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.kml.jaxb.KmlPoint;
import org.hmhb.kml.jaxb.KmlPolygon;
import org.hmhb.program.Program;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultKmlPlacemarkService implements KmlPlacemarkService {

    private KmlPolygon createSimplePolygon(
            @Nonnull String outerBoundary
    ) {
        requireNonNull(outerBoundary, "outerBoundary cannot be null");

        return new KmlPolygon(
                new KmlOuterBoundary(
                        new KmlLinearRing(
                                outerBoundary
                        )
                )
        );
    }

    @Override
    public KmlPlacemark createCountyPlacemark(
            @Nonnull String countyStyle,
            @Nonnull County county
    ) {
        requireNonNull(countyStyle, "countyStyle cannot be null");
        requireNonNull(county, "county cannot be null");

        String countyId = "county-" + county.getId();

        /* The county is missing an inner piece. */
        if (county.getInnerBoundary1() != null) {
            return new KmlPlacemark(
                    countyId,
                    county.getName(),
                    countyStyle,
                    new KmlPolygon(
                            new KmlOuterBoundary(
                                    new KmlLinearRing(
                                            county.getOuterBoundary1()
                                    )
                            ),
                            new KmlInnerBoundary(
                                    new KmlLinearRing(
                                            county.getInnerBoundary1()
                                    )
                            )
                    )
            );
        }

        /* The county has multiple pieces. */
        if (county.getOuterBoundary2() != null) {
            List<KmlPolygon> polygons = new ArrayList<>();
            polygons.add(
                    createSimplePolygon(county.getOuterBoundary1())
            );
            polygons.add(
                    createSimplePolygon(county.getOuterBoundary2())
            );
            if (county.getOuterBoundary3() != null) {
                polygons.add(
                        createSimplePolygon(county.getOuterBoundary3())
                );
            }
            return new KmlPlacemark(
                    countyId,
                    county.getName(),
                    countyStyle,
                    new KmlMuliGeometry(polygons)
            );
        }

        /* The county is a simple polygon. */
        return new KmlPlacemark(
                countyId,
                county.getName(),
                countyStyle,
                createSimplePolygon(county.getOuterBoundary1())
        );
    }

    private String getProgramDescription(
            @Nonnull Program program
    ) {
        requireNonNull(program, "program cannot be null");

        return String.format(
                "<p>%s</p><p><a href=\"views/program-details/%d\">More details</a></p>",
                program.getOrganization().getName(),
                program.getId()
        );
    }

    @Override
    public KmlPlacemark createProgramPlacemark(
            @Nonnull Program program,
            @Nonnull String styleId
    ) {
        requireNonNull(program, "program cannot be null");
        requireNonNull(styleId, "styleId cannot be null");

        return new KmlPlacemark(
                "program-" + program.getId(),
                program.getName(),
                styleId,
                getProgramDescription(program),
                new KmlPoint(
                        program.getCoordinates()
                )
        );
    }

}
