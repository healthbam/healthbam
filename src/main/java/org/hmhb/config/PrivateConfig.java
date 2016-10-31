package org.hmhb.config;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * Object to hold config that is only for the server (either explicitly cannot
 * be shared with the client, or it is irrelevant to the client).
 */
public class PrivateConfig {

    private final String googleOauthSecret;
    private final String jwtDomain;
    private final String jwtSecret;

    /**
     * Constructs a {@link PrivateConfig}.
     *
     * @param googleOauthSecret the google oauth2 client secret
     * @param jwtDomain the base domain used as issuer and audience for our
     *                  JWT tokens
     * @param jwtSecret the secret to sign our JWT tokens
     */
    public PrivateConfig(
            @Nonnull String googleOauthSecret,
            @Nonnull String jwtDomain,
            @Nonnull String jwtSecret
    ) {
        this.googleOauthSecret = requireNonNull(googleOauthSecret, "googleOauthSecret cannot be null");
        this.jwtDomain = requireNonNull(jwtDomain, "jwtDomain cannot be null");
        this.jwtSecret = requireNonNull(jwtSecret, "jwtSecret cannot be null");
    }

    /**
     * Returns the Oauth2 client secret for our server's communication with
     * google.
     *
     * @return the google oauth2 client secret
     */
    public String getGoogleOauthSecret() {
        return googleOauthSecret;
    }

    /**
     * Returns the base domain we are using as the issuer and audience for our
     * JWT tokens.
     *
     * @return the base domain used as issuer and audience for our JWT tokens
     */
    public String getJwtDomain() {
        return jwtDomain;
    }

    /**
     * Returns the secret we are using to sign our JWT tokens.
     *
     * @return the secret to sign our JWT tokens
     */
    public String getJwtSecret() {
        return jwtSecret;
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
