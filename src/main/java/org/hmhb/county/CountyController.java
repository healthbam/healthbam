package org.hmhb.county;

import java.util.List;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint for {@link County} objects.
 */
@RestController
public class CountyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountyController.class);

    private CountyService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link CountyService} to retieve {@link County}s
     */
    @Autowired
    public CountyController(
            @Nonnull CountyService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Retrieve a {@link County} by its ID.
     *
     * @param id the database ID of the {@link County}
     * @return the {@link County}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/counties/{id}"
    )
    public County getById(
            @PathVariable long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        return service.getById(id);
    }

    /**
     * Retrieves all {@link County}s in the system (Georgia).
     *
     * @return all {@link County}s
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/counties"
    )
    public List<County> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    /**
     * Retrieves all {@link County}s that match the passed in name.
     *
     * @param namePart the user's county search string to find matching
     *                 {@link County}s with
     * @return the matching {@link County}s
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/counties",
            params = {"name"}
    )
    public List<County> searchByName(
            @RequestParam(value = "name") String namePart
    ) {
        LOGGER.debug("searchByName called: namePart={}", namePart);
        return service.searchByName(namePart);
    }

}
