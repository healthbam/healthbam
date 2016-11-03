(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programCard");

    /**
     * Controller for the healthBamProgramCard component.
     *
     * @param $log
     * @constructor
     */
    function ProgramCardController(
        $log
    ) {
        var programCard = this;

        /**
         * Initializes the controller.
         */
        function activate() {
            $log.debug("ProgramCard Controller loaded", programCard);
        }

        /* Run activate when component is loaded. */
        programCard.$onInit = activate;
    }

    /* Inject dependencies. */
    ProgramCardController.$inject = [
        "$log"
    ];

    /* Create healthBamProgramCard component. */
    module.component(
        "healthBamProgramCard",
        {
            templateUrl: "program-card.html",
            controller: ProgramCardController,
            controllerAs: "programCard",
            bindings: {
                program: "<"
            }
        }
    );

}(window.angular));
