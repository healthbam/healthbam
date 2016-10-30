package org.hmhb.user;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import com.google.api.services.plus.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link UserService}.
 */
@Service
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserDao dao;

    /**
     * An injectable constructor.
     *
     * @param dao the {@link UserDao} to save and retrieve {@link HmhbUser}s
     */
    @Autowired
    public DefaultUserService(
            @Nonnull UserDao dao
    ) {
        this.dao = requireNonNull(dao, "dao cannot be null");
    }

    @Timed
    @Override
    public HmhbUser saveWithGoogleData(
            @Nonnull String email,
            @Nonnull Person profile
    ) {
        LOGGER.debug("saveWithGoogleData called: email={}, profile={}", email, profile);
        requireNonNull(email, "email cannot be null");
        requireNonNull(profile, "profile cannot be null");

        HmhbUser user = dao.findByEmailIgnoreCase(email);

        if (user == null) {
            user = new HmhbUser();
            user.setAdmin(false);
            user.setEmail(email);
        }

        user.setProfileUrl(profile.getUrl());
        user.setDisplayName(profile.getDisplayName());
        user.setImageUrl(profile.getImage().getUrl());
        user.setLastName(profile.getName().getFamilyName());
        user.setFirstName(profile.getName().getGivenName());
        user.setMiddleName(profile.getName().getMiddleName());
        user.setPrefix(profile.getName().getHonorificPrefix());
        user.setSuffix(profile.getName().getHonorificSuffix());

        LOGGER.debug("updating user info: user={}", user);

        return dao.save(user);
    }

}
