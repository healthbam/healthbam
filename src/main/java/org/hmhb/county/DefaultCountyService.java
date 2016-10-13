package org.hmhb.county;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exception.county.CountyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link CountyService}.
 */
@Service
public class DefaultCountyService implements CountyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCountyService.class);

    private final CountyDao dao;

    @Autowired
    public DefaultCountyService(
            @Nonnull CountyDao dao
    ) {
        LOGGER.debug("constructed");
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public County getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("getById called: id={}", id);
        requireNonNull(id, "id cannot be null");
        County result = dao.findOne(id);
        if (result == null) {
            throw new CountyNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<County> getAll() {
        LOGGER.debug("getAll called");
        return dao.findAllByOrderByNameAsc();
    }

    @Timed
    @Override
    public List<County> findByNameStartingWithIgnoreCase(
            @Nonnull String namePart
    ) {
        LOGGER.debug("findByNameStartingWithIgnoreCase called: namePart={}", namePart);
        requireNonNull(namePart, "namePart cannot be null");
        return dao.findByNameStartingWithIgnoreCase(namePart);
    }

}
