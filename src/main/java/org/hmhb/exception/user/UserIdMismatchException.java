package org.hmhb.exception.user;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the request's user IDs didn't match
 * (one from the path, one from the body).
 */
public class UserIdMismatchException extends BadRequestException {

    /**
     * Constructs an {@link UserIdMismatchException}.
     *
     * @param pathUserId the ID passed in the url path
     * @param bodyUserId the ID passed in the http JSON body
     */
    public UserIdMismatchException(
            long pathUserId,
            Long bodyUserId
    ) {
        super("User IDs didn't match! pathId=" + pathUserId + ", bodyId=" + bodyUserId);
    }

}
