package org.hmhb.exception.organization;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that an {@link org.hmhb.organization.Organization}
 * cannot be created without a name.
 */
public class OrganizationNameRequiredException extends BadRequestException {
}
