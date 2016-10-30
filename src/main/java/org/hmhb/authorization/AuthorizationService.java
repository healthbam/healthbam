package org.hmhb.authorization;

import org.hmhb.user.HmhbUser;

/**
 * Service to get authorization information about the current user.
 */
public interface AuthorizationService {

    /**
     * Returns whether a user is logged in or not.
     *
     * @return true if a user is logged in
     */
    boolean isLoggedIn();

    /**
     * Returns whether the logged in user is an admin or not.
     *
     * @return true if a user is logged in and is an admin, false otherwise
     */
    boolean isAdmin();

    /**
     * Returns the logged in user.
     *
     * @return the logged in user, or null if the user isn't logged in
     */
    HmhbUser getLoggedInUser();

}
