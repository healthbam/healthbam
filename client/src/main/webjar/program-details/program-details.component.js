(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programDetails");

    /**
     * Controller for the healthBamProgramDetails component.
     * @param MapQuery
     * @param Program
     * @param $state
     * @param $stateParams
     * @param $q
     * @param programFormDialogService
     * @param errorHandlingService
     * @param $mdToast
     * @param mapConfig
     * @param $mdDialog
     * @param $log
     * @constructor
     */
    function ProgramDetailsController(
        MapQuery,
        Program,
        $state,
        $stateParams,
        $q,
        programFormDialogService,
        errorHandlingService,
        $mdToast,
        mapConfig,
        $mdDialog,
        $log
    ) {
        var programDetails = this;

        /**
         * Reloads the current state.
         * @param input
         * @returns {*} input for promise chaining.
         */
        function reloadState(input) {
            $state.reload();
            return input;
        }

        /**
         * Navigates to the map view.
         */
        function goToMap() {
            $state.go(
                "map"
            );
        }

        /**
         * Handles an successful deletion the program.
         * @returns rejected promise of error input.
         */
        function handleDeleteSuccess() {

            var toast;

            toast = $mdToast.simple().textContent(
                "Program deleted."
            ).position(
                "top right"
            );

            $mdToast.show(
                toast
            );

            goToMap();
        }

        /**
         * Handles an error deleting the program.
         * @returns rejected promise of error input.
         */
        function handleDeleteFailure(error) {
            $log.debug("delete program error", error);
            errorHandlingService.handleError("Failed to delete program.");
            return $q.reject(error);
        }

        /**
         * Actually deletes the program.
         * @param input
         * @returns {*} input to maintain promise chain.
         */
        function doDelete(input) {
            programDetails.program.$delete().then(
                handleDeleteSuccess
            ).catch(
                handleDeleteFailure
            );
            return input;
        }

        /**
         * Return the program's name.
         * @returns {String}
         */
        function getProgramName() {
            if (programDetails.program && programDetails.program.name) {
                return programDetails.program.name;
            }
            return "the program";
        }

        /**
         * Prompts for confirmation and deletes the Program.
         * @param $event - click event that triggered the operation.
         */
        function deleteProgram($event) {

            var dialogOptions = $mdDialog.confirm().title(
                "Delete program?"
            ).textContent(
                "This will permanently delete '" + getProgramName() + "'."
            ).ariaLabel(
                "Delete program confirmation"
            ).targetEvent(
                $event
            ).ok(
                "Delete program"
            ).cancel(
                "Cancel"
            );

            $mdDialog.show(
                dialogOptions
            ).then(
                doDelete
            );
        }

        /**
         * Opens a dialog for editing the Program.
         * @param $event
         * @returns {promise} resolved when dialog is closed.
         */
        function edit($event) {
            return programFormDialogService.edit(
                $event,
                programDetails.program
            ).then(
                reloadState
            );
        }

        /**
         * Handles success loading the MapQuery for the program.
         * @param mapQuery response.
         * @returns mapQuery input or rejected promise on error.
         */
        function handleMapQuerySearchSuccess(mapQuery) {

            if (mapQuery.result && angular.isArray(mapQuery.result.programs) && mapQuery.result.programs.length === 1) {
                programDetails.program = new Program(
                    angular.copy(mapQuery.result.programs[0])
                );

                return mapQuery;
            }

            return $q.reject("MapQuery for program invalid");
        }

        /**
         * Handles an error loading the MapQuery for the program.
         * @returns rejected promise of error input.
         */
        function handleMapQuerySearchError(error) {
            $log.debug("mapQuery for program load error", error);
            errorHandlingService.handleError("Failed to load program.");
            return $q.reject(error);
        }

        /**
         * Toggles the visibility of the served counties list.
         */
        function toggleCounties() {
            programDetails.countiesHidden = !programDetails.countiesHidden;
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            var mapQueryPromise;

            programDetails.countiesHidden = true;
            programDetails.toggleCounties = toggleCounties;
            programDetails.edit = edit;
            programDetails.deleteProgram = deleteProgram;

            programDetails.mapStyles = mapConfig.styles;

            /* Build MapQuery to send to server. */
            programDetails.mapQuery = new MapQuery(
                {
                    search: {
                        program: {
                            id: $stateParams.programId
                        }
                    }
                }
            );

            /* Load program and map URL from server. */
            programDetails.loading = true;
            mapQueryPromise = programDetails.mapQuery.$save();

            /* Handle successful or failure server response. */
            mapQueryPromise.then(
                handleMapQuerySearchSuccess
            ).catch(
                handleMapQuerySearchError
            ).finally(
                function () {
                    programDetails.loading = false;
                    $log.debug("Program Form Dialog done loading from server");
                }
            );

            $log.debug("ProgramDetails Controller loaded", programDetails);
        }

        /* Run activate when component is loaded. */
        programDetails.$onInit = activate;
    }

    /* Inject dependencies. */
    ProgramDetailsController.$inject = [
        "MapQuery",
        "Program",
        "$state",
        "$stateParams",
        "$q",
        "programFormDialogService",
        "errorHandlingService",
        "$mdToast",
        "mapConfig",
        "$mdDialog",
        "$log"
    ];

    /* Create healthBamProgramDetails component. */
    module.component(
        "healthBamProgramDetails",
        {
            templateUrl: "program-details.html",
            controller: ProgramDetailsController,
            controllerAs: "programDetails"
        }
    );

}(window.angular));
