(function (angular) {
    "use strict";

    var module = angular.module("healthBam.map");

    /**
     * Controller for the healthBamMap component.
     * @param Program
     * @param $log
     * @constructor
     */
    function MapController(
        Program,
        $log
    ) {
        var map = this;

        /**
         * Gets the style of map to show.
         * Generated at https://mapstyle.withgoogle.com/.
         * @returns {Array} style definition.
         */
        function getMapStyle() {
            return [
                {
                    featureType: "poi",
                    elementType: "labels.text",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "poi.business",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "road",
                    elementType: "labels.icon",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "transit",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                }
            ];
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            map.programs = Program.query();
            map.mapStyle = getMapStyle();

            $log.debug("Map Controller loaded", map);
        }

        /* Run activate when component is loaded. */
        map.$onInit = activate;
    }

    /* Inject dependencies. */
    MapController.$inject = [
        "Program",
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
