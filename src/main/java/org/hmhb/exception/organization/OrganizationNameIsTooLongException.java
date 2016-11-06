package org.hmhb.exception.organization;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the passed in name for an org was too long.
 */
public class OrganizationNameIsTooLongException extends BadRequestException {
}
