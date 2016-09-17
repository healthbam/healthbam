package org.hmhb.program.requested;

import javax.annotation.Nonnull;

import java.util.List;

public interface RequestedProgramService {

    RequestedProgram getById(@Nonnull Long id);

    List<RequestedProgram> getAll();

    RequestedProgram delete(@Nonnull Long id);

    RequestedProgram save(@Nonnull RequestedProgram request);

    void publish(@Nonnull RequestedProgram request);

}
