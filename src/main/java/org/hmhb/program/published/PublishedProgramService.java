package org.hmhb.program.published;

import javax.annotation.Nonnull;

import java.util.List;

public interface PublishedProgramService {

    PublishedProgram getById(@Nonnull Long id);

    List<PublishedProgram> getAll();

    PublishedProgram delete(@Nonnull Long id);

    PublishedProgram save(@Nonnull PublishedProgram program);

}
