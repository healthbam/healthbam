package org.hmhb.exception.program.published;

import org.hmhb.exception.BadRequestException;

public class UpdateRequestsExistException extends BadRequestException {

    public UpdateRequestsExistException(long id) {
        super("There are pending update requests for this program: id=" + id);
    }

}
