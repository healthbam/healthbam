(function (angular) {
    "use strict";

    var module = angular.module("healthBam.map");

    /**
     * Controller for the healthBamMap component.
     * @param $log
     * @constructor
     */
    function MapController(
        $log
    ) {
        var map = this;

        /**
         * Initializes the controller.
         */
        function activate() {
            $log.debug("Map Controller loaded", map);
        }

        /* Run activate when component is loaded. */
        map.$onInit = activate;
    }

    /* Inject dependencies. */
    MapController.$inject = [
        "$log"
    ];

    /* Create healthBamMap component. */
    module.component(
        "healthBamMap",
        {
            templateUrl: "map.html",
            controller: MapController,
            controllerAs: "map"
        }
    );

}(window.angular));
