(function (angular) {
    "use strict";

    var module = angular.module("healthBam.navigationList");

    /**
     * Controller for the healthBamNavigationList component.
     *
     * @param $state
     * @param $log
     * @param authenticationService
     * @constructor
     */
    function NavigationListController(
        $state,
        $log,
        authenticationService
    ) {
        var navigationList = this;

        /**
         * Navigates to the map state.
         */
        function viewMap() {
            $state.go(
                "map"
            );
        }

        /**
         * Navigates to the organizationList state.
         */
        function viewOrganizations() {
            $state.go(
                "organizationList"
            );
        }

        /**
         * Navigates to the programList state.
         */
        function viewPrograms() {
            $state.go(
                "programList"
            );
        }

        /**
         * Navigates to the userList state.
         */
        function viewUsers() {
            $state.go(
                "userList"
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {
            navigationList.isAdmin = authenticationService.isAdmin;
            navigationList.viewMap = viewMap;
            navigationList.viewOrganizations = viewOrganizations;
            navigationList.viewPrograms = viewPrograms;
            navigationList.viewUsers = viewUsers;
            $log.debug("NavigationList Controller loaded", navigationList);
        }

        /* Run activate when component is loaded. */
        navigationList.$onInit = activate;
    }

    /* Inject dependencies. */
    NavigationListController.$inject = [
        "$state",
        "$log",
        "authenticationService"
    ];

    /* Create healthBamNavigationList component. */
    module.component(
        "healthBamNavigationList",
        {
            templateUrl: "navigation-list.html",
            controller: NavigationListController,
            controllerAs: "navigationList"
        }
    );

}(window.angular));
