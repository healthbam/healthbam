package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a {@link org.hmhb.program.Program} cannot be
 * created without an {@link org.hmhb.organization.Organization}.
 */
public class ProgramOrganizationRequiredException extends BadRequestException {
}
