(function (angular) {
    "use strict";

    var module = angular.module("healthBam.toolbar");

    /**
     * Controller for the healthBamToolbar component.
     * @param $mdSidenav
     * @param $log
     * @constructor
     */
    function ToolbarController(
        $mdSidenav,
        $log
    ) {
        var toolbar = this;

        /**
         * Opens or closes the sidenav when not locked open.
         * @returns promise from toggle action.
         */
        function toggleSidenav() {
            return $mdSidenav("healthBamSidenav").toggle();
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            toolbar.toggleSidenav = toggleSidenav;

            $log.debug("Toolbar Controller loaded", toolbar);
        }

        /* Run activate when component is loaded. */
        toolbar.$onInit = activate;
    }

    /* Inject dependencies. */
    ToolbarController.$inject = [
        "$mdSidenav",
        "$log"
    ];

    /* Create healthBamToolbar component. */
    module.component(
        "healthBamToolbar",
        {
            templateUrl: "/toolbar.html",
            controller: ToolbarController,
            controllerAs: "toolbar"
        }
    );

}(window.angular));
