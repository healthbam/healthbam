package org.hmhb.exception.organization;

import java.net.MalformedURLException;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the passed in URL is invalid.
 */
public class OrganizationUrlIsInvalidException extends BadRequestException {

    /**
     * Constructs a {@link OrganizationUrlIsInvalidException}.
     *
     * @param e the causedBy exception with any information on the invalid URL
     */
    public OrganizationUrlIsInvalidException(MalformedURLException e) {
        super(e);
    }

}
