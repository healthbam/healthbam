package org.hmhb.program.published;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.program.published.PublishedProgramGeoRequiredException;
import org.hmhb.exception.program.published.PublishedProgramNotFoundException;
import org.hmhb.exception.program.published.PublishedProgramOrgNameRequiredException;
import org.hmhb.exception.program.published.UpdateRequestsExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultPublishedProgramService implements PublishedProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPublishedProgramService.class);

    private final AuditHelper auditHelper;
    private final PublishedProgramDao dao;

    @Autowired
    public DefaultPublishedProgramService(
            @Nonnull AuditHelper auditHelper,
            @Nonnull PublishedProgramDao dao
    ) {
        LOGGER.debug("constructed");
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public PublishedProgram getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        PublishedProgram result = dao.findOne(id);
        if (result == null) {
            throw new PublishedProgramNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<PublishedProgram> getAll() {
        LOGGER.debug("getAll called");
        List<PublishedProgram> target = new ArrayList<>();
        dao.findAll().forEach(target::add);
        return target;
    }

    @Transactional
    @Timed
    @Override
    public PublishedProgram delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");
        /* Verify it exists. */
        PublishedProgram program = getById(id);
        if (program.getRequestedPrograms().size() > 0) {
            throw new UpdateRequestsExistException(id);
        }
        dao.delete(id);
        return program;
    }

    @Transactional
    @Timed
    @Override
    public PublishedProgram save(
            @Nonnull PublishedProgram program
    ) {
        LOGGER.debug("save called: program={}", program);
        requireNonNull(program, "program cannot be null");

        if (StringUtils.isBlank(program.getOrganizationName())) {
            throw new PublishedProgramOrgNameRequiredException();
        }

        if (StringUtils.isBlank(program.getGeo())) {
            throw new PublishedProgramGeoRequiredException();
        }

        if (program.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            program.setCreatedBy(auditHelper.getCurrentUser());
            program.setCreatedOn(auditHelper.getCurrentTime());
            program.setUpdatedBy(null);
            program.setUpdatedOn(null);
        } else {
            /* Verify it exists and get the original createdBy and createdOn. */
            PublishedProgram programInDb = getById(program.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            program.setCreatedBy(programInDb.getCreatedBy());
            program.setCreatedOn(programInDb.getCreatedOn());
            program.setUpdatedBy(auditHelper.getCurrentUser());
            program.setUpdatedOn(auditHelper.getCurrentTime());
        }

        return dao.save(program);

    }

}
