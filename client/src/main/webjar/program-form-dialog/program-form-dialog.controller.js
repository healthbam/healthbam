(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programFormDialog");

    /**
     * Controller for the program-form dialog.
     * @param $mdDialog
     * @param $q
     * @param Organization
     * @param ProgramArea
     * @param errorHandlingService
     * @param $log
     * @constructor
     */
    function ProgramFormDialogController(
        $mdDialog,
        $q,
        Organization,
        ProgramArea,
        getConfig,
        errorHandlingService,
        $log
    ) {
        var programFormDialog = this;

        /**
         * Cancels the dialog.
         * @returns promise.
         */
        function cancel() {
            return $mdDialog.cancel(programFormDialog.program);
        }

        /**
         * Returns true iff programArea.value is true.
         * @param programArea
         * @returns {boolean} programArea.value.
         */
        function isProgramAreaSelected(programArea) {
            return programArea.value;
        }

        /**
         * Handles a successful save of the Program.
         * @returns promise for hiding dialog.
         */
        function handleProgramSaveSuccess() {
            return $mdDialog.hide(programFormDialog.program);
        }

        /**
         * Handles an error saving the Program.
         * @returns rejected promise of error input.
         */
        function handleProgramSaveError(error) {
            $log.debug("program save error", error);
            errorHandlingService.handleError("Failed to save program.");
            return $q.reject(error);
        }

        /**
         * Handles an error querying for the ProgramAreas.
         * @returns rejected promise of error input.
         */
        function handleProgramAreaQueryError(error) {
            $log.debug("program area query error", error);
            errorHandlingService.handleError("Failed to retrieve Program Areas.");
            return $q.reject(error);
        }

        /**
         * Handles an error querying for the Organizations.
         * @returns rejected promise of error input.
         */
        function handleOrganizationQueryError(error) {
            $log.debug("organization query error", error);
            errorHandlingService.handleError("Failed to retrieve Organizations.");
            return $q.reject(error);
        }

        /**
         * Saves form and closes the dialog.
         * @returns promise.
         */
        function save() {

            programFormDialog.loading = true;

            $log.debug("Program Form Dialog saving to server");

            programFormDialog.program.programAreas = programFormDialog.programAreas.filter(
                isProgramAreaSelected
            );

            if (!programFormDialog.otherProgramArea) {
                /* If "Other" not checked, don't save explanation field. */
                delete programFormDialog.program.otherProgramAreaExplanation;
            }

            return programFormDialog.program.$save().then(
                handleProgramSaveSuccess
            ).catch(
                handleProgramSaveError
            ).finally(
                function () {
                    programFormDialog.loading = false;
                    $log.debug("Program Form Dialog done saving to server");
                }
            );
        }

        /**
         * Returns the current year.
         * @returns {number} the current year.
         */
        function getCurrentYear() {
            return new Date().getFullYear();
        }

        /**
         * Returns true iff the program is associated with a new organization.
         * @returns {boolean} true if new organization.
         */
        function isNewOrganization() {
            return angular.isObject(programFormDialog.program.organization) &&
                !programFormDialog.program.organization.id;
        }

        /**
         * Sets value to true for all programAreas already applied to an existing program.
         * @returns input to support promise chaining.
         */
        function setAppliedProgramAreas(input) {

            programFormDialog.programAreas.forEach(
                function (programAreaChoice) {
                    /* Set value to true if the programAreaChoice is assigned to the program. */
                    programAreaChoice.value = programFormDialog.program.programAreas.some(
                        function (programArea) {
                            /* Return true if programArea on program matches choice. */
                            return programArea.id === programAreaChoice.id;
                        }
                    );
                }
            );

            return input;
        }

        /**
         * Fetch all initial information from the server.
         */
        function load() {

            programFormDialog.loading = true;

            $log.debug("Program Form Dialog loading from server");

            programFormDialog.programAreas = ProgramArea.query();
            programFormDialog.programAreas.$promise.catch(
                handleProgramAreaQueryError
            );

            programFormDialog.programAreas.$promise.then(
                setAppliedProgramAreas
            );

            programFormDialog.organizations = Organization.query();
            programFormDialog.organizations.$promise.catch(
                handleOrganizationQueryError
            );

            $q.all(
                [
                    programFormDialog.programAreas.$promise,
                    programFormDialog.organizations.$promise
                ]
            ).finally(
                function () {
                    programFormDialog.loading = false;
                    $log.debug("Program Form Dialog done loading from server");
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            programFormDialog.config = getConfig();

            programFormDialog.otherProgramArea = false;
            programFormDialog.newOrganization = new Organization();

            load();

            programFormDialog.cancel = cancel;
            programFormDialog.save = save;
            programFormDialog.getCurrentYear = getCurrentYear;
            programFormDialog.isNewOrganization = isNewOrganization;

            $log.debug("Program Form Dialog Controller loaded", programFormDialog);
        }

        /* Not a component. Run activate manually. */
        activate();
    }

    /* Inject dependencies. */
    ProgramFormDialogController.$inject = [
        "$mdDialog",
        "$q",
        "Organization",
        "ProgramArea",
        "getConfig",
        "errorHandlingService",
        "$log"
    ];

    /* Create controller. */
    module.controller(
        "ProgramFormDialogController",
        ProgramFormDialogController
    );

}(window.angular));
