(function (angular) {
    "use strict";

    var module = angular.module("healthBam.authentication");

    /**
     * Controller for the healthBamAuthentication component.
     * @param $log
     * @param authenticationService
     * @constructor
     */
    function AuthenticationController(
        $log,
        authenticationService
    ) {

        var auth = this;

        /**
         * Initializes the controller.
         */
        function activate() {

            auth.authenticate = authenticationService.authenticate;
            auth.isAuthenticated = authenticationService.isAuthenticated;
            auth.isAdmin = authenticationService.isAdmin;
            auth.getEmail = authenticationService.getEmail;
            auth.logout = authenticationService.logout;

            $log.debug("Authentication Controller loaded", auth);
        }

        /* Run activate when component is loaded. */
        auth.$onInit = activate;
    }

    /* Inject dependencies. */
    AuthenticationController.$inject = [
        "$log",
        "authenticationService"
    ];

    /* Create healthBamAuthentication component. */
    module.component(
        "healthBamAuthentication",
        {
            templateUrl: "authentication.html",
            controller: AuthenticationController,
            controllerAs: "auth"
        }
    );

}(window.angular));
