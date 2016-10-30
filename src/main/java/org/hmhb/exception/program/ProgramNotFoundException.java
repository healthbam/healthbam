package org.hmhb.exception.program;

import org.hmhb.exception.NotFoundException;

/**
 * Exception thrown when a {@link org.hmhb.program.Program} cannot be found.
 */
public class ProgramNotFoundException extends NotFoundException {

    /**
     * Constructs an {@link ProgramNotFoundException}.
     *
     * @param id the {@link org.hmhb.program.Program}'s database ID
     */
    public ProgramNotFoundException(long id) {
        super("Program wasn't found: id=" + id);
    }

}
