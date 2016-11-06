package org.hmhb.program;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.program.ProgramIdMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
     * Retrieves all {@link Program}s in the system and exports them to a
     * CSV file.
     *
     * @param expandCounties whether a program's counties should not be
     *                       flattened into one row (comma delimited)
     * @param expandProgramAreas whether a program's program areas should not
     *                           be flattened into one row (comma delimited)
     * @param response the {@link HttpServletResponse} to send the results to
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/api/programs/csv"
    )
    public void getAllAsCsv(
            @RequestParam (value = "expandCounties", required = false) Boolean expandCounties,
            @RequestParam (value = "expandProgramAreas", required = false) Boolean expandProgramAreas,
            HttpServletResponse response
    ) throws IOException {
        LOGGER.debug(
                "getAllAsCsv called: expandCounties={}, expandProgramAreas={}",
                expandCounties,
                expandProgramAreas
        );

        String csvContent = service.getAllAsCsv(expandCounties, expandProgramAreas);

        /* Must set the status and headers after the call succeeds or else confusing errors are passed back. */
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "text/csv");

        /* Have the browser prompt the user for download rather than try to open the csv. */
        response.setHeader("Content-Disposition", "attachment; filename=all-programs.csv");

        response.getWriter()
                .append(csvContent);
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
     * @param id the database ID of the {@link Program} to update
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
