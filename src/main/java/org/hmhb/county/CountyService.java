package org.hmhb.county;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Logic layer for {@link County} objects.
 */
public interface CountyService {

    /**
     * Retrieves a {@link County} by its ID.
     *
     * @param id the database id
     * @return the {@link County}
     */
    County getById(@Nonnull Long id);

    /**
     * Retrieves all {@link County}s in the system (Georgia).
     *
     * @return all {@link County}s
     */
    List<County> getAll();

    /**
     * Searches for {@link County}s by the passed in name.
     *
     * @param namePart the search string to find {@link County}s with
     * @return the matching {@link County}s
     */
    List<County> searchByName(String namePart);

}
