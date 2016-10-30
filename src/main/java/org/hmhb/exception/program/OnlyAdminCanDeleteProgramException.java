package org.hmhb.exception.program;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that a non-admin tried to delete a
 * {@link org.hmhb.program.Program}.
 */
public class OnlyAdminCanDeleteProgramException extends NotAuthorizedException {
}
