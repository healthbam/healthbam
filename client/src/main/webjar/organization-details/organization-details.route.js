(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationDetails");

    /**
     * Registers the organizationDetails view's URL, template, and controller.
     * @param $stateProvider
     */
    function registerOrganizationDetailsRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "organizationDetails",
            {
                url: "/organization-details/{orgId:int}",
                component: "healthBamOrganizationDetails",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerOrganizationDetailsRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerOrganizationDetailsRoute);

}(window.angular));
