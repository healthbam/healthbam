package org.hmhb.exception.program;

import org.hmhb.exception.NotAuthorizedException;

/**
 * An exception to indicate that a non-admin tried to save a
 * {@link org.hmhb.program.Program}.
 */
public class OnlyAdminCanSaveProgramException extends NotAuthorizedException {
}
