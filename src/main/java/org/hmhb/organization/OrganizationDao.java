package org.hmhb.organization;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access object for {@link Organization} objects.
 *
 * spring-data-jpa is providing the implementation of this dao:
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface OrganizationDao extends CrudRepository<Organization, Long> {

    /**
     * Returns all {@link Organization}s ordered by their name ascending.
     *
     * @return all {@link Organization}s
     */
    List<Organization> findAllByOrderByNameAsc();

}
