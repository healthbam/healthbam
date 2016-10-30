package org.hmhb.authentication;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * DTO for sending back a JWT token to the client.
 */
@Immutable
public class TokenResponse {

    private final String token;

    /**
     * Constructs a {@link TokenResponse}.
     *
     * @param token the JWT token
     */
    public TokenResponse(
            @Nonnull String token
    ) {
        this.token = requireNonNull(token, "token cannot be null");
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

    public String getToken() {
        return token;
    }

}
