package org.hmhb.program;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database access object for {@link Program} objects.
 *
 * spring-data-jpa is providing the implementation of this dao:
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface ProgramDao extends JpaRepository<Program, Long> {

    /**
     * Returns all {@link Program}s ordered by their name ascending.
     *
     * @return all {@link Program}s
     */
    List<Program> findAllByOrderByNameAsc();

    /**
     * Returns all {@link Program}s associated with the passed in org ID.
     *
     * @param organizationId the database ID of the org to find
     *                       {@link Program}s for
     * @return all {@link Program}s associated with the passed in org
     */
    List<Program> findByOrganizationId(
            Long organizationId
    );

    /**
     * Returns all {@link Program}s associated with the passed in county ID.
     *
     * @param countyId the database ID of the county to find {@link Program}s
     *                 for
     * @return all {@link Program}s associated with the passed in county
     */
    List<Program> findByCountiesServedId(
            Long countyId
    );

    /**
     * Returns all {@link Program}s associated with the passed in program area
     * ID.
     *
     * @param programAreaId the database ID of the program area to find
     *                      {@link Program}s for
     * @return all {@link Program}s associated with the passed in program area
     */
    List<Program> findByProgramAreasId(
            Long programAreaId
    );

    /**
     * Returns all {@link Program}s associated with both the passed in org ID
     * and the passed in county ID.
     *
     * @param organizationId the database ID of the org to find
     *                       {@link Program}s for
     * @param countyId the database ID of the county to find {@link Program}s
     *                 for
     * @return all {@link Program}s associated with the passed in org and
     * county IDs
     */
    List<Program> findByOrganizationIdAndCountiesServedId(
            Long organizationId,
            Long countyId
    );

    /**
     * Returns all {@link Program}s associated with both the passed in org ID
     * and the passed in program area ID.
     *
     * @param organizationId the database ID of the org to find
     *                       {@link Program}s for
     * @param programAreaId the database ID of the program area to find
     *                      {@link Program}s for
     * @return all {@link Program}s associated with the passed in org and
     * program area IDs
     */
    List<Program> findByOrganizationIdAndProgramAreasId(
            Long organizationId,
            Long programAreaId
    );

    /**
     * Returns all {@link Program}s associated with both the passed in county
     * ID and the passed in program area ID.
     *
     * @param countyId the database ID of the county to find {@link Program}s
     *                 for
     * @param programAreaId the database ID of the program area to find
     *                      {@link Program}s for
     * @return all {@link Program}s associated with the passed in county and
     * program area IDs
     */
    List<Program> findByCountiesServedIdAndProgramAreasId(
            Long countyId,
            Long programAreaId
    );

    /**
     * Returns all {@link Program}s associated with all of the passed org,
     * county, and program area IDs.
     *
     * @param organizationId the database ID of the org to find
     *                       {@link Program}s for
     * @param countyId the database ID of the county to find {@link Program}s
     *                 for
     * @param programAreaId the database ID of the program area to find
     *                      {@link Program}s for
     * @return all {@link Program}s associated with the passed in org, county,
     * and program area IDs
     */
    List<Program> findByOrganizationIdAndCountiesServedIdAndProgramAreasId(
            Long organizationId,
            Long countyId,
            Long programAreaId
    );

}
