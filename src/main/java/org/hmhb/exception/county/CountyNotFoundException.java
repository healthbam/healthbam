package org.hmhb.exception.county;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a {@link org.hmhb.county.County} cannot be found.
 */
public class CountyNotFoundException extends NotFoundException {

    /**
     * Constructs a {@link CountyNotFoundException}.
     *
     * @param id the {@link org.hmhb.county.County}'s database ID
     */
    public CountyNotFoundException(long id) {
        super("County wasn't found: id=" + id);
    }

}
