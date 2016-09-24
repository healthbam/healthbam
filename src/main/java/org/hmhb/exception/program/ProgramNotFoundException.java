package org.hmhb.exception.program;

import org.hmhb.exception.NotFoundException;

public class ProgramNotFoundException extends NotFoundException {

    public ProgramNotFoundException(long id) {
        super("Program wasn't found: id=" + id);
    }

}
