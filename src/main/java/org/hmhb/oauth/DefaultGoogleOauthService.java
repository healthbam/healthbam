package org.hmhb.oauth;

import javax.annotation.Nonnull;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import org.hmhb.exception.oauth.GoogleOauthException;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link GoogleOauthService}.
 */
@Service
public class DefaultGoogleOauthService implements GoogleOauthService {

    @Override
    public GoogleResponseData getUserDataFromGoogle(
            @Nonnull String clientId,
            @Nonnull String clientSecret,
            @Nonnull String code,
            @Nonnull String redirectUri
    ) {

        try {

            GoogleTokenResponse oauthResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    redirectUri
            ).execute();

            GoogleCredential credential = new GoogleCredential()
                    .setAccessToken(
                            oauthResponse.getAccessToken()
                    );

            Plus plus = new Plus.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    new HttpRequestInitializer() {
                        @Override
                        public void initialize(HttpRequest request) throws IOException {
                            // empty
                        }
                    }
            ).setHttpRequestInitializer(
                    credential
            ).build();

            Person gPlusResponse = plus.people()
                    .get("me")
                    .execute();

            return new GoogleResponseData(
                    oauthResponse,
                    gPlusResponse
            );

        } catch (IOException e) {
            throw new GoogleOauthException("Failed to call into google for token!", e);
        }

    }

}
