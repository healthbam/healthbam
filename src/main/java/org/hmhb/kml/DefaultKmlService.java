package org.hmhb.kml;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hmhb.county.County;
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

    // You can validate the KML with:
    // http://www.feedvalidator.org/check.cgi?url=https%3A%2F%2Fhmhb.herokuapp.com%2Fapi%2Fkml%3FprogramIds%3D999

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultKmlService.class);

    private final ProgramService programService;

    @Autowired
    public DefaultKmlService(
            @Nonnull ProgramService programService
    ) {
        LOGGER.debug("constructed");
        this.programService = requireNonNull(programService, "programService cannot be null");
    }

    private List<KmlStyle> getStyles() {
        KmlStyle purpleShade = new KmlStyle(
                "purpleShade",
                new KmlPolyStyle(
                        "66b73a67",
                        "normal",
                        "1",
                        "1"
                )
        );

        KmlStyle purpleBalloon = new KmlStyle(
                "purpleBalloon",
                new KmlIconStyle(
                        new KmlIcon(
                                // TODO - push this into a config or something
                                "https://hmhb.herokuapp.com/images/place.png"
                        )
                )
        );

        return Arrays.asList(
                purpleShade,
                purpleBalloon
        );
    }

    private String getDescription(Program program) {
        return String.format(
                "<p>Founded in %s.</p>",
                program.getStartYear()
        );
    }

    private KmlRoot convertToKml(List<Program> programs) {
        List<KmlPlacemark> programPlacemarks = new ArrayList<>();

        Set<County> counties = new HashSet<>();

        for (Program program : programs) {

            counties.addAll(program.getCountiesServed());

            programPlacemarks.add(
                    new KmlPlacemark(
                            "program-" + program.getId(),
                            program.getName(),
                            "#purpleBalloon",
                            getDescription(program),
                            new KmlPoint(
                                    program.getCoordinates()
                            )
                    )
            );
        }

        List<KmlPlacemark> countyPlacemarks = new ArrayList<>();
        for (County county : counties) {
            countyPlacemarks.add(
                    new KmlPlacemark(
                            "county-" + county.getId(),
                            county.getName(),
                            "#purpleShade",
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

    private List<Long> getProgramIds(String programIdsString) {
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

        List<Long> programIds = getProgramIds(programIdsString);
        List<Program> programs = programService.getByIds(programIds);
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
