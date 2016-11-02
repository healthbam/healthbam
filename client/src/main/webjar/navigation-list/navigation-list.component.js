(function (angular) {
    "use strict";

    var module = angular.module("healthBam.navigationList");

    /**
     * Controller for the healthBamNavigationList component.
     *
     * @param $state
     * @param $log
     * @constructor
     */
    function NavigationListController(
        $state,
        $log
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
         * Initializes the controller.
         */
        function activate() {
            navigationList.viewMap = viewMap;
            navigationList.viewOrganizations = viewOrganizations;
            navigationList.viewPrograms = viewPrograms;
            $log.debug("NavigationList Controller loaded", navigationList);
        }

        /* Run activate when component is loaded. */
        navigationList.$onInit = activate;
    }

    /* Inject dependencies. */
    NavigationListController.$inject = [
        "$state",
        "$log"
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
