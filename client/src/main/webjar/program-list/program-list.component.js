(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programList");

    /**
     * Controller for the healthBam programList component.
     * @param $q
     * @param errorHandlingService
     * @param Program
     * @param $state
     * @param $log
     * @constructor
     */
    function ProgramListController(
        $q,
        errorHandlingService,
        Program,
        $state,
        $log
    ) {
        var programList = this;

        /**
         * Handles an error loading the Program List.
         * @returns rejected promise of error input.
         */
        function handleProgramListLoadError(error) {
            $log.debug("program list load error", error);
            errorHandlingService.handleError("Failed to load program list.");
            return $q.reject(error);
        }

        /**
         * Loads the list of programs.
         */
        function load() {
            programList.loading = true;
            $log.debug("ProgramList is loading");
            programList.list = Program.query();
            programList.list.$promise.catch(
                handleProgramListLoadError
            ).finally(
                function () {
                    programList.loading = false;
                    $log.debug("ProgramList has finished loading");
                }
            );
        }

        /**
         * Navigates to the programDetails state.
         * @param program
         */
        function viewDetails(program) {
            $state.go(
                "programDetails",
                {
                    programId: program.id
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            programList.viewDetails = viewDetails;

            load();

            $log.debug("ProgramList Controller loaded", programList);
        }

        /* Run activate when component is loaded. */
        programList.$onInit = activate;
    }

    /* Inject dependencies. */
    ProgramListController.$inject = [
        "$q",
        "errorHandlingService",
        "Program",
        "$state",
        "$log"
    ];

    /* Create healthBamProgramList component. */
    module.component(
        "healthBamProgramList",
        {
            templateUrl: "/program-list.html",
            controller: ProgramListController,
            controllerAs: "programList"
        }
    );

}(window.angular));
