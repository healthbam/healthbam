package org.hmhb.exception.organization;

import org.hmhb.exception.BadRequestException;

public class CannotDeleteOrganizationWithProgramsException extends BadRequestException {

    public CannotDeleteOrganizationWithProgramsException(long id) {
        super("There are existing programs that reference this organization: id=" + id);
    }

}
