(function (angular) {
    "use strict";

    var module = angular.module("healthBam.reportList");

    /**
     * Registers the report-list page URL, template, and controller.
     * @param $stateProvider
     */
    function registerReportListRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "reportList",
            {
                url: "/report-list",
                component: "healthBamReportList",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerReportListRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerReportListRoute);

}(window.angular));
