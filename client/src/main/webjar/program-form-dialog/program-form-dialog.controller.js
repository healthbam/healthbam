(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programFormDialog");

    /**
     * Controller for the program-form dialog.
     * @param $mdDialog
     * @param $log
     * @constructor
     */
    function ProgramFormDialogController(
        $mdDialog,
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
         * Saves form and closes the dialog.
         * @returns promise.
         */
        function save() {
            programFormDialog.program.programAreas = programFormDialog.programAreas.filter(
                isProgramAreaSelected
            );

            if (!programFormDialog.otherProgramArea) {
                /* If "Other" not checked, don't save explanation field. */
                delete programFormDialog.program.otherProgramAreaExplanation;
            }

            /*
             * TODO: call programFormDialog.program.$save()
             * hide on success promise, show error toast on fail promise.
             */
            return $mdDialog.hide(programFormDialog.program);
        }

        /**
         * Returns the current year.
         * @returns {number} the current year.
         */
        function getCurrentYear() {
            return new Date().getFullYear();
        }

        /**
         * Returns the list of all program areas.
         * @returns {Array} of program areas.
         */
        function getProgramAreas() {

            return [
                {
                    id: 1,
                    name: "Infant Mortality",
                    value: false
                },
                {
                    id: 2,
                    name: "Low Birthweight",
                    value: false
                },
                {
                    id: 3,
                    name: "Prematurity",
                    value: false
                },
                {
                    id: 4,
                    name: "Smoking Cessation",
                    value: false
                },
                {
                    id: 5,
                    name: "Maternal Mortality",
                    value: false
                },
                {
                    id: 6,
                    name: "Prenatal Care Access",
                    value: false
                },
                {
                    id: 7,
                    name: "Prenatal Education",
                    value: false
                },
                {
                    id: 8,
                    name: "Breastfeeding Initiation",
                    value: false
                },
                {
                    id: 9,
                    name: "Breastfeeding Duration",
                    value: false
                },
                {
                    id: 10,
                    name: "Obesity",
                    value: false
                },
                {
                    id: 11,
                    name: "Substance Abuse Prevention",
                    value: false
                },
                {
                    id: 12,
                    name: "Neonatal Care",
                    value: false
                },
                {
                    id: 13,
                    name: "Postpartum Visits",
                    value: false
                },
                {
                    id: 14,
                    name: "Child Safety",
                    value: false
                },
                {
                    id: 15,
                    name: "Pediatric Care Access",
                    value: false
                },
                {
                    id: 16,
                    name: "Perinatal Mood and Anxiety Disorders (Mental Health)",
                    value: false
                }
            ];
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            programFormDialog.otherProgramArea = false;

            programFormDialog.cancel = cancel;
            programFormDialog.save = save;
            programFormDialog.getCurrentYear = getCurrentYear;
            programFormDialog.programAreas = getProgramAreas();

            $log.debug("Program Form Dialog Controller loaded", programFormDialog);
        }

        /* Not a component. Run activate manually. */
        activate();
    }

    /* Inject dependencies. */
    ProgramFormDialogController.$inject = [
        "$mdDialog",
        "$log"
    ];

    /* Create controller. */
    module.controller(
        "ProgramFormDialogController",
        ProgramFormDialogController
    );

}(window.angular));
