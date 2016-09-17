package org.hmhb.program.requested;

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
public class RequestedProgramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestedProgramController.class);

    private final RequestedProgramService service;

    @Autowired
    public RequestedProgramController(
            @Nonnull RequestedProgramService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/requested-programs"
    )
    public List<RequestedProgram> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/requested-programs/{id}"
    )
    public RequestedProgram getById(
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
            path = "/api/requested-programs"
    )
    public RequestedProgram create(
            @RequestBody RequestedProgram request
    ) {
        LOGGER.debug("create called: request={}", request);
        return service.save(request);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/requested-programs/{id}"
    )
    public RequestedProgram update(
            @PathVariable long id,
            @RequestBody RequestedProgram request
    ) {
        LOGGER.debug("update called: id={}, request={}", id, request);
        return service.save(request);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/requested-programs/{id}/publish"
    )
    public void publish(
            @PathVariable long id,
            @RequestBody RequestedProgram request
    ) {
        LOGGER.debug("publish called: id={}, request={}", id, request);
        service.publish(request);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/requested-programs/{id}"
    )
    public RequestedProgram delete(
            @PathVariable long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        return service.delete(id);
    }

}
