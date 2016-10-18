(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationList");

    /**
     * Controller for the healthBam organizationList component.
     * @param $q
     * @param errorHandlingService
     * @param Organization
     * @param $log
     * @constructor
     */
    function OrganizationListController(
        $q,
        errorHandlingService,
        Organization,
        $log
    ) {
        var organizationList = this;

        /**
         * Handles an error loading the Organization List.
         * @returns rejected promise of error input.
         */
        function handleOrganizationListLoadError(error) {
            $log.debug("organization list load error", error);
            errorHandlingService.handleError("Failed to load organization list.");
            return $q.reject(error);
        }

        /**
         * Loads the list of organizations.
         */
        function load() {
            organizationList.loading = true;
            $log.debug("OrganizationList is loading");
            organizationList.list = Organization.query();
            organizationList.list.$promise.catch(handleOrganizationListLoadError).finally(
                function () {
                    organizationList.loading = false;
                    $log.debug("OrganizationList has finished loading");
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            load();

            $log.debug("OrganizationList Controller loaded", organizationList);
        }

        /* Run activate when component is loaded. */
        organizationList.$onInit = activate;
    }

    /* Inject dependencies. */
    OrganizationListController.$inject = [
        "$q",
        "errorHandlingService",
        "Organization",
        "$log"
    ];

    /* Create healthBamOrganizationList component. */
    module.component(
        "healthBamOrganizationList",
        {
            templateUrl: "organization-list.html",
            controller: OrganizationListController,
            controllerAs: "organizationList"
        }
    );

}(window.angular));
