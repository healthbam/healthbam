package org.hmhb.oauth;

import javax.annotation.Nonnull;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.hmhb.exception.oauth.GoogleOauthException;
import org.springframework.stereotype.Service;

@Service
public class DefaultGoogleOauthService implements GoogleOauthService {

    @Override
    public GoogleTokenResponse getTokenResponse(
            @Nonnull String clientId,
            @Nonnull String clientSecret,
            @Nonnull String code,
            @Nonnull String redirectUri
    ) {

        try {

            return new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    redirectUri
            ).execute();

        } catch (IOException e) {
            throw new GoogleOauthException("Failed to call into google for token!", e);
        }

    }

}
