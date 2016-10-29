(function (angular) {
    "use strict";

    var module = angular.module("healthBam.authentication");

    /**
     * A service to authenticate
     */
    function authenticationServiceFactory(
        $log,
        $q,
        $auth
    ) {

        /**
         * Pops open satellizer's login prompt.
         */
        function authenticate() {
            $log.debug("authenticate called");

            return $auth.authenticate("google").then(
                function (response) {
                    $log.debug("login succeeded", response);
                    return response;
                }
            ).catch(
                function (response) {
                    $log.debug("login failed", response);
                    return $q.reject(response);
                }
            );
        }

        /**
         * Returns true if the user has a token and it isn't expired.
         */
        function isAuthenticated() {
            return $auth.isAuthenticated();
        }

        /**
         * Returns true if the user is logged in and is an admin.
         */
        function isAdmin() {
            if (isAuthenticated()) {
                return $auth.getPayload().admin;
            }
            return false;
        }

        /**
         * Returns the user's email or empty string if not logged in.
         */
        function getEmail() {
            if (isAuthenticated()) {
                return $auth.getPayload().email;
            }
            return "";
        }

        /**
         * Returns the user's JWT token.
         */
        function getToken() {
            $log.debug("getToken called");
            return $auth.getToken();
        }

        /**
         * Logs the user out by deleting their token from localStorage.
         */
        function logout() {
            $log.debug("logout called");
            return $auth.logout();
        }

        return {
            authenticate: authenticate,
            isAuthenticated: isAuthenticated,
            isAdmin: isAdmin,
            getEmail: getEmail,
            getToken: getToken,
            logout: logout
        };
    }

    authenticationServiceFactory.$inject = [
        "$log",
        "$q",
        "$auth"
    ];

    module.factory(
        "authenticationService",
        authenticationServiceFactory
    );

}(window.angular));
