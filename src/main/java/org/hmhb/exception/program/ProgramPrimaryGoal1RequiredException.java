package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that a {@link org.hmhb.program.Program} cannot be
 * created without a primary goal.
 */
public class ProgramPrimaryGoal1RequiredException extends BadRequestException {
}
