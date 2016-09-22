(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programFormDialog");

    /**
     * Service for creating program-form dialog instances.
     * @param $mdDialog
     * @param $mdMedia
     * @param RequestedProgram
     * @returns programFormDialogService.
     */
    function programFormDialogServiceFactory(
        $mdDialog,
        $mdMedia,
        RequestedProgram
    ) {

        /**
         * Opens an instance of the program-form dialog.
         * @param $event
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function open($event) {

            var dialogOptions = {
                templateUrl: "program-form-dialog.html",
                controller: "ProgramFormDialogController",
                controllerAs: "programFormDialog",
                targetEvent: $event,
                fullscreen: !$mdMedia("gt-sm"),
                escapeToClose: false,
                bindToController: true,
                locals: {
                    requestedProgram: new RequestedProgram()
                }
            };

            return $mdDialog.show(dialogOptions);
        }

        return {
            open: open
        };
    }

    /* Inject dependencies. */
    programFormDialogServiceFactory.$inject = [
        "$mdDialog",
        "$mdMedia",
        "RequestedProgram"
    ];

    /* Create programFormDialogService. */
    module.factory(
        "programFormDialogService",
        programFormDialogServiceFactory
    );

}(window.angular));
