(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programDetails");

    /**
     * Controller for the healthBamProgramDetails component.
     * @param Program
     * @param $stateParams
     * @param $q
     * @param errorHandlingService
     * @param $log
     * @constructor
     */
    function ProgramDetailsController(
        Program,
        $stateParams,
        $q,
        errorHandlingService,
        $log
    ) {
        var programDetails = this;

        /**
         * Handles an error loading the Program.
         * @returns rejected promise of error input.
         */
        function handleProgramLoadError(error) {
            $log.debug("program load error", error);
            errorHandlingService.handleError("Failed to load program.");
            return $q.reject(error);
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            programDetails.program = Program.get(
                {
                    id: $stateParams.programId
                }
            );

            programDetails.program.$promise.catch(handleProgramLoadError);

            $log.debug("ProgramDetails Controller loaded", programDetails);
        }

        /* Run activate when component is loaded. */
        programDetails.$onInit = activate;
    }

    /* Inject dependencies. */
    ProgramDetailsController.$inject = [
        "Program",
        "$stateParams",
        "$q",
        "errorHandlingService",
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
