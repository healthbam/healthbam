(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userManagement");

    /**
     * Factory for creating the userManagementService.
     */
    function userManagementServiceFactory(
        $log,
        $mdDialog,
        $q,
        $state,
        errorHandlingService
    ) {

        /**
         * Reloads the current state to reflect user changes.
         * @returns {Promise<State>}.
         */
        function reloadState() {
            return $state.reload();
        }

        /**
         * Return the role that this user holds.
         * @param user - User instance to inspect.
         * @returns {string} name of the role the user holds.
         */
        function getRole(user) {

            var role;

            if (user.superAdmin) {
                role = "Super admin";

            } else if (user.admin) {
                role = "Admin";

            } else {
                role = "General user";
            }

            return role;
        }

        /**
         * Handles an error granting the user admin access.
         * @param error that occurred.
         * @returns rejected promise of error input.
         */
        function handleGrantAdminError(error) {
            $log.debug("grant admin to user error", error);
            errorHandlingService.handleError("Failed to grant admin to user.");
            return $q.reject(error);
        }

        /**
         * Handles an error revoking admin access from a user.
         * @param error that occurred.
         * @returns rejected promise of error input.
         */
        function handleRevokeAdminError(error) {
            $log.debug("revoke admin from user error", error);
            errorHandlingService.handleError("Failed to revoke admin from user.");
            return $q.reject(error);
        }

        /**
         * Handles an error deleting a user.
         * @param error that occurred.
         * @returns rejected promise of error input.
         */
        function handleDeleteUserError(error) {
            $log.debug("delete user error", error);
            errorHandlingService.handleError("Failed to delete user.");
            return $q.reject(error);
        }

        /**
         * Grants admin role to the provided user.
         * @param user to whom to grant admin.
         */
        function doGrantAdmin(user) {
            $log.debug("granting admin to user", user);
            user.admin = true;
            return user.$save().catch(
                handleGrantAdminError
            ).finally(
                reloadState
            );
        }

        /**
         * Revokes admin role from the provided user.
         * @param user from whom to revoke admin.
         */
        function doRevokeAdmin(user) {
            $log.debug("revoking admin from user", user);
            user.admin = false;
            return user.$save().catch(
                handleRevokeAdminError
            ).finally(
                reloadState
            );
        }

        /**
         * Deletes the provided user.
         * @param user to delete.
         */
        function doDeleteUser(user) {
            $log.debug("deleting user", user);
            return user.$delete().catch(
                handleDeleteUserError
            ).finally(
                reloadState
            );
        }

        /**
         * Grants admin role to the provided user after confirmation.
         * @param user to whom to grant admin.
         * @param event that triggered the action (for animation).
         */
        function grantAdmin(
            user,
            event
        ) {

            var dialogOptions = $mdDialog.confirm().title(
                "Grant admin?"
            ).textContent(
                "This will grant the admin role to '" + user.email + "'."
            ).ariaLabel(
                "Grant admin confirmation"
            ).targetEvent(
                event
            ).ok(
                "Grant admin"
            ).cancel(
                "Cancel"
            );

            return $mdDialog.show(
                dialogOptions
            ).then(
                function () {
                    return doGrantAdmin(user);
                }
            );

        }

        /**
         * Revokes admin role from the provided user after confirmation.
         * @param user from whom to revoke admin.
         * @param event that triggered the action (for animation).
         */
        function revokeAdmin(
            user,
            event
        ) {

            var dialogOptions = $mdDialog.confirm().title(
                "Revoke admin?"
            ).textContent(
                "This will revoke the admin role from '" + user.email + "'."
            ).ariaLabel(
                "Revoke admin confirmation"
            ).targetEvent(
                event
            ).ok(
                "Revoke admin"
            ).cancel(
                "Cancel"
            );

            return $mdDialog.show(
                dialogOptions
            ).then(
                function () {
                    return doRevokeAdmin(user);
                }
            );
        }

        /**
         * Deletes the provided user after confirmation.
         * @param user to delete.
         * @param event that triggered the action (for animation).
         */
        function deleteUser(
            user,
            event
        ) {

            var dialogOptions = $mdDialog.confirm().title(
                "Delete user?"
            ).textContent(
                "This will permanently delete '" + user.email + "'."
            ).ariaLabel(
                "Delete user confirmation"
            ).targetEvent(
                event
            ).ok(
                "Delete user"
            ).cancel(
                "Cancel"
            );

            return $mdDialog.show(
                dialogOptions
            ).then(
                function () {
                    return doDeleteUser(user);
                }
            );
        }

        /* Return service instance. */
        return {
            getRole: getRole,
            grantAdmin: grantAdmin,
            revokeAdmin: revokeAdmin,
            deleteUser: deleteUser
        };
    }

    /* Inject dependencies. */
    userManagementServiceFactory.$inject = [
        "$log",
        "$mdDialog",
        "$q",
        "$state",
        "errorHandlingService"
    ];

    /* Register service. */
    module.factory(
        "userManagementService",
        userManagementServiceFactory
    );

}(window.angular));
