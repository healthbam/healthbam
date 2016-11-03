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

    /**
     * Constructs a {@link PublicConfig}.
     *
     * @param googleOauthClientId the google oauth2 client ID
     * @param urlPrefix the configured url prefix
     */
    public PublicConfig(
            @Nonnull String googleOauthClientId,
            @Nullable String urlPrefix,
            int programStartYearMin,
            int programStreetAddressMaxLength,
            int programCityMaxLength,
            int programGoalMaxLength,
            int programOutcomeMaxLength
    ) {
        this.googleOauthClientId = requireNonNull(googleOauthClientId, "googleOauthClientId cannot be null");
        this.urlPrefix = urlPrefix;
        this.programStartYearMin = programStartYearMin;
        this.programStreetAddressMaxLength = programStreetAddressMaxLength;
        this.programCityMaxLength = programCityMaxLength;
        this.programGoalMaxLength = programGoalMaxLength;
        this.programOutcomeMaxLength = programOutcomeMaxLength;
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

    public int getProgramStartYearMin() {
        return programStartYearMin;
    }

    public int getProgramStreetAddressMaxLength() {
        return programStreetAddressMaxLength;
    }

    public int getProgramCityMaxLength() {
        return programCityMaxLength;
    }

    public int getProgramGoalMaxLength() {
        return programGoalMaxLength;
    }

    public int getProgramOutcomeMaxLength() {
        return programOutcomeMaxLength;
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
