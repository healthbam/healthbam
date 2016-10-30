package org.hmhb.county;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access object for {@link County} objects.
 *
 * spring-data-jpa is providing the implementation of this dao:
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface CountyDao extends CrudRepository<County, Long> {

    /**
     * Returns all {@link County}s ordered by their name ascending.
     *
     * @return all {@link County}s
     */
    List<County> findAllByOrderByNameAsc();

    /**
     * Returns all {@link County}s that start with the passed in namePart
     * (ignoring case) ordered by their name ascending.
     *
     * @param namePart the first part of the name to match on
     * @return all {@link County}s matching the input
     */
    List<County> findByNameStartingWithIgnoreCaseOrderByName(String namePart);

}
