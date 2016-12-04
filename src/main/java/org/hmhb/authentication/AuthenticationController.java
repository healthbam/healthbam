package org.hmhb.authentication;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * REST endpoint for our client to call into to pass google oauth information
 * so we can lookup and confirm a user's email from google.
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService service;

    /**
     * An injectable constructor.
     *
     * @param service the {@link AuthenticationService} to generate a JWT token
     */
    @Autowired
    public AuthenticationController(
            @Nonnull AuthenticationService service
    ) {
        this.service = requireNonNull(service, "service cannot be null");
    }

    /**
     * Checks with google and generates a JWT token and puts it into a
     * {@link TokenResponse} so the client can pass that token in on future
     * requests.
     *
     * @param request the {@link GoogleOauthAccessRequestInfo} to use to call
     *                into google with
     * @return the {@link TokenResponse}
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/auth/google"
    )
    public TokenResponse authenticate(
            @RequestBody GoogleOauthAccessRequestInfo request
    ) {
        LOGGER.debug("authenticate called: request={}", request);
        return service.authenticate(request);
    }

}
