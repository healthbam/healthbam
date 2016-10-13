package org.hmhb.programarea;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.programarea.ProgramAreaNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ProgramAreaService}.
 */
@Service
public class DefaultProgramAreaService implements ProgramAreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProgramAreaService.class);

    private final ProgramAreaDao dao;

    @Autowired
    public DefaultProgramAreaService(
            @Nonnull ProgramAreaDao dao
    ) {
        LOGGER.debug("constructed");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public ProgramArea getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        ProgramArea result = dao.findOne(id);
        if (result == null) {
            throw new ProgramAreaNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<ProgramArea> getAll() {
        LOGGER.debug("getAll called");
        return dao.findAllByOrderByNameAsc();
    }

}
