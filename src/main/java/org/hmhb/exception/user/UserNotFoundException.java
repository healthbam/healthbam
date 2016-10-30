package org.hmhb.exception.user;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a {@link org.hmhb.user.HmhbUser} cannot be found.
 */
public class UserNotFoundException extends NotFoundException {

    /**
     * Constructs a {@link UserNotFoundException}.
     *
     * @param email the {@link org.hmhb.user.HmhbUser}'s email that wasn't found
     */
    public UserNotFoundException(String email) {
        super("User wasn't found: email=" + email);
    }

}
