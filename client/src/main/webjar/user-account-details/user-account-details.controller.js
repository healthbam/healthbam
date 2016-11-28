(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userAccountDetails");

    /**
     * Controller for the user account details dialog.
     * @param $mdDialog
     * @param $log
     * @param authenticationService
     * @param userManagementService
     * @constructor
     */
    function UserAccountDetailsController(
        $mdDialog,
        $log,
        authenticationService,
        User,
        userManagementService
    ) {
        var userAccountDetails = this;

        /**
         * Handles a successful save of the Organization.
         * @returns promise for hiding dialog.
         */
        function close() {
            return $mdDialog.hide();
        }

        /**
         * Builds a User instance with data from the user token.
         * @returns {Object} current User instance.
         */
        function makeUserObject() {

            var userData,
                user;

            userData = authenticationService.getUser();

            user = new User();
            user.id = userData.userId;
            user.displayName = userData.displayName;
            user.email = userData.email;
            user.imageUrl = userData.imageUrl;
            user.admin = userData.admin;
            user.superAdmin = userData.superAdmin;

            return user;
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            userAccountDetails.close = close;
            userAccountDetails.deleteUser = userManagementService.deleteUser;
            userAccountDetails.getRole = userManagementService.getRole;
            userAccountDetails.user = makeUserObject();

            $log.debug("User Account Details Controller loaded", userAccountDetails);
        }

        /* Not a component. Run activate manually. */
        activate();
    }

    /* Inject dependencies. */
    UserAccountDetailsController.$inject = [
        "$mdDialog",
        "$log",
        "authenticationService",
        "User",
        "userManagementService"
    ];

    /* Create controller. */
    module.controller(
        "UserAccountDetailsController",
        UserAccountDetailsController
    );

}(window.angular));
