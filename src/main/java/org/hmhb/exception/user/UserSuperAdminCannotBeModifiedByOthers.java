package org.hmhb.exception.user;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that someone tried to update someone else's
 * super-admin profile.
 */
public class UserSuperAdminCannotBeModifiedByOthers extends NotAuthorizedException {

    /**
     * Constructs a {@link UserSuperAdminCannotBeModifiedByOthers}.
     */
    public UserSuperAdminCannotBeModifiedByOthers() {
        super("Super admins can only be modified by themselves!");
    }

}
