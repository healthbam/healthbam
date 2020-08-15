(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organizationFormDialog");

    /**
     * Service for creating organization-form dialog instances.
     * @param $mdDialog
     * @param $mdMedia
     * @param Organization
     * @returns organizationFormDialogService.
     */
    function organizationFormDialogServiceFactory(
        $mdDialog,
        $mdMedia,
        Organization
    ) {

        /**
         * Opens an instance of the organization-form dialog.
         * @param $event that triggered the action.
         * @param Organization to edit.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function open(
            $event,
            organization
        ) {

            var dialogOptions = {
                templateUrl: "/organization-form-dialog.html",
                controller: "OrganizationFormDialogController",
                controllerAs: "organizationFormDialog",
                targetEvent: $event,
                fullscreen: !$mdMedia("gt-sm"),
                escapeToClose: false,
                bindToController: true,
                locals: {
                    organization: organization
                }
            };

            return $mdDialog.show(dialogOptions);
        }

        /**
         * Opens an instance of the organization-form dialog to create a new organization.
         * @param $event that triggered the action.
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function create(
            $event
        ) {
            return open(
                $event,
                new Organization()
            );
        }

        /**
         * Opens an instance of the organization-form dialog to edit an existing organization.
         * @param $event that triggered the action.
         * @param Organization
         * @returns promise that will be resolved when dialog is hidden or canceled.
         */
        function edit(
            $event,
            organization
        ) {
            return open(
                $event,
                angular.copy(organization)
            );
        }

        return {
            create: create,
            edit: edit
        };
    }

    /* Inject dependencies. */
    organizationFormDialogServiceFactory.$inject = [
        "$mdDialog",
        "$mdMedia",
        "Organization"
    ];

    /* Create organizationFormDialogService. */
    module.factory(
        "organizationFormDialogService",
        organizationFormDialogServiceFactory
    );

}(window.angular));
