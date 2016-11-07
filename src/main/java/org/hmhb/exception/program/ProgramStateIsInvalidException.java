package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the state passed in isn't a valid US state.
 */
public class ProgramStateIsInvalidException extends BadRequestException {
}
