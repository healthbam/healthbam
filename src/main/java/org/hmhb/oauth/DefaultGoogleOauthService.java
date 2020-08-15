package org.hmhb.oauth;

import javax.annotation.Nonnull;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.hmhb.exception.oauth.GoogleOauthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

/**
 * Default implementation of {@link GoogleOauthService}.
 */
@Service
public class DefaultGoogleOauthService implements GoogleOauthService {

    private final RestOperations restOperations;

    @Autowired
    public DefaultGoogleOauthService(@Nonnull RestOperations restOperations) {
        this.restOperations = restOperations;
    }

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

            GoogleWellKnownResponse wellKnown = restOperations.getForObject(
                    "https://accounts.google.com/.well-known/openid-configuration",
                    GoogleWellKnownResponse.class
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + oauthResponse.getAccessToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            // getUserinfoEndpoint is currently: https://openidconnect.googleapis.com/v1/userinfo
            ResponseEntity<GoogleUserInfo> result = restOperations.exchange(wellKnown.getUserinfoEndpoint(), HttpMethod.GET, entity, GoogleUserInfo.class);

            return new GoogleResponseData(
                    oauthResponse,
                    result.getBody()
            );

        } catch (IOException e) {
            throw new GoogleOauthException("Failed to call into google for token!", e);
        }

    }

}
