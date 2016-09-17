package org.hmhb.exception.program.published;

import org.hmhb.exception.NotFoundException;

public class PublishedProgramNotFoundException extends NotFoundException {

    public PublishedProgramNotFoundException(long id) {
        super("Program wasn't found: id=" + id);
    }

}
