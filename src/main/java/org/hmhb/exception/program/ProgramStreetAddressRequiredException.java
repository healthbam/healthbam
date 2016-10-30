package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a {@link org.hmhb.program.Program} cannot be
 * created without a street address.
 */
public class ProgramStreetAddressRequiredException extends BadRequestException {
}
