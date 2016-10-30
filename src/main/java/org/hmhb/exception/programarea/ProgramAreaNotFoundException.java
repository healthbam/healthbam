package org.hmhb.exception.programarea;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a {@link org.hmhb.programarea.ProgramArea} cannot be
 * found.
 */
public class ProgramAreaNotFoundException extends NotFoundException {

    /**
     * Constructs an {@link ProgramAreaNotFoundException}.
     *
     * @param id the {@link org.hmhb.programarea.ProgramArea}'s database ID
     */
    public ProgramAreaNotFoundException(long id) {
        super("ProgramArea wasn't found: id=" + id);
    }

}
