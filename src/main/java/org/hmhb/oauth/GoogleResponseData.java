package org.hmhb.oauth;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.plus.model.Person;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An object to hold the user's information from google's responses.
 */
@Immutable
public class GoogleResponseData {

    private final GoogleTokenResponse googleOauthToken;
    private final Person googlePlusProfile;

    /**
     * Constructs a {@link GoogleResponseData}.
     *
     * @param googleOauthToken google's oauth token response
     * @param googlePlusProfile google's g+ profile response
     */
    public GoogleResponseData(
            @Nonnull GoogleTokenResponse googleOauthToken,
            @Nonnull Person googlePlusProfile
    ) {
        this.googleOauthToken = requireNonNull(googleOauthToken, "googleOauthToken cannot be null");
        this.googlePlusProfile = requireNonNull(googlePlusProfile, "googlePlusProfile cannot be null");
    }

    public GoogleTokenResponse getGoogleOauthToken() {
        return googleOauthToken;
    }

    public Person getGooglePlusProfile() {
        return googlePlusProfile;
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
