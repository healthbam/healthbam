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
         * @param $event that triggered the action.
         * @param {Program} program to edit.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function open(
            $event,
            program
        ) {

            var dialogOptions = {
                templateUrl: "/program-form-dialog.html",
                controller: "ProgramFormDialogController",
                controllerAs: "programFormDialog",
                targetEvent: $event,
                fullscreen: !$mdMedia("gt-sm"),
                escapeToClose: false,
                bindToController: true,
                locals: {
                    program: program
                }
            };

            return $mdDialog.show(dialogOptions);
        }

        /**
         * Opens an instance of the program-form dialog to create a new program.
         * @param $event that triggered the action.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function create(
            $event
        ) {
            return open(
                $event,
                new Program(
                    {
                        countiesServed: [],
                        programAreas: [],
                        state: "GA"
                    }
                )
            );
        }

        /**
         * Opens an instance of the program-form dialog to edit an existing program.
         * @param $event that triggered the action.
         * @param {Program} program to edit.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function edit(
            $event,
            program
        ) {
            return open(
                $event,
                angular.copy(program)
            );
        }

        return {
            create: create,
            edit: edit
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
