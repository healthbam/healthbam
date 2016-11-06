package org.hmhb.user;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.user.UserIdMismatchException;
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
 * REST endpoint for {@link HmhbUser} objects.
 */
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link UserService} for saving, deleting, and
     *                retrieving {@link HmhbUser}s
     */
    @Autowired
    public UserController(
            @Nonnull UserService service
    ) {
        LOGGER.debug("constructed");
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Retrieves all {@link HmhbUser}s in the system.
     *
     * @return all {@link HmhbUser}s
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users"
    )
    public List<HmhbUser> getAll() {
        LOGGER.debug("getAll called");
        return service.getAll();
    }

    /**
     * Retrieves all {@link HmhbUser}s in the system and exports them to a
     * CSV file.
     *
     * @param jwtToken the JWT auth token (needed in a query param because this
     *                 method will be called via $window.open).
     * @param response the {@link HttpServletResponse} to send the results to
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/api/users/csv"
    )
    public void getAllAsCsv(
            @RequestParam("token") String jwtToken,
            HttpServletResponse response
    ) throws IOException {
        LOGGER.debug("getAllAsCsv called");

        String csvContent = service.getAllAsCsv(jwtToken);

        /* Must set the status and headers after the call succeeds or else confusing errors are passed back. */
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "text/csv");

        /* Have the browser prompt the user for download rather than try to open the csv. */
        response.setHeader("Content-Disposition", "attachment; filename=all-users.csv");

        response.getWriter()
                .append(csvContent);
    }

    /**
     * Retrieves a {@link HmhbUser} by its database ID.
     *
     * @param id the database ID
     * @return the {@link HmhbUser}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users/{id}"
    )
    public HmhbUser getById(
            @PathVariable long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        return service.getById(id);
    }

    /**
     * Creates a new {@link HmhbUser}.
     *
     * @param user the new {@link HmhbUser} to create
     * @return the newly created {@link HmhbUser}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users"
    )
    public HmhbUser create(
            @RequestBody HmhbUser user
    ) {
        LOGGER.debug("create called: user={}", user);
        return service.save(user);
    }

    /**
     * Updates an existing {@link HmhbUser}.
     *
     * @param id the database ID of the {@link HmhbUser} to update
     * @param user the {@link HmhbUser} to update to
     * @return the updated {@link HmhbUser}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users/{id}"
    )
    public HmhbUser update(
            @PathVariable long id,
            @RequestBody HmhbUser user
    ) {
        LOGGER.debug("update called: id={}, user={}", id, user);

        if (!Long.valueOf(id).equals(user.getId())) {
            throw new UserIdMismatchException(id, user.getId());
        }

        return service.save(user);
    }

    /**
     * Deletes a {@link HmhbUser}.
     *
     * @param id the database ID of the {@link HmhbUser} to delete
     * @return the deleted {@link HmhbUser}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users/{id}"
    )
    public HmhbUser delete(
            @PathVariable long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        return service.delete(id);
    }

}
