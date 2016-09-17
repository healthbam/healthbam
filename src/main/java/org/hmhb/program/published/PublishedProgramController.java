package org.hmhb.program.published;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
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

@RestController
public class PublishedProgramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishedProgramController.class);

    private final PublishedProgramService service;

    @Autowired
    public PublishedProgramController(
            @Nonnull PublishedProgramService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/published-programs"
    )
    public List<PublishedProgram> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/published-programs/{id}"
    )
    public PublishedProgram getById(
            @PathVariable long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        return service.getById(id);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/published-programs"
    )
    public PublishedProgram create(
            @RequestBody PublishedProgram program
    ) {
        LOGGER.debug("create called: program={}", program);
        return service.save(program);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/published-programs/{id}"
    )
    public PublishedProgram update(
            @PathVariable long id,
            @RequestBody PublishedProgram program
    ) {
        LOGGER.debug("update called: id={}, program={}", id, program);
        return service.save(program);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/published-programs/{id}"
    )
    public PublishedProgram delete(
            @PathVariable long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        return service.delete(id);
    }

}
