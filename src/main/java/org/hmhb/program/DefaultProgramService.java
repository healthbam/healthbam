package org.hmhb.program;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.authorization.AuthorizationService;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.hmhb.county.County;
import org.hmhb.csv.CsvService;
import org.hmhb.exception.program.OnlyAdminCanDeleteProgramException;
import org.hmhb.exception.program.OnlyAdminCanSaveProgramException;
import org.hmhb.exception.program.ProgramCityNameIsTooLongException;
import org.hmhb.exception.program.ProgramCityRequiredException;
import org.hmhb.exception.program.ProgramGoalIsTooLongException;
import org.hmhb.exception.program.ProgramMeasurableOutcome1RequiredException;
import org.hmhb.exception.program.ProgramNameIsTooLongException;
import org.hmhb.exception.program.ProgramNameRequiredException;
import org.hmhb.exception.program.ProgramNotFoundException;
import org.hmhb.exception.program.ProgramOrganizationRequiredException;
import org.hmhb.exception.program.ProgramOtherExplanationIsTooLongException;
import org.hmhb.exception.program.ProgramOutcomeIsTooLongException;
import org.hmhb.exception.program.ProgramPrimaryGoal1RequiredException;
import org.hmhb.exception.program.ProgramStartYearIsTooOldException;
import org.hmhb.exception.program.ProgramStateIsInvalidException;
import org.hmhb.exception.program.ProgramStateRequiredException;
import org.hmhb.exception.program.ProgramStreetAddressIsTooLongException;
import org.hmhb.exception.program.ProgramStreetAddressRequiredException;
import org.hmhb.exception.program.ProgramZipCodeIsInvalidException;
import org.hmhb.exception.program.ProgramZipCodeRequiredException;
import org.hmhb.geocode.GeocodeService;
import org.hmhb.geocode.LocationInfo;
import org.hmhb.mapquery.MapQuery;
import org.hmhb.mapquery.MapQuerySearch;
import org.hmhb.organization.Organization;
import org.hmhb.organization.OrganizationService;
import org.hmhb.programarea.ProgramArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ProgramService}.
 */
