(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programDetails");

    /**
     * Registers the programDetails view's URL, template, and controller.
     * @param $stateProvider
     */
    function registerProgramDetailsRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "programDetails",
            {
                url: "/program-details/{programId:int}",
                component: "healthBamProgramDetails",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerProgramDetailsRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerProgramDetailsRoute);

}(window.angular));
