(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programDetails");

    /**
     * Controller for the healthBamProgramDetails component.
     * @param MapQuery
     * @param $stateParams
     * @param $q
     * @param errorHandlingService
     * @param mapConfig
     * @param $log
     * @constructor
     */
    function ProgramDetailsController(
        MapQuery,
        $stateParams,
        $q,
        errorHandlingService,
        mapConfig,
        $log
    ) {
        var programDetails = this;

        /**
         * Handles success loading the MapQuery for the program.
         * @param mapQuery response.
         * @returns mapQuery input or rejected promise on error.
         */
        function handleMapQuerySearchSuccess(mapQuery) {

            if (mapQuery.result && angular.isArray(mapQuery.result.programs) && mapQuery.result.programs.length === 1) {
                programDetails.program = mapQuery.result.programs[0];

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
        "$stateParams",
        "$q",
        "errorHandlingService",
        "mapConfig",
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
