(function (angular) {
    "use strict";

    var module = angular.module("healthBam.authentication");

    /**
     * Returns the protocol, host, and port part of our single-page-app's URL.
     *
     * Right now, this has to match what the server is sending to google, so it
     * has to be explicit with the port.
     */
    function getUrlPrefix() {
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

        return urlPrefix + ":" + port + "/";
    }

    /**
     * Configures satellizer's Google Oauth.
     */
    function configGoogleOauth(
        getConfig,
        $authProvider
    ) {
        var redirectUri = getUrlPrefix() + "views/oauth-callback";

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
                /* TODO: get the redirectUrl from the server */
                redirectUri: redirectUri, /* redirects to the angular app; this has to match what the server uses */
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
