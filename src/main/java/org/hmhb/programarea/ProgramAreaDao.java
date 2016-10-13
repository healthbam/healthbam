package org.hmhb.programarea;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access layer for {@link ProgramArea} objects.
 */
public interface ProgramAreaDao extends CrudRepository<ProgramArea, Long> {

    List<ProgramArea> findAllByOrderByNameAsc();

}
