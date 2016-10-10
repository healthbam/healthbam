package org.hmhb.exception.user;

import org.hmhb.exception.NotFoundException;
import org.hmhb.user.HmhbUser;

/**
 * Exception thrown when a {@link HmhbUser} cannot be found.
 */
public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String email) {
        super("User wasn't found: email=" + email);
    }

    public UserNotFoundException(Long id) {
        super("User wasn't found: id=" + id);
    }

}
