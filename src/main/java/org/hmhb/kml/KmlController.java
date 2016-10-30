package org.hmhb.kml;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint to serve KML for Google Maps.
 */
@RestController
public class KmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KmlController.class);

    private final KmlService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link KmlService} to generate KML
     */
    @Autowired
    public KmlController(
            @Nonnull KmlService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Generate KML for the passed in county ID and program IDs.
     *
     * @param countyId the county ID
     * @param programIds the comma delimited list of program IDs
     * @return the generated KML
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            path = "/api/kml",
            params = {
                    "countyId",
                    "programIds"
            }
    )
    public String getKml(
            @RequestParam("countyId") String countyId,
            @RequestParam("programIds") String programIds
    ) {
        LOGGER.debug("getKml called: countyId={}, programIds={}", countyId, programIds);
        return service.getKml(countyId, programIds);
    }

}
