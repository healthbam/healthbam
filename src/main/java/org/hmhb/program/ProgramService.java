package org.hmhb.program;

import javax.annotation.Nonnull;

import java.util.List;

import org.hmhb.mapquery.MapQuery;

public interface ProgramService {

    Program getById(@Nonnull Long id);

    List<Program> getByIds(@Nonnull List<Long> ids);

    List<Program> getAll();

    List<Program> search(@Nonnull MapQuery query);

    Program delete(@Nonnull Long id);

    Program save(@Nonnull Program request);

}
