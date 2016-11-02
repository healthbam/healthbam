package org.hmhb.exception.user;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that a non-admin tried to modify their profile to
 * be an admin.
 */
public class UserNonAdminCannotEscalateToAdminException extends NotAuthorizedException {

    /**
     * Constructs a {@link UserNonAdminCannotEscalateToAdminException}.
     */
    public UserNonAdminCannotEscalateToAdminException() {
        super("Non-admins cannot modify themselves to be an admin!");
    }

}
