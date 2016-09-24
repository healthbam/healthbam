package org.hmhb.organization;

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
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService service;

    @Autowired
    public OrganizationController(
            @Nonnull OrganizationService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations"
    )
    public List<Organization> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations/{id}"
    )
    public Organization getById(
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
            path = "/api/organizations"
    )
    public Organization create(
            @RequestBody Organization request
    ) {
        LOGGER.debug("create called: request={}", request);
        return service.save(request);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations/{id}"
    )
    public Organization update(
            @PathVariable long id,
            @RequestBody Organization request
    ) {
        LOGGER.debug("update called: id={}, request={}", id, request);
        return service.save(request);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations/{id}"
    )
    public Organization delete(
            @PathVariable long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        return service.delete(id);
    }

}
