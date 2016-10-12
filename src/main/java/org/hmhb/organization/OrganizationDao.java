package org.hmhb.organization;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrganizationDao extends CrudRepository<Organization, Long> {

    List<Organization> findAllByOrderByNameAsc();

}
