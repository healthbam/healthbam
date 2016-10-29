package org.hmhb.oauth;

import javax.annotation.Nonnull;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

public interface GoogleOauthService {

    GoogleTokenResponse getTokenResponse(
            @Nonnull String clientId,
            @Nonnull String clientSecret,
            @Nonnull String code,
            @Nonnull String redirectUri
    );

}
