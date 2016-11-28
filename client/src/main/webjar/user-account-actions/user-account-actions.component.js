(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userAccountActions");

    /**
     * Controller for the healthBamUserAccountActions component.
     * @param $log
     * @param $mdDialog
     * @param $mdMedia
     * @param authenticationService
     * @constructor
     */
    function UserAccountActionsController(
        $log,
        $mdDialog,
        $mdMedia,
        authenticationService
    ) {

        var userAccountActions = this;

        /**
         * Opens the account-details dialog.
         */
        function viewAccount($event) {

            var dialogOptions = {
                templateUrl: "user-account-details.html",
                controller: "UserAccountDetailsController",
                controllerAs: "userAccountDetails",
                targetEvent: $event,
                fullscreen: !$mdMedia("gt-sm"),
                escapeToClose: true
            };

            return $mdDialog.show(dialogOptions);
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            userAccountActions.authenticate = authenticationService.authenticate;
            userAccountActions.isAuthenticated = authenticationService.isAuthenticated;
            userAccountActions.getUser = authenticationService.getUser;
            userAccountActions.viewAccount = viewAccount;
            userAccountActions.logout = authenticationService.logout;

            $log.debug("UserAccountActions Controller loaded", userAccountActions);
        }

        /* Run activate when component is loaded. */
        userAccountActions.$onInit = activate;
    }

    /* Inject dependencies. */
    UserAccountActionsController.$inject = [
        "$log",
        "$mdDialog",
        "$mdMedia",
        "authenticationService"
    ];

    /* Create healthBamUserAccountActions component. */
    module.component(
        "healthBamUserAccountActions",
        {
            templateUrl: "user-account-actions.html",
            controller: UserAccountActionsController,
            controllerAs: "userAccountActions"
        }
    );

}(window.angular));
