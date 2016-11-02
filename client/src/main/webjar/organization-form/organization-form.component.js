(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationForm");

    /**
     * Controller for the healthBamOrganizationForm component.
     *
     * @param $log
     * @constructor
     */
    function OrganizationFormController(
        $log
    ) {
        var organizationForm = this;

        /**
         * Initializes the controller.
         */
        function activate() {
            $log.debug("OrganizationForm Controller loaded", organizationForm);
        }

        /* Run activate when component is loaded. */
        organizationForm.$onInit = activate;
    }

    /* Inject dependencies. */
    OrganizationFormController.$inject = [
        "$log"
    ];

    /* Create healthBamOrganizationForm component. */
    module.component(
        "healthBamOrganizationForm",
        {
            templateUrl: "organization-form.html",
            controller: OrganizationFormController,
            controllerAs: "organizationForm",
            bindings: {
                organization: "<"
            }
        }
    );

}(window.angular));