@Service
public class DefaultProgramService implements ProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProgramService.class);

    private final PublicConfig publicConfig;
    private final AuditHelper auditHelper;
    private final AuthorizationService authorizationService;
    private final CsvService csvService;
    private final GeocodeService geocodeService;
    private final OrganizationService organizationService;
    private final ProgramDao dao;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} to get config for
     *                      validation rules of programs
     * @param auditHelper the {@link AuditHelper} to get audit information
     * @param authorizationService the {@link AuthorizationService} to verify a
     *                             user is allowed to do certain operations
     * @param csvService the {@link CsvService} for exporting/importing to/from
     *                   CSV
     * @param geocodeService the {@link GeocodeService} to lookup location info
     * @param organizationService the {@link OrganizationService} to save
     *                            inline {@link Organization} info and lookup
     *                            {@link Organization}s for validation
     * @param dao the {@link ProgramDao} for saving, deleting, and
     *            retrieving {@link Organization}s
     */
    @Autowired
    public DefaultProgramService(
            @Nonnull ConfigService configService,
            @Nonnull AuditHelper auditHelper,
            @Nonnull AuthorizationService authorizationService,
            @Nonnull CsvService csvService,
            @Nonnull GeocodeService geocodeService,
            @Nonnull OrganizationService organizationService,
            @Nonnull ProgramDao dao
    ) {
        LOGGER.debug("constructed");
        requireNonNull(configService, "configService cannot be null");
        this.publicConfig = requireNonNull(configService.getPublicConfig(), "publicConfig cannot be null");
        this.authorizationService = requireNonNull(authorizationService, "authorizationService cannot be null");
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.csvService = requireNonNull(csvService, "csvService cannot be null");
        this.geocodeService = requireNonNull(geocodeService, "geocodeService cannot be null");
        this.organizationService = requireNonNull(organizationService, "organizationService cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public Program getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        Program result = dao.findOne(id);
        if (result == null) {
            throw new ProgramNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<Program> getByIds(
            @Nonnull List<Long> ids
    ) {
        LOGGER.debug("getByIds called: ids={}", ids);
        requireNonNull(ids, "ids cannot be null");
        return dao.findAll(ids);
    }

    @Timed
    @Override
    public List<Program> getAll() {
        LOGGER.debug("getAll called");
        return dao.findAllByOrderByNameAsc();
    }

    @Timed
    @Override
    public String getAllAsCsv(
            @Nullable Boolean expandCounties,
            @Nullable Boolean expandProgramAreas
    ) {
        LOGGER.debug(
                "getAllAsCsv called: expandCounties={}, expandProgramAreas={}",
                expandCounties,
                expandProgramAreas
        );

        return csvService.generateFromPrograms(
                getAll(),
                expandCounties,
                expandProgramAreas
        );
    }

    @Timed
    @Override
    public List<Program> search(
            @Nonnull MapQuery query
    ) {
        LOGGER.debug("search called: query={}", query);
        requireNonNull(query, "query cannot be null");

        MapQuerySearch search = query.getSearch();

        if (search == null) {
            return getAll();
        }

        if (search.getProgram() != null) {

            return Collections.singletonList(
                    getById(search.getProgram().getId())
            );

        }

        County county = search.getCounty();
        Organization organization = search.getOrganization();
        ProgramArea programArea = search.getProgramArea();

        Long countyId = null;

        if (county != null) {
            countyId = county.getId();
        }

        Long organizationId = null;

        if (organization != null) {
            organizationId = organization.getId();
        }

        Long programAreaId = null;

        if (programArea != null) {
            programAreaId = programArea.getId();
        }

        /*
         * This implementation is really ugly, but using JPA Specification
         * looked much more complicated.
         *
         * For now, with only 3 search params, I'm just going to have all
         * permutations of searches in the DAO.  If anymore params are added
         * and the permutations get worse, I'll have to use JPA Specification
         * or figure something else out.
         */
        return doSearch(organizationId, countyId, programAreaId);
    }

    private List<Program> doSearch(
            Long organizationId,
            Long countyId,
            Long programAreaId
    ) {
        if (organizationId != null) {

            if (countyId != null) {

                if (programAreaId != null) {
                    return dao.findByOrganizationIdAndCountiesServedIdAndProgramAreasId(
                            organizationId,
                            countyId,
                            programAreaId
                    );

                } else {
                    return dao.findByOrganizationIdAndCountiesServedId(organizationId, countyId);

                }

            } else {

                if (programAreaId != null) {
                    return dao.findByOrganizationIdAndProgramAreasId(organizationId, programAreaId);

                } else {
                    return dao.findByOrganizationId(organizationId);

                }

            }

        } else {

            if (countyId != null) {

                if (programAreaId != null) {
                    return dao.findByCountiesServedIdAndProgramAreasId(
                            countyId,
                            programAreaId
                    );

                } else {
                    return dao.findByCountiesServedId(countyId);

                }

            } else {

                if (programAreaId != null) {
                    return dao.findByProgramAreasId(programAreaId);

                } else {
                    return getAll();

                }

            }

        }
    }

    @Transactional
    @Timed
    @Override
    public Program delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");

        if (!authorizationService.isAdmin()) {
            throw new OnlyAdminCanDeleteProgramException();
        }

        /* Verify it exists. */
        Program program = getById(id);

        dao.delete(id);

        return program;
    }

    @Transactional
    @Timed
    @Override
    public Program save(
            @Nonnull Program program
    ) {
        LOGGER.debug("save called: program={}", program);
        requireNonNull(program, "program cannot be null");

        if (!authorizationService.isAdmin()) {
            throw new OnlyAdminCanSaveProgramException();
        }

        if (StringUtils.isBlank(program.getName())) {
            throw new ProgramNameRequiredException();
        }

        if (program.getName().length() > publicConfig.getProgramNameMaxLength()) {
            throw new ProgramNameIsTooLongException();
        }

        String other = program.getOtherProgramAreaExplanation();
        if (other != null && other.length() > publicConfig.getProgramAreaExplanationMaxLength()) {
            throw new ProgramOtherExplanationIsTooLongException();
        }

        if (StringUtils.isBlank(program.getState())) {
            throw new ProgramStateRequiredException();
        }

        if (!program.getState().matches("^[A-Z]{2}$")) {
            throw new ProgramStateIsInvalidException();
        }

        if (StringUtils.isBlank(program.getPrimaryGoal1())) {
            throw new ProgramPrimaryGoal1RequiredException();
        }

        if (program.getPrimaryGoal1().length() > publicConfig.getProgramGoalMaxLength()) {
            throw new ProgramGoalIsTooLongException();
        }

        if (program.getPrimaryGoal2() != null) {
            if (program.getPrimaryGoal2().length() > publicConfig.getProgramGoalMaxLength()) {
                throw new ProgramGoalIsTooLongException();
            }
        }

        if (program.getPrimaryGoal3() != null) {
            if (program.getPrimaryGoal3().length() > publicConfig.getProgramGoalMaxLength()) {
                throw new ProgramGoalIsTooLongException();
            }
        }

        if (StringUtils.isBlank(program.getMeasurableOutcome1())) {
            throw new ProgramMeasurableOutcome1RequiredException();
        }

        if (program.getMeasurableOutcome1().length() > publicConfig.getProgramOutcomeMaxLength()) {
            throw new ProgramOutcomeIsTooLongException();
        }

        if (program.getMeasurableOutcome2() != null) {
            if (program.getMeasurableOutcome2().length() > publicConfig.getProgramOutcomeMaxLength()) {
                throw new ProgramOutcomeIsTooLongException();
            }
        }

        if (program.getMeasurableOutcome3() != null) {
            if (program.getMeasurableOutcome3().length() > publicConfig.getProgramOutcomeMaxLength()) {
                throw new ProgramOutcomeIsTooLongException();
            }
        }

        if (program.getStartYear() != null) {
            if (program.getStartYear() < publicConfig.getProgramStartYearMin()) {
                throw new ProgramStartYearIsTooOldException();
            }
        }

        if (StringUtils.isBlank(program.getStreetAddress())) {
            throw new ProgramStreetAddressRequiredException();
        }

        if (program.getStreetAddress().length() > publicConfig.getProgramStreetAddressMaxLength()) {
            throw new ProgramStreetAddressIsTooLongException();
        }

        if (StringUtils.isBlank(program.getCity())) {
            throw new ProgramCityRequiredException();
        }

        if (program.getCity().length() > publicConfig.getProgramCityMaxLength()) {
            throw new ProgramCityNameIsTooLongException();
        }

        if (program.getOrganization() == null) {
            throw new ProgramOrganizationRequiredException();
        } else if (program.getOrganization().getId() == null) {
            /* The user is submitting a new organization while submitting a program. */
            program.setOrganization(organizationService.save(program.getOrganization()));
        } else {
            /* Verify the organization exists and get the updated organization info. */
            program.setOrganization(organizationService.getById(program.getOrganization().getId()));
        }

        fillCoordinatesAndZip(program);

        if (StringUtils.isBlank(program.getZipCode())) {
            throw new ProgramZipCodeRequiredException();
        }

        if (!program.getZipCode().matches("^[0-9]{5}([ -][0-9]{4})?$")) {
            throw new ProgramZipCodeIsInvalidException();
        }

        if (program.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            program.setCreatedBy(auditHelper.getCurrentUserEmail());
            program.setCreatedOn(auditHelper.getCurrentTime());
            program.setUpdatedBy(null);
            program.setUpdatedOn(null);
        } else {
            /* Verify it exists and get the original createdBy and createdOn. */
            Program programInDb = getById(program.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            program.setCreatedBy(programInDb.getCreatedBy());
            program.setCreatedOn(programInDb.getCreatedOn());
            program.setUpdatedBy(auditHelper.getCurrentUserEmail());
            program.setUpdatedOn(auditHelper.getCurrentTime());
        }

        return dao.save(program);

    }

    private void fillCoordinatesAndZip(Program program) {
        LocationInfo locationInfo = geocodeService.getLocationInfo(program);
        if (locationInfo != null) {
            program.setZipCode(locationInfo.getZipCode());
            program.setCoordinates(locationInfo.getLngLat());
        }
    }

}
