package org.hmhb.program;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramDao extends JpaRepository<Program, Long> {

    List<Program> findAllByOrderByNameAsc();

    List<Program> findByOrganizationId(
            Long organizationId
    );
    List<Program> findByCountiesServedId(
            Long countyId
    );
    List<Program> findByProgramAreasId(
            Long programAreaId
    );

    List<Program> findByOrganizationIdAndCountiesServedId(
            Long organizationId,
            Long countyId
    );
    List<Program> findByOrganizationIdAndProgramAreasId(
            Long organizationId,
            Long programAreaId
    );
    List<Program> findByCountiesServedIdAndProgramAreasId(
            Long countyId,
            Long programAreaId
    );

    List<Program> findByOrganizationIdAndCountiesServedIdAndProgramAreasId(
            Long organizationId,
            Long countyId,
            Long programAreaId
    );

}
