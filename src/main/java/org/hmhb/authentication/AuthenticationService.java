package org.hmhb.authentication;

import javax.annotation.Nonnull;

/**
 * Service to confirm a user's email with google and generate a JWT token for
 * the client to pass in on future requests.
 */
public interface AuthenticationService {

    /**
     * Checks with google and generates a JWT token and puts it into a
     * {@link TokenResponse} so the client can pass that token in on future
     * requests.
     *
     * @param request the {@link GoogleOauthAccessRequestInfo} to use to call
     *                into google with
     * @return the {@link TokenResponse}
     */
    TokenResponse authenticate(
            @Nonnull GoogleOauthAccessRequestInfo request
    );

}
