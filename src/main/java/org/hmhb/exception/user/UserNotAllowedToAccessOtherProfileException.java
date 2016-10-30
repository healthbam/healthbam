package org.hmhb.exception.user;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that a non-admin tried to view / change someone
 * else's profile.
 */
public class UserNotAllowedToAccessOtherProfileException extends NotAuthorizedException {

    /**
     * Constructs a {@link UserNotAllowedToAccessOtherProfileException}.
     */
    public UserNotAllowedToAccessOtherProfileException() {
        super("Non-admins aren't allowed to access others' profiles!");
    }

}
