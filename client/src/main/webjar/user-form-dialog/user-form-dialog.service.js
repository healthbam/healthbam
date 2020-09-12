(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userFormDialog");

    /**
     * Service for creating user-form dialog instances.
     * @param $mdDialog
     * @param $mdMedia
     * @param User
     * @returns userFormDialogService.
     */
    function userFormDialogServiceFactory(
        $mdDialog,
        $mdMedia,
        User
    ) {

        /**
         * Opens an instance of the user-form dialog.
         * @param $event that triggered the action.
         * @param {User} user to edit.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function open(
            $event,
            user
        ) {

            var dialogOptions = {
                templateUrl: "/user-form-dialog.html",
                controller: "UserFormDialogController",
                controllerAs: "userFormDialog",
                targetEvent: $event,
                fullscreen: !$mdMedia("gt-sm"),
                escapeToClose: false,
                bindToController: true,
                locals: {
                    user: user
                }
            };

            return $mdDialog.show(dialogOptions);
        }

        /**
         * Opens an instance of the user-form dialog to create a new user.
         * @param $event that triggered the action.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function create(
            $event
        ) {
            return open(
                $event,
                new User(
                    {
                        admin: true
                    }
                )
            );
        }

        return {
            create: create
        };
    }

    /* Inject dependencies. */
    userFormDialogServiceFactory.$inject = [
        "$mdDialog",
        "$mdMedia",
        "User"
    ];

    /* Create userFormDialogService. */
    module.factory(
        "userFormDialogService",
        userFormDialogServiceFactory
    );

}(window.angular));
