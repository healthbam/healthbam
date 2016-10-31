(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationFormDialog");

    /**
     * Controller for the organization-form dialog.
     * @param $mdDialog
     * @param $q
     * @param Organization
     * @param errorHandlingService
     * @param $log
     * @constructor
     */
    function OrganizationFormDialogController(
        $mdDialog,
        $q,
        Organization,
        errorHandlingService,
        $log
    ) {
        var organizationFormDialog = this;

        /**
         * Cancels the dialog.
         * @returns promise.
         */
        function cancel() {
            return $mdDialog.cancel(organizationFormDialog.organization);
        }

        /**
         * Handles a successful save of the Organization.
         * @returns promise for hiding dialog.
         */
        function handleOrganizationSaveSuccess() {
            return $mdDialog.hide(organizationFormDialog.organization);
        }

        /**
         * Handles an error saving the Organization.
         * @returns rejected promise of error input.
         */
        function handleOrganizationSaveError(error) {
            $log.debug("organization save error", error);
            errorHandlingService.handleError("Failed to save organization.");
            return $q.reject(error);
        }

        /**
         * Saves form and closes the dialog.
         * @returns promise.
         */
        function save() {

            organizationFormDialog.loading = true;

            $log.debug("Organization Form Dialog saving to server");

            return organizationFormDialog.organization.$save().then(
                handleOrganizationSaveSuccess
            ).catch(
                handleOrganizationSaveError
            ).finally(
                function () {
                    organizationFormDialog.loading = false;
                    $log.debug("Organization Form Dialog done saving to server");
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            organizationFormDialog.cancel = cancel;
            organizationFormDialog.save = save;

            $log.debug("Organization Form Dialog Controller loaded", organizationFormDialog);
        }

        /* Not a component. Run activate manually. */
        activate();
    }

    /* Inject dependencies. */
    OrganizationFormDialogController.$inject = [
        "$mdDialog",
        "$q",
        "Organization",
        "errorHandlingService",
        "$log"
    ];

    /* Create controller. */
    module.controller(
        "OrganizationFormDialogController",
        OrganizationFormDialogController
    );

}(window.angular));
