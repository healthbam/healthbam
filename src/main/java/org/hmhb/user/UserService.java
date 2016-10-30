package org.hmhb.user;

import javax.annotation.Nonnull;

import com.google.api.services.plus.model.Person;

/**
 * Service to create and retrieve {@link HmhbUser}s.
 */
public interface UserService {

    /**
     * Updates an existing user, or creates a new non-admin {@link HmhbUser} in
     * the system based on the data passed in from google.
     *
     * @param email the email to lookup or save into the {@link HmhbUser}
     * @param profile the user's google+ profile
     * @return the {@link HmhbUser}
     */
    HmhbUser saveWithGoogleData(
            @Nonnull String email,
            @Nonnull Person profile
    );

}
