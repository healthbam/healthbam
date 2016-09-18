(function (angular) {
    "use strict";

    var module = angular.module("healthBam.addProgram");

    /**
     * Controller for the healthBamAddProgram component.
     * @param $log
     * @constructor
     */
    function AddProgramController(
        $log
    ) {
        var addProgram = this;

        $log.debug("AddProgram Controller loaded", addProgram);
    }

    /* Inject dependencies. */
    AddProgramController.$inject = [
        "$log"
    ];

    /* Create healthBamAddProgram component. */
    module.component(
        "healthBamAddProgram",
        {
            templateUrl: "add-program.html",
            controller: AddProgramController,
            controllerAs: "addProgram"
        }
    );

}(window.angular));
