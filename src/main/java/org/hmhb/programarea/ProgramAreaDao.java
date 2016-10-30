package org.hmhb.programarea;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access object for {@link ProgramArea} objects.
 *
 * spring-data-jpa is providing the implementation of this dao:
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface ProgramAreaDao extends CrudRepository<ProgramArea, Long> {

    /**
     * Returns all {@link ProgramArea}s ordered by their name ascending.
     *
     * @return all {@link ProgramArea}s
     */
    List<ProgramArea> findAllByOrderByNameAsc();

}
