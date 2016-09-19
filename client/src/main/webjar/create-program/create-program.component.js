(function (angular) {
    "use strict";

    var module = angular.module("healthBam.createProgram");

    /**
     * Controller for the healthBamCreateProgram component.
     * @param programFormDialogService
     * @param $log
     * @constructor
     */
    function CreateProgramController(
        programFormDialogService,
        $log
    ) {

        var createProgram = this;

        /**
         * Opens the program form dialog.
         * @returns promise that resolves when dialog is closed.
         */
        function openProgramForm($event) {
            return programFormDialogService.open($event);
        }

        /**
         * Initializes the controller.
         */
        function activate() {
            createProgram.openProgramForm = openProgramForm;

            $log.debug("CreateProgram Controller loaded", createProgram);
        }

        /* Run createProgram when component loaded. */
        createProgram.$onInit = activate;
    }

    /* Inject dependencies. */
    CreateProgramController.$inject = [
        "programFormDialogService",
        "$log"
    ];

    /* Create healthBamCreateProgram component. */
    module.component(
        "healthBamCreateProgram",
        {
            templateUrl: "create-program.html",
            controller: CreateProgramController,
            controllerAs: "createProgram"
        }
    );

}(window.angular));
