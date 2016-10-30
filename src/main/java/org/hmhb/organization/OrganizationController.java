package org.hmhb.organization;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.organization.OrganizationIdMismatchException;
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
 * REST endpoint for {@link Organization} objects.
 */
@RestController
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link OrganizationService} for saving, deleting, and
     *                retrieving {@link Organization}s
     */
    @Autowired
    public OrganizationController(
            @Nonnull OrganizationService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Retrieves all {@link Organization}s in the system.
     *
     * @return all {@link Organization}s
     */
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

    /**
     * Retrieves an {@link Organization} by its database ID.
     *
     * @param id the database ID
     * @return the {@link Organization}
     */
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

    /**
     * Creates a new {@link Organization}.
     *
     * @param organization the {@link Organization} to create
     * @return the newly created {@link Organization}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations"
    )
    public Organization create(
            @RequestBody Organization organization
    ) {
        LOGGER.debug("create called: organization={}", organization);
        return service.save(organization);
    }

    /**
     * Updates an existing {@link Organization}.
     *
     * @param id the database ID of the {@link Organization} to update
     * @param organization the {@link Organization} to update to
     * @return the updated {@link Organization}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/organizations/{id}"
    )
    public Organization update(
            @PathVariable long id,
            @RequestBody Organization organization
    ) {
        LOGGER.debug("update called: id={}, organization={}", id, organization);

        if (!Long.valueOf(id).equals(organization.getId())) {
            throw new OrganizationIdMismatchException(id, organization.getId());
        }

        return service.save(organization);
    }

    /**
     * Deletes an {@link Organization}.
     *
     * @param id the database ID of the {@link Organization} to delete
     * @return the deleted {@link Organization}
     */
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
