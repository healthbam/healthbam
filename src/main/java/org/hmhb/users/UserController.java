package org.hmhb.users;

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
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public void setService(
            @Nonnull UserService service
    ) {
        LOGGER.debug("{} constructed", UserController.class.getSimpleName());
        this.service = requireNonNull(service, "service cannot be null");
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users"
    )
    public List<HmhbUser> getAll() {
        LOGGER.debug("{}.getAll called", UserController.class.getSimpleName());
        return service.getAll();
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users/{id}"
    )
    public HmhbUser getById(
            @PathVariable long id
    ) {
        LOGGER.debug("{}.getById called: id={}", UserController.class.getSimpleName(), id);
        return service.getById(id);
    }

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
        LOGGER.debug("{}.create called: user={}", UserController.class.getSimpleName(), user);
        return service.save(user);
    }

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
        LOGGER.debug("{}.update called: id={}, user={}", UserController.class.getSimpleName(), id, user);
        return service.save(user);
    }

    @Timed
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/api/users/{id}"
    )
    public HmhbUser delete(
            @PathVariable long id
    ) {
        LOGGER.debug("{}.delete called: id={}", UserController.class.getSimpleName(), id);
        return service.delete(id);
    }

}
