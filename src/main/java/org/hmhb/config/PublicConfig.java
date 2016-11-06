package org.hmhb.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * DTO to hold config that is ok to share with the client.
 */
public class PublicConfig {

    private final String googleOauthClientId;
    private final String urlPrefix;
    private final int programStartYearMin;
    private final int programStreetAddressMaxLength;
    private final int programCityMaxLength;
    private final int programGoalMaxLength;
    private final int programOutcomeMaxLength;
    private final int programNameMaxLength;
    private final int programAreaExplanationMaxLength;
    private final int organizationNameMaxLength;

    /**
     * Constructs a {@link PublicConfig}.
     *
     * @param googleOauthClientId the google oauth2 client ID
     * @param urlPrefix the configured url prefix
     * @param programStartYearMin the min start year allowed for a program
     * @param programStreetAddressMaxLength the max chars allowed for a
     *                                      program's street address
     * @param programCityMaxLength the max chars allowed for a program's city
     * @param programGoalMaxLength the max chars allowed for program's
     *                             primary goals
     * @param programOutcomeMaxLength the max chars allowed for a program's
     *                                measurable outcomes
     * @param programNameMaxLength the max chars allowed for a program's name
     * @param programAreaExplanationMaxLength the max chars allowed for a
     *                                        program's other-program-area
     *                                        explanation
     * @param organizationNameMaxLength the max chars allowed for an
     *                                  organization's name
     */
    public PublicConfig(
            @Nonnull String googleOauthClientId,
            @Nullable String urlPrefix,
            int programStartYearMin,
            int programStreetAddressMaxLength,
            int programCityMaxLength,
            int programGoalMaxLength,
            int programOutcomeMaxLength,
            int programNameMaxLength,
            int programAreaExplanationMaxLength,
            int organizationNameMaxLength
    ) {
        this.googleOauthClientId = requireNonNull(googleOauthClientId, "googleOauthClientId cannot be null");
        this.urlPrefix = urlPrefix;
        this.programStartYearMin = programStartYearMin;
        this.programStreetAddressMaxLength = programStreetAddressMaxLength;
        this.programCityMaxLength = programCityMaxLength;
        this.programGoalMaxLength = programGoalMaxLength;
        this.programOutcomeMaxLength = programOutcomeMaxLength;
        this.programNameMaxLength = programNameMaxLength;
        this.programAreaExplanationMaxLength = programAreaExplanationMaxLength;
        this.organizationNameMaxLength = organizationNameMaxLength;
    }

    /**
     * Returns the Oauth2 client ID for our server's communication with google.
     *
     * @return the google oauth2 client ID
     */
    public String getGoogleOauthClientId() {
        return googleOauthClientId;
    }

    /**
     * Returns the configured url prefix (useful for allowing your local
     * instances to generate external KML links for google maps.
     *
     * @return the configured url prefix
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * Returns the configured min start year allowed for a program.
     *
     * @return the min start year allowed for a program
     */
    public int getProgramStartYearMin() {
        return programStartYearMin;
    }

    /**
     * Returns the configured max chars allowed for a program's street address.
     *
     * @return the max chars allowed for a program's street address
     */
    public int getProgramStreetAddressMaxLength() {
        return programStreetAddressMaxLength;
    }

    /**
     * Returns the configured max chars allowed for a program's city.
     *
     * @return the max chars allowed for a program's city
     */
    public int getProgramCityMaxLength() {
        return programCityMaxLength;
    }

    /**
     * Returns the configured max chars allowed for program's primary goals.
     *
     * @return the max chars allowed for program's primary goals
     */
    public int getProgramGoalMaxLength() {
        return programGoalMaxLength;
    }

    /**
     * Returns the configured max chars allowed for program's measurable
     * outcomes.
     *
     * @return the max chars allowed for program's measurable outcomes
     */
    public int getProgramOutcomeMaxLength() {
        return programOutcomeMaxLength;
    }

    /**
     * Returns the configured max chars allowed for a program's name.
     *
     * @return the max chars allowed for a program's name
     */
    public int getProgramNameMaxLength() {
        return programNameMaxLength;
    }

    /**
     * Returns the configured max chars allowed for a program's
     * other-program-area explanation.
     *
     * @return the max chars allowed for a program's other-program-area
     * explanation
     */
    public int getProgramAreaExplanationMaxLength() {
        return programAreaExplanationMaxLength;
    }

    /**
     * Returns the configured max chars allowed for an organization's name.
     *
     * @return the max chars allowed for an organization's name
     */
    public int getOrganizationNameMaxLength() {
        return organizationNameMaxLength;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
