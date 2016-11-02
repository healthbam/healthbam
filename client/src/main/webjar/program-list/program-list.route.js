(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programList");

    /**
     * Registers the program-list page URL, template, and controller.
     * @param $stateProvider
     */
    function registerProgramListRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "programList",
            {
                url: "/program-list",
                component: "healthBamProgramList",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerProgramListRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerProgramListRoute);

}(window.angular));
