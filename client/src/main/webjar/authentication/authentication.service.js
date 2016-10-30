(function (angular) {
    "use strict";

    var module = angular.module("healthBam.authentication");

    /**
     * Factory for creating the authenticationService, which handles login/logout actions.
     */
    function authenticationServiceFactory(
        $log,
        $q,
        $auth,
        errorHandlingService
    ) {

        /**
         * Pops open satellizer's login prompt.
         * @returns promise from login.
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
                    errorHandlingService.handleError("Sign-in failed.");
                    return $q.reject(response);
                }
            );
        }

        /**
         * Returns true if the user has a token and it isn't expired.
         * @returns {boolean} true iff current user is authenticated.
         */
        function isAuthenticated() {
            return $auth.isAuthenticated();
        }

        /**
         * Returns true if the user is logged in and is an admin.
         * @returns {boolean} true iff current user is authenticated and an admin.
         */
        function isAdmin() {
            if (isAuthenticated()) {
                return $auth.getPayload().admin;
            }
            return false;
        }

        /**
         * Returns the object containing all user information, or undefined if not logged in.
         * @returns {Object} user data.
         */
        function getUser() {
            if (isAuthenticated()) {
                return $auth.getPayload();
            }
        }

        /**
         * Returns the user's JWT token.
         * @returns {string} JWT token.
         */
        function getToken() {
            $log.debug("getToken called");
            return $auth.getToken();
        }

        /**
         * Logs the user out by deleting their token from localStorage.
         * @returns promise from logout.
         */
        function logout() {
            $log.debug("logout called");
            return $auth.logout();
        }

        /* Return service instance. */
        return {
            authenticate: authenticate,
            getToken: getToken,
            getUser: getUser,
            isAdmin: isAdmin,
            isAuthenticated: isAuthenticated,
            logout: logout
        };
    }

    /* Inject dependencies. */
    authenticationServiceFactory.$inject = [
        "$log",
        "$q",
        "$auth",
        "errorHandlingService"
    ];

    /* Register service. */
    module.factory(
        "authenticationService",
        authenticationServiceFactory
    );

}(window.angular));
