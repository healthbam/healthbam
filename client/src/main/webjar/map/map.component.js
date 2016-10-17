(function (angular) {
    "use strict";

    var module = angular.module("healthBam.map");

    /**
     * Controller for the healthBamMap component.
     * @param Program
     * @param MapQuery
     * @param mapConfig
     * @param $log
     * @param $stateParams
     * @constructor
     */
    function MapController(
        Program,
        MapQuery,
        mapConfig,
        $log,
        $stateParams
    ) {
        var map = this;

        /**
         * Initializes the controller.
         */
        function activate() {

            if ($stateParams.mapQuery) {
                map.mapQuery = $stateParams.mapQuery;

            } else {
                map.mapQuery = new MapQuery();
            }

            /* Run search. */
            map.mapQuery.$save();

            map.programs = Program.query();
            map.mapStyles = mapConfig.styles;

            $log.debug("Map Controller loaded", map);
        }

        /* Run activate when component is loaded. */
        map.$onInit = activate;
    }

    /* Inject dependencies. */
    MapController.$inject = [
        "Program",
        "MapQuery",
        "mapConfig",
        "$log",
        "$stateParams"
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
