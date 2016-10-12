package org.hmhb.kml;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hmhb.county.County;
import org.hmhb.county.CountyService;
import org.hmhb.kml.jaxb.KmlDocument;
import org.hmhb.kml.jaxb.KmlIcon;
import org.hmhb.kml.jaxb.KmlIconStyle;
import org.hmhb.kml.jaxb.KmlLinearRing;
import org.hmhb.kml.jaxb.KmlOuterBoundary;
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.kml.jaxb.KmlPoint;
import org.hmhb.kml.jaxb.KmlPolyStyle;
import org.hmhb.kml.jaxb.KmlPolygon;
import org.hmhb.kml.jaxb.KmlRoot;
import org.hmhb.kml.jaxb.KmlStyle;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultKmlService implements KmlService {

    private static final String COVERED_COUNTY = "coveredCounty";
    private static final String UNCOVERED_COUNTY = "uncoveredCounty";
    private static final String PROGRAM = "program";

    // You can validate the KML with:
    // http://www.feedvalidator.org/check.cgi?url=https%3A%2F%2Fhmhb.herokuapp.com%2Fapi%2Fkml%3FprogramIds%3D999

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultKmlService.class);

    private final CountyService countyService;
    private final ProgramService programService;

    @Autowired
    public DefaultKmlService(
            @Nonnull CountyService countyService,
            @Nonnull ProgramService programService
    ) {
        LOGGER.debug("constructed");
        this.countyService = requireNonNull(countyService, "countyService cannot be null");
        this.programService = requireNonNull(programService, "programService cannot be null");
    }

    private List<KmlStyle> getStyles() {
        KmlStyle covered = new KmlStyle(
                COVERED_COUNTY,
                new KmlPolyStyle(
                        "88b73a67",
                        "normal",
                        "1",
                        "1"
                )
        );

        KmlStyle uncovered = new KmlStyle(
                UNCOVERED_COUNTY,
                new KmlPolyStyle(
                        "449e9e9e",
                        "normal",
                        "1",
                        "1"
                )
        );

        KmlStyle pin = new KmlStyle(
                PROGRAM,
                new KmlIconStyle(
                        new KmlIcon(
                                // TODO - push this into a config or something
                                "https://hmhb.herokuapp.com/images/place.png"
                        )
                )
        );

        return Arrays.asList(
                covered,
                uncovered,
                pin
        );
    }

    private String getDescription(
            @Nonnull Program program
    ) {
        requireNonNull(program, "program cannot be null");

        return String.format(
                "<p>Founded in %s.</p>",
                program.getStartYear()
        );
    }

    private KmlRoot convertToKml(
            @Nonnull List<Program> programs
    ) {
        requireNonNull(programs, "programs cannot be null");

        List<KmlPlacemark> programPlacemarks = new ArrayList<>();

        Set<County> allCounties = new HashSet<>(
                countyService.getAll()
        );
        Set<County> shadedCounties = new HashSet<>();

        for (Program program : programs) {

            shadedCounties.addAll(program.getCountiesServed());

            programPlacemarks.add(
                    new KmlPlacemark(
                            "program-" + program.getId(),
                            program.getName(),
                            "#" + PROGRAM,
                            getDescription(program),
                            new KmlPoint(
                                    program.getCoordinates()
                            )
                    )
            );

        }

        List<KmlPlacemark> countyPlacemarks = new ArrayList<>();
        for (County county : allCounties) {

            String countyStyle;

            if (shadedCounties.contains(county)) {
                countyStyle = "#" + COVERED_COUNTY;
            } else {
                countyStyle = "#" + UNCOVERED_COUNTY;
            }

            countyPlacemarks.add(
                    new KmlPlacemark(
                            "county-" + county.getId(),
                            county.getName(),
                            countyStyle,
                            new KmlPolygon(
                                    new KmlOuterBoundary(
                                            new KmlLinearRing(
                                                    county.getShape()
                                            )
                                    )
                            )
                    )
            );

        }

        List<KmlPlacemark> allPlacemarks = new ArrayList<>();
        allPlacemarks.addAll(programPlacemarks);
        allPlacemarks.addAll(countyPlacemarks);

        return new KmlRoot(
                new KmlDocument(
                        "HMHB",
                        "Making an impact on the Health of Babies And Mothers!",
                        getStyles(),
                        allPlacemarks
                )
        );
    }

    private List<Long> getProgramIds(
            @Nonnull String programIdsString
    ) {
        requireNonNull(programIdsString, "programIdsString cannot be null");

        List<String> programStringIds = Arrays.asList(programIdsString.split("[,]"));
        List<Long> programIds = new ArrayList<>();
        for (String progIdStr : programStringIds) {
            programIds.add(Long.parseLong(progIdStr));
        }
        return programIds;
    }

    @Override
    public String getKml(
            @Nonnull String programIdsString
    ) {
        LOGGER.debug("getKml called: programIdsString={}", programIdsString);
        requireNonNull(programIdsString, "programIdsString cannot be null");

        List<Program> programs;

        if (programIdsString.isEmpty()) {
            programs = Collections.emptyList();

        } else {
            List<Long> programIds = getProgramIds(programIdsString);
            programs = programService.getByIds(programIds);

        }

        LOGGER.debug("programs found: {}", programs);

        KmlRoot kml = convertToKml(programs);

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(KmlRoot.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();

            jaxbMarshaller.marshal(kml, stringWriter);

            return stringWriter.toString();

        } catch (JAXBException e) {
            throw new RuntimeException("Failed to generate KML for programIds: " + programIdsString, e);
        }
    }

}
