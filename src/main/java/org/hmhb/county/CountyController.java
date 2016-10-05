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

/**
 * REST endpoint for {@link County} objects.
 */
@RestController
public class CountyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountyController.class);

    private CountyService service;

    @Autowired
    public CountyController(
            @Nonnull CountyService service
    ) {
        LOGGER.debug("constructed");
        this.service = service;
    }

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

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/counties",
            params = {"name"}
    )
    public List<County> findByNameStartingWithIgnoreCase(
            @RequestParam(value = "name") String namePart
    ) {
        LOGGER.debug("findByNameStartingWithIgnoreCase called: namePart={}", namePart);
        return service.findByNameStartingWithIgnoreCase(namePart);
    }

}
