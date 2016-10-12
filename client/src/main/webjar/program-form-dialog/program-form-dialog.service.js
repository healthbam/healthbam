(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programFormDialog");

    /**
     * Service for creating program-form dialog instances.
     * @param $mdDialog
     * @param $mdMedia
     * @param Program
     * @returns programFormDialogService.
     */
    function programFormDialogServiceFactory(
        $mdDialog,
        $mdMedia,
        Program
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
                    program: new Program(
                        {
                            countiesServed: [],
                            state: "GA"
                        }
                    )
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
        "Program"
    ];

    /* Create programFormDialogService. */
    module.factory(
        "programFormDialogService",
        programFormDialogServiceFactory
    );

}(window.angular));
