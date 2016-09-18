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

        $log.debug("Map Controller loaded", map);
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
