package org.hmhb.exception.organization;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the request's organization IDs didn't match
 * (one from the path, one from the body).
 */
public class OrganizationIdMismatchException extends BadRequestException {

    /**
     * Constructs an {@link OrganizationIdMismatchException}.
     *
     * @param pathOrgId the ID passed in the url path
     * @param bodyOrgId the ID passed in the http JSON body
     */
    public OrganizationIdMismatchException(
            long pathOrgId,
            Long bodyOrgId
    ) {
        super("Organization IDs didn't match! pathId=" + pathOrgId + ", bodyId=" + bodyOrgId);
    }

}
