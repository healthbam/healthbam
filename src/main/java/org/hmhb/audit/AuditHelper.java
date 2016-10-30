package org.hmhb.audit;

import java.util.Date;

/**
 * Convenience methods to help get info for our audit columns.
 */
public interface AuditHelper {

    /**
     * Retrieves the user's IP address out of the http request.
     *
     * @return the user's IP address
     */
    String getCallerIp();

    /**
     * Retrieves the user's email address out of the http request.
     *
     * @return the user's email address, or null if user isn't logged in
     */
    String getCurrentUserEmail();

    /**
     * Returns the current time.  This is useful for mocking in the unit tests.
     *
     * @return the current time
     */
    Date getCurrentTime();

}
