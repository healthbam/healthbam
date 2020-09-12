(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationCard");

    /**
     * Controller for the healthBamOrganizationCard component.
     *
     * @param $log
     * @constructor
     */
    function OrganizationCardController(
        $log
    ) {
        var organizationCard = this;

        /**
         * Initializes the controller.
         */
        function activate() {
            $log.debug("OrganizationCard Controller loaded", organizationCard);
        }

        /* Run activate when component is loaded. */
        organizationCard.$onInit = activate;
    }

    /* Inject dependencies. */
    OrganizationCardController.$inject = [
        "$log"
    ];

    /* Create healthBamOrganizationCard component. */
    module.component(
        "healthBamOrganizationCard",
        {
            templateUrl: "/organization-card.html",
            controller: OrganizationCardController,
            controllerAs: "organizationCard",
            bindings: {
                organization: "<"
            }
        }
    );

}(window.angular));
