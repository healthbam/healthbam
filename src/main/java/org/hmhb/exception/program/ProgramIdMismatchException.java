package org.hmhb.exception.program;

import org.hmhb.exception.BadRequestException;

/**
 * An exception to indicate that the request's program IDs didn't match
 * (one from the path, one from the body).
 */
public class ProgramIdMismatchException extends BadRequestException {

    /**
     * Constructs an {@link ProgramIdMismatchException}.
     *
     * @param pathProgramId the ID passed in the url path
     * @param bodyProgramId the ID passed in the http JSON body
     */
    public ProgramIdMismatchException(
            long pathProgramId,
            Long bodyProgramId
    ) {
        super("Program IDs didn't match! pathId=" + pathProgramId + ", bodyId=" + bodyProgramId);
    }

}
