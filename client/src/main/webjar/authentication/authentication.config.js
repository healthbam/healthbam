(function (angular) {
    "use strict";

    var module = angular.module("healthBam.authentication");

    /**
     * Generates the Google OAuth callback redirect URI.
     *
     * Right now, this has to match what the server is sending to google, so it
     * is passed to the server to use.
     *
     * This must also be configured in:
     * https://console.developers.google.com/apis/credentials
     */
    function getRedirectUri() {
        /* location.protocol includes the colon */
        var urlPrefix = location.protocol + "//" + location.hostname,
            port = location.port;

        if (!port) {
            /* we have to infer it from the protocol */
            if (location.protocol === "http:") {
                port = 80;
            } else if (location.protocol === "https:") {
                port = 443;
            } else {
                throw new Error("Unknown protocol! " + location.protocol);
            }
        }

        return urlPrefix + ":" + port + "/views/oauth-callback";
    }

    /**
     * Configures satellizer's Google Oauth.
     */
    function configGoogleOauth(
        getConfig,
        $authProvider
    ) {
        var redirectUri = getRedirectUri();

        window.console.log("Registering google oauth with: redirectUri = ", redirectUri);

        $authProvider.google(
            {
                /* https://developers.google.com/identity/protocols/OAuth2UserAgent */
                prompt: "select_account",
                optionalUrlParams: [
                    "prompt"
                ],
                scope: [
                    "profile",
                    "email"
                ],
                url: "/auth/google", /* posts to our server */
                redirectUri: redirectUri,
                clientId: getConfig().googleOauthClientId
            }
        );
    }

    configGoogleOauth.$inject = [
        "getConfig",
        "$authProvider"
    ];

    module.config(configGoogleOauth);

}(window.angular));
