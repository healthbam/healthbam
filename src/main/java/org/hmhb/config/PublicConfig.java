package org.hmhb.config;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.core.env.Environment;

import static java.util.Objects.requireNonNull;

/**
 * DTO to hold config that is ok to share with the client.
 */
public class PublicConfig {

    private final Environment environment;

    /**
     * Constructs a {@link PublicConfig}.
     *
     * @param environment the {@link Environment} to get config values from
     */
    public PublicConfig(
            @Nonnull Environment environment
    ) {
        this.environment = requireNonNull(environment, "environment cannot be null");
    }

    /**
     * Returns the Oauth2 client ID for our server's communication with google.
     *
     * @return the google oauth2 client ID
     */
    public String getGoogleOauthClientId() {
        return environment.getProperty("google.oauth.client.id");
    }

    /**
     * Returns the configured url prefix (useful for allowing your local
     * instances to generate external KML links for google maps.
     *
     * @return the configured url prefix
     */
    public String getUrlPrefix() {
        return environment.getProperty("hmhb.url.prefix");
    }

    /**
     * Returns the configured min start year allowed for a program.
     *
     * @return the min start year allowed for a program
     */
    public int getProgramStartYearMin() {
        return environment.getProperty("hmhb.program.startYear.minValue", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a program's street address.
     *
     * @return the max chars allowed for a program's street address
     */
    public int getProgramStreetAddressMaxLength() {
        return environment.getProperty("hmhb.program.streetAddress.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a program's city.
     *
     * @return the max chars allowed for a program's city
     */
    public int getProgramCityMaxLength() {
        return environment.getProperty("hmhb.program.city.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for program's primary goals.
     *
     * @return the max chars allowed for program's primary goals
     */
    public int getProgramGoalMaxLength() {
        return environment.getProperty("hmhb.program.goal.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for program's measurable
     * outcomes.
     *
     * @return the max chars allowed for program's measurable outcomes
     */
    public int getProgramOutcomeMaxLength() {
        return environment.getProperty("hmhb.program.outcome.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a program's name.
     *
     * @return the max chars allowed for a program's name
     */
    public int getProgramNameMaxLength() {
        return environment.getProperty("hmhb.program.name.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a program's
     * other-program-area explanation.
     *
     * @return the max chars allowed for a program's other-program-area
     * explanation
     */
    public int getProgramAreaExplanationMaxLength() {
        return environment.getProperty("hmhb.program.otherProgramArea.explanation.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for an organization's name.
     *
     * @return the max chars allowed for an organization's name
     */
    public int getOrganizationNameMaxLength() {
        return environment.getProperty("hmhb.organization.name.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a URL (an org's facebook
     * or website).
     *
     * @return the max chars allowed for a URL
     */
    public int getUrlMaxLength() {
        return environment.getProperty("hmhb.url.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for an email (an org's email
     * contact).
     *
     * @return the max chars allowed for an email
     */
    public int getEmailMaxLength() {
        return environment.getProperty("hmhb.email.maxLength", Integer.class);
    }

    /**
     * Returns the configured max chars allowed for a phone number (an org's
     * phone contact).
     *
     * @return the max chars allowed for a phone number
     */
    public int getPhoneMaxLength() {
        return environment.getProperty("hmhb.phone.maxLength", Integer.class);
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
