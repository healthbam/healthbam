(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userFormDialog");

    /**
     * Controller for the user-form dialog.
     * @param $mdDialog
     * @param $q
     * @param getConfig
     * @param errorHandlingService
     * @param $log
     * @constructor
     */
    function UserFormDialogController(
        $mdDialog,
        $q,
        getConfig,
        errorHandlingService,
        $log
    ) {
        var userFormDialog = this;

        /**
         * Cancels the dialog.
         * @returns promise.
         */
        function cancel() {
            return $mdDialog.cancel(userFormDialog.user);
        }

        /**
         * Handles a successful save of the User.
         * @returns promise for hiding dialog.
         */
        function handleUserSaveSuccess() {
            return $mdDialog.hide(userFormDialog.user);
        }

        /**
         * Handles an error saving the User.
         * @returns rejected promise of error input.
         */
        function handleUserSaveError(error) {
            $log.debug("user save error", error);
            errorHandlingService.handleError("Failed to save user.");
            return $q.reject(error);
        }

        /**
         * Saves form and closes the dialog.
         * @returns promise.
         */
        function save() {

            userFormDialog.loading = true;

            $log.debug("User Form Dialog saving to server");

            return userFormDialog.user.$save().then(
                handleUserSaveSuccess
            ).catch(
                handleUserSaveError
            ).finally(
                function () {
                    userFormDialog.loading = false;
                    $log.debug("User Form Dialog done saving to server");
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            userFormDialog.config = getConfig();

            userFormDialog.cancel = cancel;
            userFormDialog.save = save;

            $log.debug("User Form Dialog Controller loaded", userFormDialog);
        }

        /* Not a component. Run activate manually. */
        activate();
    }

    /* Inject dependencies. */
    UserFormDialogController.$inject = [
        "$mdDialog",
        "$q",
        "getConfig",
        "errorHandlingService",
        "$log"
    ];

    /* Create controller. */
    module.controller(
        "UserFormDialogController",
        UserFormDialogController
    );

}(window.angular));
