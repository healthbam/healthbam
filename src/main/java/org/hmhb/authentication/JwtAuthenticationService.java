package org.hmhb.authentication;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import org.hmhb.user.HmhbUser;

public interface JwtAuthenticationService {

    String generateJwtToken(
            @Nonnull HmhbUser user
    );

    void validateAuthentication(
            @Nonnull HttpServletRequest request
    );

}
