package org.hmhb.program;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.program.ProgramIdMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint for {@link Program} objects.
 */
@RestController
public class ProgramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

    private final ProgramService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link ProgramService} for saving, deleting, and
     *                retrieving {@link Program}
     */
    @Autowired
    public ProgramController(
            @Nonnull ProgramService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Retrieves all {@link Program}s in the system.
     *
     * @return all {@link Program}s
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/programs"
    )
    public List<Program> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    /**
     * Retrieves a {@link Program} by its database ID.
     *
     * @param id the database ID
     * @return the {@link Program}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/programs/{id}"
    )
    public Program getById(
            @PathVariable long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        return service.getById(id);
    }

    /**
     * Creates a new {@link Program}.
     *
     * @param program the new {@link Program} to create
     * @return the newly created {@link Program}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/programs"
    )
    public Program create(
            @RequestBody Program program
    ) {
        LOGGER.debug("create called: program={}", program);
        return service.save(program);
    }

    /**
     * Updates an existing {@link Program}.
     *
     * @param id the database of the {@link Program} to update
     * @param program the {@link Program} to update to
     * @return the updated {@link Program}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/programs/{id}"
    )
    public Program update(
            @PathVariable long id,
            @RequestBody Program program
    ) {
        LOGGER.debug("update called: id={}, program={}", id, program);

        if (!Long.valueOf(id).equals(program.getId())) {
            throw new ProgramIdMismatchException(id, program.getId());
        }

        return service.save(program);
    }

    /**
     * Deletes a {@link Program}.
     *
     * @param id the database ID of the {@link Program} to delete
     * @return the deleted {@link Program}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/programs/{id}"
    )
    public Program delete(
            @PathVariable long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        return service.delete(id);
    }

}
