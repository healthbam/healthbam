package org.hmhb.exception.county;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a County cannot be found.
 */
public class CountyNotFoundException extends NotFoundException {

    public CountyNotFoundException(long id) {
        super("County wasn't found: id=" + id);
    }

}
