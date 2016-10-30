package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a {@link org.hmhb.program.Program} cannot be
 * created without a measurable outcome.
 */
public class ProgramMeasurableOutcome1RequiredException extends BadRequestException {
}
