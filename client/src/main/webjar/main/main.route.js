(function (angular) {
    "use strict";

    var module = angular.module("healthBam.main");

    /**
     * Registers the main view's URL, template, and controller.
     * @param $stateProvider
     */
    function registerMainRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "main",
            {
                abstract: true,
                url: "/views",
                component: "healthBamMain"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerMainRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerMainRoute);

}(window.angular));
