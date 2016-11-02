package org.hmhb.exception.user;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a {@link org.hmhb.user.HmhbUser} cannot be found.
 */
public class UserNotFoundException extends NotFoundException {

    /**
     * Constructs a {@link UserNotFoundException}.
     *
     * @param id the {@link org.hmhb.user.HmhbUser}'s database ID that wasn't found
     */
    public UserNotFoundException(Long id) {
        super("User wasn't found: id=" + id);
    }

}
