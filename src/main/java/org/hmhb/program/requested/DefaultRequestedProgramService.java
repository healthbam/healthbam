package org.hmhb.program.requested;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.program.requested.RequestedProgramGeoRequiredException;
import org.hmhb.exception.program.requested.RequestedProgramNotFoundException;
import org.hmhb.exception.program.requested.RequestedProgramOrgNameRequiredException;
import org.hmhb.program.published.PublishedProgram;
import org.hmhb.program.published.PublishedProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultRequestedProgramService implements RequestedProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRequestedProgramService.class);

    private final AuditHelper auditHelper;
    private final PublishedProgramService programService;
    private final RequestedProgramDao dao;

    @Autowired
    public DefaultRequestedProgramService(
            @Nonnull AuditHelper auditHelper,
            @Nonnull PublishedProgramService programService,
            @Nonnull RequestedProgramDao dao
    ) {
        LOGGER.debug("constructed");
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.programService = requireNonNull(programService, "programService cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public RequestedProgram getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        RequestedProgram result = dao.findOne(id);
        if (result == null) {
            throw new RequestedProgramNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<RequestedProgram> getAll() {
        LOGGER.debug("getAll called");
        List<RequestedProgram> results = new ArrayList<>();
        dao.findAll().forEach(results::add);
        return results;
    }

    @Transactional
    @Timed
    @Override
    public RequestedProgram delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");
        /* Verify it exists. */
        RequestedProgram request = getById(id);
        dao.delete(id);
        return request;
    }

    private void validateRequestedProgram(RequestedProgram request) {
        if (StringUtils.isBlank(request.getOrganizationName())) {
            throw new RequestedProgramOrgNameRequiredException();
        }

        if (StringUtils.isBlank(request.getGeo())) {
            throw new RequestedProgramGeoRequiredException();
        }
    }

    @Transactional
    @Timed
    @Override
    public RequestedProgram save(
            @Nonnull RequestedProgram request
    ) {
        LOGGER.debug("save called: request={}", request);
        requireNonNull(request, "request cannot be null");

        validateRequestedProgram(request);

        if (request.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            request.setCreatedBy(auditHelper.getCallerIp());
            request.setCreatedOn(auditHelper.getCurrentTime());
            request.setUpdatedBy(null);
            request.setUpdatedOn(null);
        } else {
            /* Verify request exists and get the original createdBy and createdOn. */
            RequestedProgram requestInDb = getById(request.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            request.setCreatedBy(requestInDb.getCreatedBy());
            request.setCreatedOn(requestInDb.getCreatedOn());
            request.setUpdatedBy(auditHelper.getCurrentUser());
            request.setUpdatedOn(auditHelper.getCurrentTime());
        }

        if (request.getProgramId() != null) {
            /* Verify program exists. */
            programService.getById(request.getProgramId());
        }

        return dao.save(request);
    }

    @Transactional
    @Timed
    @Override
    public void publish(
            @Nonnull RequestedProgram request
    ) {
        LOGGER.debug("publish called: request={}", request);
        requireNonNull(request, "request cannot be null");

        validateRequestedProgram(request);

        if (request.getId() != null) {
            /* Delete will verify that the request exists. */
            delete(request.getId());
        }

        PublishedProgram program;

        if (request.getProgramId() == null) {
            program = new PublishedProgram();
            program.setCreatedBy(auditHelper.getCurrentUser());
            program.setCreatedOn(auditHelper.getCurrentTime());
        } else {
            /* Verify the program exists. */
            program = programService.getById(request.getProgramId());
            program.setUpdatedBy(auditHelper.getCurrentUser());
            program.setUpdatedOn(auditHelper.getCurrentTime());
        }

        program.setGeo(request.getGeo());
        program.setMission(request.getMission());
        program.setOrganizationName(request.getOrganizationName());
        program.setOutcomes(request.getOutcomes());
        program.setPrivateEmail(request.getPrivateEmail());
        program.setPrivatePhone(request.getPrivatePhone());
        program.setPublicEmail(request.getPublicEmail());
        program.setPublicPhone(request.getPublicPhone());
        program.setSummary(request.getSummary());

        programService.save(program);
    }

}
