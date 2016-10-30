package org.hmhb.exception.authentication;

import org.hmhb.exception.NotAuthorizedException;

/**
 * Exception thrown when the client's passed in google-oauth-client-id doesn't
 * match what is configured.  The client's clientId should have ultimately came
 * from the server, so this indicates a bug in the code, or someone tampering
 * with things.
 */
public class ClientIdMismatchException extends NotAuthorizedException {

    /**
     * Constructs a {@link ClientIdMismatchException}.
     *
     * @param requestClientId the http request's client ID
     * @param configuredClientId the client ID from config
     */
    public ClientIdMismatchException(
            String requestClientId,
            String configuredClientId
    ) {
        super(
                "Unexpected client ID! request clientId="
                + requestClientId
                + ", configured clientId="
                + configuredClientId
        );
    }

}
