(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userList");

    /**
     * Controller for the healthBam userList component.
     * @param $q
     * @param authenticationService
     * @param errorHandlingService
     * @param User
     * @param userManagementService
     * @param $state
     * @param $log
     * @constructor
     */
    function UserListController(
        $q,
        authenticationService,
        errorHandlingService,
        User,
        userManagementService,
        $state,
        $log
    ) {
        var userList = this;

        /**
         * Handles an error loading the User List.
         * @param error that occurred.
         * @returns rejected promise of error input.
         */
        function handleUserListLoadError(error) {
            $log.debug("user list load error", error);
            errorHandlingService.handleError("Failed to load user list.");
            return $q.reject(error);
        }

        /**
         * Loads the list of users.
         */
        function load() {
            userList.loading = true;
            $log.debug("UserList is loading");
            userList.list = User.query();
            userList.list.$promise.catch(
                handleUserListLoadError
            ).finally(
                function () {
                    userList.loading = false;
                    $log.debug("UserList has finished loading");
                }
            );
        }

        /**
         * Navigates to the map state.
         */
        function viewMap() {
            $state.go(
                "map"
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            userList.getRole = userManagementService.getRole;
            userList.grantAdmin = userManagementService.grantAdmin;
            userList.revokeAdmin = userManagementService.revokeAdmin;
            userList.deleteUser = userManagementService.deleteUser;

            if (authenticationService.isAdmin()) {
                load();

            } else {
                errorHandlingService.handleError("Insufficient privileges to view user list.");
                viewMap();
            }

            $log.debug("UserList Controller loaded", userList);
        }

        /* Run activate when component is loaded. */
        userList.$onInit = activate;
    }

    /* Inject dependencies. */
    UserListController.$inject = [
        "$q",
        "authenticationService",
        "errorHandlingService",
        "User",
        "userManagementService",
        "$state",
        "$log"
    ];

    /* Create healthBamUserList component. */
    module.component(
        "healthBamUserList",
        {
            templateUrl: "/user-list.html",
            controller: UserListController,
            controllerAs: "userList"
        }
    );

}(window.angular));
