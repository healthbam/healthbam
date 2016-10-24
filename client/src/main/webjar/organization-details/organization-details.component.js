(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationDetails");

    /**
     * Controller for the healthBamOrganizationDetails component.
     * @param MapQuery
     * @param Organization
     * @param $state
     * @param $stateParams
     * @param $q
     * @param errorHandlingService
     * @param $mdToast
     * @param mapConfig
     * @param $mdDialog
     * @param $log
     * @constructor
     */
    function OrganizationDetailsController(
        Organization,
        $state,
        $stateParams,
        $q,
 //       organizationFormDialogService,
        errorHandlingService,
        $mdToast,
        mapConfig,
        $mdDialog,
        $log
    ) {
        var organizationDetails = this;

        /**
         * Reloads the current state.
         * @param input
         * @returns {*} input for promise chaining.
         */
        // function reloadState(input) {
        //     $state.reload();
        //     return input;
        // }

        /**
         * Navigates to the map view.
         */
        function goToMap() {
            $state.go(
                "map"
            );
        }

        /**
         * Handles a successful deletion the organization.
         * @returns rejected promise of error input.
         */
        function handleDeleteSuccess() {

            var toast;

            toast = $mdToast.simple().textContent(
                "Organization deleted."
            ).position(
                "top right"
            );

            $mdToast.show(
                toast
            );

            goToMap();
        }

        /**
         * Handles an error deleting the organization.
         * @returns rejected promise of error input.
         */
        function handleDeleteFailure(error) {
            $log.debug("delete organization error", error);
            errorHandlingService.handleError("Failed to delete organization.");
            return $q.reject(error);
        }

        /**
         * Actually deletes the organization.
         * @param input
         * @returns {*} input to maintain promise chain.
         */
        function doDelete(input) {
            organizationDetails.organization.$delete().then(
                handleDeleteSuccess
            ).catch(
                handleDeleteFailure
            );
            return input;
        }

        /**
         * Return the organization's name.
         * @returns {String}
         */
        function getOrganizationName() {
            if (organizationDetails.organization && organizationDetails.organization.name) {
                return organizationDetails.organization.name;
            }
            return "the organization";
        }

        /**
         * Prompts for confirmation and deletes the Organization.
         * @param $event - click event that triggered the operation.
         */
        function deleteOrganization($event) {

            var dialogOptions = $mdDialog.confirm().title(
                "Delete organization?"
            ).textContent(
                "This will permanently delete '" + getOrganizationName() + "'."
            ).ariaLabel(
                "Delete organization confirmation"
            ).targetEvent(
                $event
            ).ok(
                "Delete organization"
            ).cancel(
                "Cancel"
            );

            $mdDialog.show(
                dialogOptions
            ).then(
                doDelete
            );
        }

        /**
         * Opens a dialog for editing the Organization.
         * @param $event
         * @returns {promise} resolved when dialog is closed.
         */
        // function edit($event) {
        //     return organizationFormDialogService.edit(
        //         $event,
        //         programDetails.program
        //     ).then(
        //         reloadState
        //     );
        // }

        /**
         * Handles success loading the organization.
         */
        function handleOrgDetailsLoadSuccess() {
            organizationDetails.organization = new Organization(
                angular.copy(organizationDetails.orgInfo)
            );

            $log.debug("success");
        }

        /**
         * Handles an error loading the organization.
         * @returns rejected promise of error input.
         */
        function handleOrgDetailsLoadError(error) {
            $log.debug("organization load error", error);
            errorHandlingService.handleError("Failed to load organization.");
            return $q.reject(error);
        }

        /**
         * Initializes the controller.
         */
        function activate() {
       //     organizationDetails.edit = edit;
            organizationDetails.deleteOrganization = deleteOrganization;

     //       organizationDetails.mapStyles = mapConfig.styles;
            organizationDetails.orgInfo = Organization.get({id: $stateParams.orgId});

            /* Load organization URL from server. */
            organizationDetails.loading = true;

            /* Handle successful or failure server response. */
            organizationDetails.orgInfo.$promise.then(
                handleOrgDetailsLoadSuccess
            ).catch(
                handleOrgDetailsLoadError
            ).finally(
                function () {
                    organizationDetails.loading = false;
                    $log.debug("Organization Details done loading from server");
                }
            );

            $log.debug("OrganizationDetails Controller loaded", organizationDetails);
        }

        /* Run activate when component is loaded. */
        organizationDetails.$onInit = activate;
    }

    /* Inject dependencies. */
    OrganizationDetailsController.$inject = [
        "Organization",
        "$state",
        "$stateParams",
        "$q",
    //    "organizationFormDialogService",
        "errorHandlingService",
        "$mdToast",
        "mapConfig",
        "$mdDialog",
        "$log"
    ];

    /* Create healthBamOrganizationDetails component. */
    module.component(
        "healthBamOrganizationDetails",
        {
            templateUrl: "organization-details.html",
            controller: OrganizationDetailsController,
            controllerAs: "organizationDetails"
        }
    );

}(window.angular));
