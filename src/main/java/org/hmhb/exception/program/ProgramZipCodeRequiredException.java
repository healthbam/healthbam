package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a {@link org.hmhb.program.Program} cannot be
 * created without a zip code.
 */
public class ProgramZipCodeRequiredException extends BadRequestException {
}
