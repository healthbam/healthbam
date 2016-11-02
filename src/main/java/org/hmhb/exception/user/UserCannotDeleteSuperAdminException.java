package org.hmhb.exception.user;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that the super-admin cannot be deleted.
 */
public class UserCannotDeleteSuperAdminException extends NotAuthorizedException {

    /**
     * Constructs a {@link UserCannotDeleteSuperAdminException}.
     */
    public UserCannotDeleteSuperAdminException() {
        super("Super admins cannot be deleted!");
    }

}
