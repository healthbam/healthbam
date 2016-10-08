package org.hmhb.programarea;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Logic layer for {@link ProgramArea} objects.
 */
public interface ProgramAreaService {

    /**
     * Look up a single {@link ProgramArea} by ID.
     * @param id of the {@link ProgramArea} to find.
     * @return matching {@link ProgramArea}.
     */
    ProgramArea getById(@Nonnull Long id);

    /**
     * Fetch all {@link ProgramArea} objects.
     * @return {@link List} of all {@link ProgramArea} objects.
     */
    List<ProgramArea> getAll();

}
