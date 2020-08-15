package org.hmhb.oauth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleWellKnownResponse {

    private final String userinfoEndpoint;

    @JsonCreator
    public GoogleWellKnownResponse(
            @JsonProperty("userinfo_endpoint") String userinfoEndpoint
    ) {
        this.userinfoEndpoint = userinfoEndpoint;
    }

    public String getUserinfoEndpoint() {
        return userinfoEndpoint;
    }

}
