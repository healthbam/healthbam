(function (angular) {
    "use strict";

    var module = angular.module("healthBam.sidenav");

    /**
     * Controller for the healthBamSidenav component.
     * @param $log
     * @param $mdMedia
     * @constructor
     */
    function SidenavController(
        $log,
        $mdMedia
    ) {
        var sidenav = this;

        /**
         * Returns true if the sidenav is locked inline visible.
         * @returns {boolean} if sidenav should be stuck visible.
         */
        function isLockedOpen() {
            return $mdMedia("gt-sm");
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            sidenav.isLockedOpen = isLockedOpen;

            $log.debug("Sidenav Controller loaded", sidenav);
        }

        /* Run activate when component is loaded. */
        sidenav.$onInit = activate;
    }

    /* Inject dependencies. */
    SidenavController.$inject = [
        "$log",
        "$mdMedia"
    ];

    /* Create healthBamSidenav component. */
    module.component(
        "healthBamSidenav",
        {
            templateUrl: "sidenav.html",
            controller: SidenavController,
            controllerAs: "sidenav"
        }
    );

}(window.angular));
