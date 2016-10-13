package org.hmhb.county;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access layer for {@link County} objects.
 */
public interface CountyDao extends CrudRepository<County, Long> {

    List<County> findAllByOrderByNameAsc();

    List<County> findByNameStartingWithIgnoreCase(String namePart);

}
