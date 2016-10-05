package org.hmhb.county;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Logic layer for {@link County} objects.
 */
public interface CountyService {

    County getById(@Nonnull Long id);

    List<County> getAll();

    List<County> findByNameStartingWithIgnoreCase(String namePart);

}
