package org.hmhb.exception.user;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the passed in email is too long.
 */
public class UserEmailTooLongException extends BadRequestException {
}
