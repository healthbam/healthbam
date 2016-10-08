package org.hmhb.program;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.program.ProgramNameRequiredException;
import org.hmhb.exception.program.ProgramNotFoundException;
import org.hmhb.exception.program.ProgramOrganizationIdRequiredException;
import org.hmhb.exception.program.ProgramStartYearRequiredException;
import org.hmhb.exception.program.ProgramStateRequiredException;
import org.hmhb.exception.program.ProgramZipCodeRequiredException;
import org.hmhb.geocode.GeocodeService;
import org.hmhb.organization.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultProgramService implements ProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProgramService.class);

    private final AuditHelper auditHelper;
    private final GeocodeService geocodeService;
    private final OrganizationService organizationService;
    private final ProgramDao dao;

    @Autowired
    public DefaultProgramService(
            @Nonnull AuditHelper auditHelper,
            @Nonnull GeocodeService geocodeService,
            @Nonnull OrganizationService organizationService,
            @Nonnull ProgramDao dao
    ) {
        LOGGER.debug("constructed");
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
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
        return dao.findByIdIn(ids);
    }

    @Timed
    @Override
    public List<Program> getAll() {
        LOGGER.debug("getAll called");
        List<Program> target = new ArrayList<>();
        dao.findAll().forEach(target::add);
        return target;
    }

    @Transactional
    @Timed
    @Override
    public Program delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");
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

        if (StringUtils.isBlank(program.getName())) {
            throw new ProgramNameRequiredException();
        }

        if (program.getOrganization() == null || program.getOrganization().getId() == null) {
            throw new ProgramOrganizationIdRequiredException();
        } else {
            /* Verify the organization exists and get the updated organization info. */
            program.setOrganization(organizationService.getById(program.getOrganization().getId()));
        }

        if (program.getStartYear() == null || program.getStartYear().equals(0)) {
            throw new ProgramStartYearRequiredException();
        }

        if (StringUtils.isBlank(program.getState())) {
            throw new ProgramStateRequiredException();
        }

        if (StringUtils.isBlank(program.getZipCode())) {
            throw new ProgramZipCodeRequiredException();
        }

        if (program.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            program.setCreatedBy(auditHelper.getCurrentUser());
            program.setCreatedOn(auditHelper.getCurrentTime());
            program.setUpdatedBy(null);
            program.setUpdatedOn(null);
        } else {
            /* Verify it exists and get the original createdBy and createdOn. */
            Program programInDb = getById(program.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            program.setCreatedBy(programInDb.getCreatedBy());
            program.setCreatedOn(programInDb.getCreatedOn());
            program.setUpdatedBy(auditHelper.getCurrentUser());
            program.setUpdatedOn(auditHelper.getCurrentTime());
        }

        fillCoordinates(program);

        return dao.save(program);

    }

    private void fillCoordinates(Program program) {
        program.setCoordinates(geocodeService.getLngLat(program));
    }

}
