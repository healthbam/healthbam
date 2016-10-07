package org.hmhb.exception.programarea;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a ProgramArea cannot be found.
 */
public class ProgramAreaNotFoundException extends NotFoundException {

    public ProgramAreaNotFoundException(long id) {
        super("ProgramArea wasn't found: id=" + id);
    }

}
