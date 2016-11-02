package org.hmhb.exception.user;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a user must have an email.
 */
public class UserEmailRequiredException extends BadRequestException {

    /**
     * Constructs a {@link UserEmailRequiredException}.
     */
    public UserEmailRequiredException() {
        super("A user must have an email!");
    }

}
