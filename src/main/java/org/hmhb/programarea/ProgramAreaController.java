package org.hmhb.programarea;

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
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint for {@link ProgramArea} objects.
 */
@RestController
public class ProgramAreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramAreaController.class);

    private ProgramAreaService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link ProgramAreaService} to retrieve
     *                {@link ProgramArea}s
     */
    @Autowired
    public ProgramAreaController(
            @Nonnull ProgramAreaService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Retrieves a {@link ProgramArea} by its database ID.
     *
     * @param id the database ID
     * @return the {@link ProgramArea}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/program-areas/{id}"
    )
    public ProgramArea getById(
            @PathVariable long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        return service.getById(id);
    }

    /**
     * Retrieves all {@link ProgramArea}s in the system.
     *
     * @return all {@link ProgramArea}s
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/program-areas"
    )
    public List<ProgramArea> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

}
