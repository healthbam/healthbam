package org.hmhb.user;

import javax.annotation.Nonnull;

/**
 * Service to create and retrieve {@link HmhbUser}s.
 */
public interface UserService {

    /**
     * Retrieves a {@link HmhbUser} by its email.
     *
     * @param email the email to look up a {@link HmhbUser} for
     * @return the {@link HmhbUser}
     */
    HmhbUser getUserByEmail(
            @Nonnull String email
    );

    /**
     * Creates a new non-admin {@link HmhbUser} in the system.
     *
     * @param email the email to save into the {@link HmhbUser}
     * @return the newly created {@link HmhbUser}
     */
    HmhbUser provisionNewUser(
            @Nonnull String email
    );

}
