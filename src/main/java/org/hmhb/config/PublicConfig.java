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

    /**
     * Constructs a {@link PublicConfig}.
     *
     * @param googleOauthClientId the google oauth2 client ID
     * @param urlPrefix the configured url prefix
     */
    public PublicConfig(
            @Nonnull String googleOauthClientId,
            @Nullable String urlPrefix
    ) {
        this.googleOauthClientId = requireNonNull(googleOauthClientId, "googleOauthClientId cannot be null");
        this.urlPrefix = urlPrefix;
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
