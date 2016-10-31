(function (angular) {
    "use strict";

    var module = angular.module("healthBam.config", []);

    /**
     * Returns config data (in the real code, this will come from the server).
     * @returns {{googleOauthClientId: string, urlPrefix: null}}
     */
    function getConfig() {
        return {
            googleOauthClientId: "test-oauth-client-id",
            urlPrefix: null
        };
    }

    module.constant("getConfig", getConfig);

}(window.angular));
