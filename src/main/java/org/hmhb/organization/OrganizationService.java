package org.hmhb.organization;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * Service to save, delete, and retrieve {@link Organization}s.
 */
public interface OrganizationService {

    /**
     * Retrieve an {@link Organization} by its database ID.
     *
     * @param id the database ID
     * @return the {@link Organization}
     */
    Organization getById(@Nonnull Long id);

    /**
     * Retrieve all {@link Organization}s in the system.
     *
     * @return all {@link Organization}s
     */
    List<Organization> getAll();

    /**
     * Delete an {@link Organization}.
     *
     * @param id the database ID of the {@link Organization} to delete
     * @return the {@link Organization} that was deleted
     */
    Organization delete(@Nonnull Long id);

    /**
     * Saves an {@link Organization}.
     *
     * @param organization the {@link Organization} to create or update
     * @return the saved {@link Organization}
     */
    Organization save(@Nonnull Organization organization);

}
