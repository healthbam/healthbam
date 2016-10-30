package org.hmhb.exception.organization;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when an {@link org.hmhb.organization.Organization} cannot
 * be found.
 */
public class OrganizationNotFoundException extends NotFoundException {

    /**
     * Constructs an {@link OrganizationNotFoundException}.
     *
     * @param id the {@link org.hmhb.organization.Organization}'s database ID
     */
    public OrganizationNotFoundException(long id) {
        super("Organization wasn't found: id=" + id);
    }

}
