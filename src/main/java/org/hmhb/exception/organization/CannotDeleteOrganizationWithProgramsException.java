package org.hmhb.exception.organization;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the {@link org.hmhb.organization.Organization}
 * cannot be deleted because it has {@link org.hmhb.program.Program}s.
 */
public class CannotDeleteOrganizationWithProgramsException extends BadRequestException {

    /**
     * Constructs a {@link CannotDeleteOrganizationWithProgramsException}.
     *
     * @param id the {@link org.hmhb.organization.Organization}'s database ID
     */
    public CannotDeleteOrganizationWithProgramsException(long id) {
        super("There are existing programs that reference this organization: id=" + id);
    }

}
