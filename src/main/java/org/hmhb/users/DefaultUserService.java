package org.hmhb.users;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.exceptions.users.EmailTooLongException;
import org.hmhb.exceptions.users.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserDao dao;

    @Autowired
    public DefaultUserService(
            @Nonnull UserDao dao
    ) {
        LOGGER.debug("{} constructed", DefaultUserService.class.getSimpleName());
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public HmhbUser getById(
            @Nonnull Long id
    ) {
        LOGGER.debug("{}.getById called: id={}", DefaultUserService.class, id);
        requireNonNull(id, "id cannot be null");
        HmhbUser result = dao.findOne(id);
        if (result == null) {
            throw new UserNotFoundException(id);
        }
        return result;
    }

    @Timed
    @Override
    public List<HmhbUser> getAll() {
        LOGGER.debug("{}.getAll called", DefaultUserService.class);
        List<HmhbUser> target = new ArrayList<>();
        dao.findAll().forEach(target::add);
        return target;
    }

    @Transactional
    @Timed
    @Override
    public HmhbUser delete(
            @Nonnull Long id
    ) {
        LOGGER.debug("{}.delete called: id={}", DefaultUserService.class, id);
        requireNonNull(id, "id cannot be null");
        HmhbUser user = getById(id); // verify it exists and they can see it
        // TODO - verify they can modify it
        dao.delete(id);
        return user;
    }

    @Transactional
    @Timed
    @Override
    public HmhbUser save(
            @Nonnull HmhbUser user
    ) {
        LOGGER.debug("{}.save called: user={}", DefaultUserService.class, user);
        requireNonNull(user, "user cannot be null");

        // TODO - put in validation: required fields not specified, regex of an email, names too long, etc.

        if (user.getEmail().length() > 100) {
            throw new EmailTooLongException(user.getEmail());
        }

        if (user.getId() != null) {
            getById(user.getId()); // verify they have access
            // TODO - verify they can modify it
        }

        return dao.save(user);

    }

}
