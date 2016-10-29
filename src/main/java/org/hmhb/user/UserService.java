package org.hmhb.user;

import javax.annotation.Nonnull;

public interface UserService {

    HmhbUser getUserByEmail(
            @Nonnull String email
    );

    HmhbUser provisionNewUser(
            @Nonnull String email
    );

}
