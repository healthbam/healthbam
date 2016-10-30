package org.hmhb.mapquery;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint for {@link MapQuery} objects.
 */
@RestController
public class MapQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapQueryController.class);

    private MapQueryService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link MapQueryService} to search with
     */
    @Autowired
    public MapQueryController(
            @Nonnull MapQueryService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Searches for programs matching the provided criteria.
     *
     * @param mapQuery {@link MapQuery} containing {@link MapQuerySearch} criteria.
     * @return {@link MapQuery} with filled in {@link MapQueryResult} object.
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/map-queries"
    )
    public MapQuery search(
            @RequestBody MapQuery mapQuery
    ) {
        LOGGER.debug("search called: mapQuery={}", mapQuery);
        return service.search(mapQuery);
    }

}
