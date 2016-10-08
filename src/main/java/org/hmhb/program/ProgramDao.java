package org.hmhb.program;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProgramDao extends CrudRepository<Program, Long> {

    List<Program> findByOrganizationId(Long organizationId);

    List<Program> findByIdIn(Collection<Long> ids);

}
