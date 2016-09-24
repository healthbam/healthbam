package org.hmhb.organization;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.audit.AuditHelper;
import org.hmhb.exception.organization.CannotDeleteOrganizationWithProgramsException;
import org.hmhb.exception.organization.OrganizationNameRequiredException;
import org.hmhb.exception.organization.OrganizationNotFoundException;
import org.hmhb.program.ProgramDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultOrganizationService implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrganizationService.class);

    private final AuditHelper auditHelper;
    private final OrganizationDao dao;
    private final ProgramDao programDao;

    @Autowired
    public DefaultOrganizationService(
            @Nonnull AuditHelper auditHelper,
            @Nonnull ProgramDao programDao,
            @Nonnull OrganizationDao dao
    ) {
        LOGGER.debug("constructed");
        this.auditHelper = requireNonNull(auditHelper, "auditHelper cannot be null");
        this.programDao = requireNonNull(programDao, "programDao cannot be null");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public Organization getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        Organization result = dao.findOne(id);
        if (result == null) {
            throw new OrganizationNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<Organization> getAll() {
        LOGGER.debug("getAll called");
        List<Organization> target = new ArrayList<>();
        dao.findAll().forEach(target::add);
        return target;
    }

    @Transactional
    @Timed
    @Override
    public Organization delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("delete called: id={}", id);
        requireNonNull(id, "id cannot be null");
        /* Verify it exists. */
        Organization organization = getById(id);
        if (programDao.findByOrganizationId(id).size() > 0) {
            throw new CannotDeleteOrganizationWithProgramsException(id);
        }
        dao.delete(id);
        return organization;
    }

    @Transactional
    @Timed
    @Override
    public Organization save(
            @Nonnull Organization organization
    ) {
        LOGGER.debug("save called: organization={}", organization);
        requireNonNull(organization, "organization cannot be null");

        if (StringUtils.isBlank(organization.getName())) {
            throw new OrganizationNameRequiredException();
        }

        if (organization.getId() == null) {
            /* They aren't allowed to set created* or updated* in a create. */
            organization.setCreatedBy(auditHelper.getCurrentUser());
            organization.setCreatedOn(auditHelper.getCurrentTime());
            organization.setUpdatedBy(null);
            organization.setUpdatedOn(null);
        } else {
            /* Verify it exists and get the original createdBy and createdOn. */
            Organization organizationInDb = getById(organization.getId());

            /* They aren't allowed to change created* or updated* in an update. */
            organization.setCreatedBy(organizationInDb.getCreatedBy());
            organization.setCreatedOn(organizationInDb.getCreatedOn());
            organization.setUpdatedBy(auditHelper.getCurrentUser());
            organization.setUpdatedOn(auditHelper.getCurrentTime());
        }

        return dao.save(organization);

    }

}
