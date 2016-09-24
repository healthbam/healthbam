package org.hmhb.exception.organization;

import org.hmhb.exception.NotFoundException;

public class OrganizationNotFoundException extends NotFoundException {

    public OrganizationNotFoundException(long id) {
        super("Organization wasn't found: id=" + id);
    }

}
