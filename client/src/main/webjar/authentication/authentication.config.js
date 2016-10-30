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
                redirectUri: redirectUri, /* redirects to the angular app; this has to match what the server uses */
                /* TODO: get the clientId and redirectUrl from the server */
                clientId: "259324353484-f8u4ltb5qko7fltub68dguhs16ae93nr.apps.googleusercontent.com"
            }
        );
    }

    configGoogleOauth.$inject = [
        "$authProvider"
    ];

    module.config(configGoogleOauth);

}(window.angular));
