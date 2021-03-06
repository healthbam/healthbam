(function (angular) {
    "use strict";

    var module = angular.module("healthBam.map");

    /**
     * Registers the map view's URL, template, and controller.
     * @param $stateProvider
     */
    function registerMapRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "map",
            {
                url: "/map?{time:int}",
                component: "healthBamMap",
                parent: "main",
                params: {
                    mapQuery: null
                }
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerMapRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerMapRoute);

}(window.angular));
