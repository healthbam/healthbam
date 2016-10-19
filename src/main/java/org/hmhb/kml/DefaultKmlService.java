package org.hmhb.kml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.kml.jaxb.KmlPolyStyle;
import org.hmhb.kml.jaxb.KmlRoot;
import org.hmhb.kml.jaxb.KmlStyle;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
import org.hmhb.url.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultKmlService implements KmlService {

    private static final String COVERED_COUNTY_STYLE = "coveredCounty";
    private static final String UNCOVERED_COUNTY_STYLE = "uncoveredCounty";
    private static final String PROGRAM_STYLE = "program";

    // You can validate the KML with:
    // http://www.feedvalidator.org/check.cgi?url=https%3A%2F%2Fhmhb.herokuapp.com%2Fapi%2Fkml%3FprogramIds%3D999

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultKmlService.class);

    private final UrlService urlService;
    private final KmlPlacemarkService placemarkService;
    private final CountyService countyService;
    private final ProgramService programService;

    @Autowired
    public DefaultKmlService(
            @Nonnull UrlService urlService,
            @Nonnull KmlPlacemarkService placemarkService,
            @Nonnull CountyService countyService,
            @Nonnull ProgramService programService
    ) {
        LOGGER.debug("constructed");
        this.urlService = requireNonNull(urlService, "urlService cannot be null");
        this.placemarkService = requireNonNull(placemarkService, "placemarkService cannot be null");
        this.countyService = requireNonNull(countyService, "countyService cannot be null");
        this.programService = requireNonNull(programService, "programService cannot be null");
    }

    private List<KmlStyle> getStyles() {
        KmlStyle covered = new KmlStyle(
                COVERED_COUNTY_STYLE,
                new KmlPolyStyle(
                        "88b73a67",
                        "normal",
                        "1",
                        "1"
                )
        );

        KmlStyle uncovered = new KmlStyle(
                UNCOVERED_COUNTY_STYLE,
                new KmlPolyStyle(
                        "449e9e9e",
                        "normal",
                        "1",
                        "1"
                )
        );

        KmlStyle pin = new KmlStyle(
                PROGRAM_STYLE,
                new KmlIconStyle(
                        new KmlIcon(
                                urlService.getUrlPrefix() + "/images/place.png"
                        )
                )
        );

        return Arrays.asList(
                covered,
                uncovered,
                pin
        );
    }

    private KmlRoot convertToKml(
            @Nullable Long countyId,
            @Nonnull List<Program> programs
    ) {
        requireNonNull(programs, "programs cannot be null");

        List<KmlPlacemark> programPlacemarks = new ArrayList<>();

        Set<County> allCounties = new HashSet<>(
                countyService.getAll()
        );
        Set<County> shadedCounties = new HashSet<>();

        if (countyId != null) {
            for (County county : allCounties) {
                if (countyId.equals(county.getId())) {
                    shadedCounties.add(county);
                }
            }
        }

        for (Program program : programs) {

            if (countyId == null) {
                if (program.isServesAllCounties()) {
                    shadedCounties = allCounties;
                } else {
                    shadedCounties.addAll(program.getCountiesServed());
                }
            }

            programPlacemarks.add(
                    placemarkService.createProgramPlacemark(program, "#" + PROGRAM_STYLE)
            );

        }

        List<KmlPlacemark> countyPlacemarks = new ArrayList<>();
        for (County county : allCounties) {

            String countyStyle;

            if (shadedCounties.contains(county)) {
                countyStyle = "#" + COVERED_COUNTY_STYLE;
            } else {
                countyStyle = "#" + UNCOVERED_COUNTY_STYLE;
            }

            countyPlacemarks.add(
                    placemarkService.createCountyPlacemark(
                            countyStyle,
                            county
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

    private List<Long> getIds(
            @Nonnull String idsString
    ) {
        requireNonNull(idsString, "idsString cannot be null");

        List<String> stringIds = Arrays.asList(idsString.split("[,]"));
        List<Long> ids = new ArrayList<>();
        for (String idStr : stringIds) {
            ids.add(Long.parseLong(idStr));
        }
        return ids;
    }

    @Override
    public String getKml(
            @Nonnull String countyIdString,
            @Nonnull String programIdsString
    ) {
        LOGGER.debug("getKml called: countyIdString={}, programIdsString={}", countyIdString, programIdsString);
        requireNonNull(countyIdString, "countyIdString cannot be null");
        requireNonNull(programIdsString, "programIdsString cannot be null");

        Long countyId = null;

        if (!countyIdString.isEmpty()) {
            countyId = Long.parseLong(countyIdString);
        }

        List<Program> programs;

        if (programIdsString.isEmpty()) {
            programs = Collections.emptyList();

        } else {
            List<Long> programIds = getIds(programIdsString);
            programs = programService.getByIds(programIds);

        }

        LOGGER.debug("programs found: {}", programs);

        KmlRoot kml = convertToKml(countyId, programs);

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
