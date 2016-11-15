(function (angular) {
    "use strict";

    var module = angular.module("healthBam.userList");

    /**
     * Registers the user-list page URL, template, and controller.
     * @param $stateProvider
     */
    function registerUserListRoute(
        $stateProvider
    ) {
        $stateProvider.state(
            "userList",
            {
                url: "/user-list",
                component: "healthBamUserList",
                parent: "main"
            }
        );
    }

    /* Inject dependencies for view registration. */
    registerUserListRoute.$inject = [
        "$stateProvider"
    ];

    /* Register route to view. */
    module.config(registerUserListRoute);

}(window.angular));
