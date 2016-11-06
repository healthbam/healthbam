package org.hmhb.csv;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import org.hmhb.program.Program;
import org.hmhb.user.HmhbUser;

/**
 * A service to generate CSV from our data.
 */
public interface CsvService {

    /**
     * Generates the contents for a CSV from the passed in {@link Program}s.
     *
     * @param programs the list of {@link Program}s to generate CSV from
     * @param expandCounties whether a program's counties should not be
     *                       flattened into one row (comma delimited)
     * @param expandProgramAreas whether a program's program areas should not
     *                           be flattened into one row (comma delimited)
     * @return the CSV of the passed in programs
     */
    String generateFromPrograms(
            @Nonnull List<Program> programs,
            @Nullable Boolean expandCounties,
            @Nullable Boolean expandProgramAreas
    );

    /**
     * Generates the contents for a CSV from the passed in {@link HmhbUser}s.
     *
     * @param users the list of {@link HmhbUser}s to generate CSV from
     * @return the CSV of the passed in users
     */
    String generateFromUsers(
            @Nonnull List<HmhbUser> users
    );

}
