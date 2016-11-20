(function (angular) {
    "use strict";

    var module = angular.module("healthBam.createUser");

    /**
     * Controller for the healthBamCreateUser component.
     * @param authenticationService
     * @param userFormDialogService
     * @param $log
     * @constructor
     */
    function CreateUserController(
        authenticationService,
        userFormDialogService,
        $state,
        $log
    ) {

        var createUser = this;

        /**
         * Reload the ui-view.
         *
         * @param data
         * @return {*}
         */
        function reloadView(data) {
            $state.reload();
            return data;
        }

        /**
         * Opens the user form dialog.
         * @returns promise that resolves when dialog is closed.
         */
        function openUserForm($event) {
            return userFormDialogService.create($event).then(reloadView);
        }

        /**
         * Initializes the controller.
         */
        function activate() {
            createUser.openUserForm = openUserForm;
            createUser.isAdmin = authenticationService.isAdmin;

            $log.debug("CreateUser Controller loaded", createUser);
        }

        /* Run activate when component is loaded. */
        createUser.$onInit = activate;
    }

    /* Inject dependencies. */
    CreateUserController.$inject = [
        "authenticationService",
        "userFormDialogService",
        "$state",
        "$log"
    ];

    /* Create healthBamCreateUser component. */
    module.component(
        "healthBamCreateUser",
        {
            templateUrl: "create-user.html",
            controller: CreateUserController,
            controllerAs: "createUser"
        }
    );

}(window.angular));
