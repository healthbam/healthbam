package org.hmhb.config;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.core.env.Environment;

import static java.util.Objects.requireNonNull;

/**
 * Object to hold config that is only for the server (either explicitly cannot
 * be shared with the client, or it is irrelevant to the client).
 */
public class PrivateConfig {

    private final Environment environment;

    /**
     * Constructs a {@link PrivateConfig}.
     *
     * @param environment the {@link Environment} to get config values from
     */
    public PrivateConfig(
            @Nonnull Environment environment
    ) {
        this.environment = requireNonNull(environment, "environment cannot be null");
    }

    /**
     * Returns the Oauth2 client secret for our server's communication with
     * google.
     *
     * @return the google oauth2 client secret
     */
    public String getGoogleOauthSecret() {
        return environment.getProperty("google.oauth.client.secret");
    }

    /**
     * Returns the base domain we are using as the issuer and audience for our
     * JWT tokens.
     *
     * @return the base domain used as issuer and audience for our JWT tokens
     */
    public String getJwtDomain() {
        return environment.getProperty("hmhb.jwt.domain");
    }

    /**
     * Returns the secret we are using to sign our JWT tokens.
     *
     * @return the secret to sign our JWT tokens
     */
    public String getJwtSecret() {
        return environment.getProperty("hmhb.jwt.secret");
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
