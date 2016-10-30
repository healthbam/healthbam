package org.hmhb.oauth;

import javax.annotation.Nonnull;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

/**
 * Service to call into google to retrieve a user's information.
 */
public interface GoogleOauthService {

    /**
     * Retrieves user's information from google using oauth2.
     *
     * @param clientId the oauth client ID
     * @param clientSecret the oauth client secret
     * @param code the code from the user's request token
     * @param redirectUri the redirect uri for the request
     * @return google's token response, which includes an email
     */
    GoogleTokenResponse getTokenResponse(
            @Nonnull String clientId,
            @Nonnull String clientSecret,
            @Nonnull String code,
            @Nonnull String redirectUri
    );

}
