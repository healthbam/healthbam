package org.hmhb.csv;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Set;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.county.County;
import org.hmhb.program.Program;
import org.hmhb.programarea.ProgramArea;
import org.hmhb.user.HmhbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link CsvService}.
 */
@Service
public class DefaultCsvService implements CsvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCsvService.class);

    /**
     * An injectable constructor.
     */
    public DefaultCsvService() {
        LOGGER.debug("constructed");
    }

    private String handleString(
            @Nullable String data
    ) {
        if (data == null) {
            return "";
        }

        if (data.contains(",") || data.contains("\"")) {
            return "\"" + data.replace("\"", "\"\"") + "\"";
        } else {
            return data;
        }
    }

    private String flattenProgramAreas(
            @Nonnull Set<ProgramArea> programAreas
    ) {
        requireNonNull(programAreas, "programAreas cannot be null");

        if (programAreas.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (ProgramArea programArea : programAreas) {
            sb.append(programArea.getName()); /* None of our program areas have a comma or double quote. */
            sb.append(',');
        }

        String toRet = sb.toString();

        if (!toRet.isEmpty()) {
            /* Strip away the trailing comma. */
            toRet = toRet.substring(0, toRet.length() - 1);
        }

        return "\"" + toRet + "\"";
    }

    private String flattenCounties(
            @Nonnull Set<County> counties
    ) {
        requireNonNull(counties, "counties cannot be null");

        if (counties.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (County county : counties) {
            sb.append(county.getName()); /* None of our counties have a comma or double quote. */
            sb.append(',');
        }

        String toRet = sb.toString();

        if (!toRet.isEmpty()) {
            /* Strip away the trailing comma. */
            toRet = toRet.substring(0, toRet.length() - 1);
        }

        return "\"" + toRet + "\"";
    }

    @Timed
    @Override
    public String generateFromPrograms(
            @Nonnull List<Program> programs,
            @Nullable Boolean expandCounties,
            @Nullable Boolean expandProgramAreas
    ) {
        LOGGER.debug(
                "generateFromPrograms called: expandCounties={}, expandProgramAreas={}",
                expandCounties,
                expandProgramAreas
        );
        requireNonNull(programs, "programs cannot be null");

        StringBuilder toReturn = new StringBuilder();

        /* Print the header row. */
        toReturn.append("org_name,org_email,org_phone,org_facebook,org_website,");
        toReturn.append("prog_name,prog_year,prog_street,prog_city,prog_state,prog_zip,prog_coordinates,");
        toReturn.append("prog_goal1,prog_goal2,prog_goal3,prog_outcome1,prog_outcome2,prog_outcome3,");
        toReturn.append("prog_areas,prog_area_explanation,prog_counties\n");

        for (Program program : programs) {

            /* organization information */
            toReturn.append(handleString(program.getOrganization().getName()));
            toReturn.append(',');
            toReturn.append(handleString(program.getOrganization().getContactEmail()));
            toReturn.append(',');
            toReturn.append(handleString(program.getOrganization().getContactPhone()));
            toReturn.append(',');
            toReturn.append(handleString(program.getOrganization().getFacebookUrl()));
            toReturn.append(',');
            toReturn.append(handleString(program.getOrganization().getWebsiteUrl()));
            toReturn.append(',');

            toReturn.append(handleString(program.getName()));
            toReturn.append(',');

            if (program.getStartYear() != null) {
                toReturn.append(program.getStartYear());
            }
            toReturn.append(',');

            /* location information */
            toReturn.append(handleString(program.getStreetAddress()));
            toReturn.append(',');
            toReturn.append(handleString(program.getCity()));
            toReturn.append(',');
            toReturn.append(handleString(program.getState()));
            toReturn.append(',');
            toReturn.append(handleString(program.getZipCode()));
            toReturn.append(',');
            toReturn.append(handleString(program.getCoordinates()));
            toReturn.append(',');

            toReturn.append(handleString(program.getPrimaryGoal1()));
            toReturn.append(',');
            toReturn.append(handleString(program.getPrimaryGoal2()));
            toReturn.append(',');
            toReturn.append(handleString(program.getPrimaryGoal3()));
            toReturn.append(',');

            toReturn.append(handleString(program.getMeasurableOutcome1()));
            toReturn.append(',');
            toReturn.append(handleString(program.getMeasurableOutcome2()));
            toReturn.append(',');
            toReturn.append(handleString(program.getMeasurableOutcome3()));
            toReturn.append(',');

            toReturn.append(flattenProgramAreas(program.getProgramAreas()));
            toReturn.append(',');

            toReturn.append(handleString(program.getOtherProgramAreaExplanation()));
            toReturn.append(',');

            /* Don't end this one with a comma. */
            if (program.isServesAllCounties()) {
                toReturn.append("ALL");
            } else {
                toReturn.append(flattenCounties(program.getCountiesServed()));
            }

            toReturn.append('\n');
        }

        return toReturn.toString();
    }

    @Timed
    @Override
    public String generateFromUsers(
            @Nonnull List<HmhbUser> users
    ) {
        LOGGER.debug(
                "generateFromUsers called"
        );
        requireNonNull(users, "users cannot be null");

        StringBuilder toReturn = new StringBuilder();

        /* Print the header row. */
        toReturn.append("email,super_admin,admin,display_name,");
        toReturn.append("first_name,middle_name,last_name,prefix,suffix,");
        toReturn.append("image_url,profile_url\n");

        for (HmhbUser user : users) {

            toReturn.append(handleString(user.getEmail()));
            toReturn.append(',');

            toReturn.append(user.isSuperAdmin());
            toReturn.append(',');

            toReturn.append(user.isAdmin());
            toReturn.append(',');

            toReturn.append(handleString(user.getDisplayName()));
            toReturn.append(',');

            toReturn.append(handleString(user.getFirstName()));
            toReturn.append(',');

            toReturn.append(handleString(user.getMiddleName()));
            toReturn.append(',');

            toReturn.append(handleString(user.getLastName()));
            toReturn.append(',');

            toReturn.append(handleString(user.getPrefix()));
            toReturn.append(',');

            toReturn.append(handleString(user.getSuffix()));
            toReturn.append(',');

            toReturn.append(handleString(user.getImageUrl()));
            toReturn.append(',');

            toReturn.append(handleString(user.getProfileUrl()));
            toReturn.append('\n');

        }

        return toReturn.toString();
    }

}
