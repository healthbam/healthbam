package org.hmhb.oauth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfo {

    private final String sub;
    private final String name;
    private final String givenName;
    private final String familyName;
    private final String picture;
    private final String email;
    private final boolean emailVerified;
    private final String locale;

    @JsonCreator
    public GoogleUserInfo(
            @JsonProperty("sub") String sub,
            @JsonProperty("name") String name,
            @JsonProperty("given_name") String givenName,
            @JsonProperty("family_name") String familyName,
            @JsonProperty("picture") String picture,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("locale") String locale
    ) {
        this.sub = sub;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.email = email;
        this.emailVerified = emailVerified;
        this.locale = locale;
    }

    public String getSub() {
        return sub;
    }

    public String getName() {
        return name;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPicture() {
        return picture;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getLocale() {
        return locale;
    }

}
