(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationList");

    /**
     * Registers the organization-list page URL, template, and controller.
     * @param $stateProvider
     */
    function registerOrganizationListRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "organizationList",
            {
                url: "/organization-list",
                component: "healthBamOrganizationList",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerOrganizationListRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerOrganizationListRoute);

}(window.angular));
