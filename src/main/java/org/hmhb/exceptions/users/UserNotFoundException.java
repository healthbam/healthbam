package org.hmhb.exceptions.users;

import org.hmhb.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(long id) {
        super("User wasn't found: userId=" + id);
    }

}
