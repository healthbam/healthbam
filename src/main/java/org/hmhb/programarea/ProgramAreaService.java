package org.hmhb.programarea;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Service for retrieving {@link ProgramArea}s.
 */
public interface ProgramAreaService {

    /**
     * Retrieves a {@link ProgramArea} by its database ID.
     *
     * @param id the database ID
     * @return the {@link ProgramArea}
     */
    ProgramArea getById(@Nonnull Long id);

    /**
     * Retrieves all {@link ProgramArea}s.
     *
     * @return all {@link ProgramArea}s.
     */
    List<ProgramArea> getAll();

}
