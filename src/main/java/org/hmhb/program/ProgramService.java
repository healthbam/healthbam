package org.hmhb.program;

import javax.annotation.Nonnull;

import java.util.List;

public interface ProgramService {

    Program getById(@Nonnull Long id);

    List<Program> getAll();

    Program delete(@Nonnull Long id);

    Program save(@Nonnull Program request);

}
