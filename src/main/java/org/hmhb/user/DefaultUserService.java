package org.hmhb.user;

import javax.annotation.Nonnull;

import org.hmhb.exception.user.UserNotFoundException;
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
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Override
    public HmhbUser getUserByEmail(
            @Nonnull String email
    ) {
        LOGGER.debug("getUserByEmail called: email={}", email);
        requireNonNull(email, "email cannot be null");

        HmhbUser user = dao.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(email);
        }

        return user;
    }

    @Override
    public HmhbUser provisionNewUser(
            @Nonnull String email
    ) {
        LOGGER.debug("provisionNewUser called: email={}", email);
        requireNonNull(email, "email cannot be null");

        HmhbUser user = new HmhbUser();
        user.setAdmin(false);
        user.setEmail(email);

        return dao.save(user);
    }

}
