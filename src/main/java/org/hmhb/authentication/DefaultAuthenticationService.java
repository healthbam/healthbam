package org.hmhb.authentication;

import javax.annotation.Nonnull;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.hmhb.exception.authentication.ClientIdMismatchException;
import org.hmhb.exception.oauth.GoogleOauthException;
import org.hmhb.exception.user.UserNotFoundException;
import org.hmhb.oauth.GoogleOauthService;
import org.hmhb.user.HmhbUser;
import org.hmhb.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link AuthenticationService}.
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationService.class);

    private final Environment environment;
    private final GoogleOauthService googleOauthService;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserService userService;

    /**
     * An injectable constructor.
     *
     * @param environment the {@link Environment} for config
     * @param googleOauthService the {@link GoogleOauthService} to confirm a
     *                           user's email with google
     * @param jwtAuthenticationService the {@link JwtAuthenticationService} to
     *                                 generate a JWT with
     * @param userService the {@link UserService} to lookup or provision a new
     *                    {@link HmhbUser} in the system
     */
    @Autowired
    public DefaultAuthenticationService(
            @Nonnull Environment environment,
            @Nonnull GoogleOauthService googleOauthService,
            @Nonnull JwtAuthenticationService jwtAuthenticationService,
            @Nonnull UserService userService
    ) {
        this.environment = requireNonNull(environment, "environment cannot be null");
        this.googleOauthService = requireNonNull(googleOauthService, "googleOauthService cannot be null");
        this.jwtAuthenticationService = requireNonNull(jwtAuthenticationService, "jwtAuthenticationService cannot be null");
        this.userService = requireNonNull(userService, "userService cannot be null");
    }

    @Override
    public TokenResponse authenticate(
            @Nonnull GoogleOauthAccessRequestInfo request
    ) {
        LOGGER.debug("authenticate called: request={}", request);
        requireNonNull(request, "request cannot be null");

        String email;

        String clientId = environment.getProperty("google.oauth.client.id");
        String clientSecret = environment.getProperty("google.oauth.client.secret");

        if (!clientId.equals(request.getClientId())) {
            throw new ClientIdMismatchException(
                    request.getClientId(),
                    clientId
            );
        }

        LOGGER.debug(
                "Calling into google: clientId={}, code={}, redirectUrl={}",
                request.getClientId(),
                request.getCode(),
                request.getRedirectUri()
        );

        GoogleTokenResponse googleTokenResponse = googleOauthService.getTokenResponse(
                request.getClientId(),
                clientSecret,
                request.getCode(),
                request.getRedirectUri()
        );

        try {
            GoogleIdToken idToken = googleTokenResponse.parseIdToken();
            email = idToken.getPayload().getEmail();
            LOGGER.debug("successfully called into google: email={}", email);
        } catch (IOException e) {
            throw new GoogleOauthException("Failed to parse google id token!", e);
        }

        HmhbUser user;

        try {
            user = userService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            user = userService.provisionNewUser(email);
        }

        TokenResponse tokenResponse = new TokenResponse(
                jwtAuthenticationService.generateJwtToken(user)
        );

        LOGGER.info("tokenResponse: {}", tokenResponse);
        return tokenResponse;
    }

}
