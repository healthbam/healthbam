package org.hmhb.authentication;

import javax.annotation.Nonnull;

public interface AuthenticationService {

    TokenResponse authenticate(
            @Nonnull GoogleOauthAccessRequestInfo request
    );

}
