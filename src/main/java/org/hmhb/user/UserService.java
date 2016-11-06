package org.hmhb.user;

import javax.annotation.Nonnull;

import java.util.List;

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

    /**
     * Retrieves a {@link HmhbUser} by its database ID.
     *
     * @param id the database ID
     * @return the {@link HmhbUser}
     */
    HmhbUser getById(
            @Nonnull Long id
    );

    /**
     * Retrieves all {@link HmhbUser}s in the system.
     *
     * @return all {@link HmhbUser}s
     */
    List<HmhbUser> getAll();

    /**
     * Retrieves all {@link HmhbUser}s in the system as a CSV.
     *
     * @param jwtToken the JWT auth token
     * @return all {@link HmhbUser}s as CSV
     */
    String getAllAsCsv(
            @Nonnull String jwtToken
    );

    /**
     * Deletes a {@link HmhbUser} by its database ID.
     *
     * @param id the database ID
     * @return the deleted {@link HmhbUser}
     */
    HmhbUser delete(
            @Nonnull Long id
    );

    /**
     * Saves a {@link HmhbUser} (create or update).
     *
     * @param user the {@link HmhbUser} to create or update
     * @return the saved {@link HmhbUser}
     */
    HmhbUser save(
            @Nonnull HmhbUser user
    );

}
