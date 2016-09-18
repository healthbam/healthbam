package org.hmhb.exception.program.requested;

import org.hmhb.exception.NotFoundException;

public class RequestedProgramNotFoundException extends NotFoundException {

    public RequestedProgramNotFoundException(long id) {
        super("Request wasn't found: id=" + id);
    }

}
