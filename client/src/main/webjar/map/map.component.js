(function (angular) {
    "use strict";

    var module = angular.module("healthBam.map");

    /**
     * Controller for the healthBamMap component.
     * @param Program
     * @param MapQuery
     * @param $log
     * @param $stateParams
     * @constructor
     */
    function MapController(
        Program,
        MapQuery,
        $log,
        $stateParams
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
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#f5f5f5"
                        }
                    ]
                },
                {
                    elementType: "labels.icon",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#616161"
                        }
                    ]
                },
                {
                    elementType: "labels.text.stroke",
                    stylers: [
                        {
                            color: "#f5f5f5"
                        }
                    ]
                },
                {
                    featureType: "administrative",
                    elementType: "geometry",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "administrative.land_parcel",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "administrative.land_parcel",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#bdbdbd"
                        }
                    ]
                },
                {
                    featureType: "administrative.neighborhood",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "poi",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "poi",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#eeeeee"
                        }
                    ]
                },
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
                    featureType: "poi",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#757575"
                        }
                    ]
                },
                {
                    featureType: "poi.park",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#e5e5e5"
                        }
                    ]
                },
                {
                    featureType: "poi.park",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#9e9e9e"
                        }
                    ]
                },
                {
                    featureType: "road",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#ffffff"
                        }
                    ]
                },
                {
                    featureType: "road",
                    elementType: "labels",
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
                    featureType: "road.arterial",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "road.arterial",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#757575"
                        }
                    ]
                },
                {
                    featureType: "road.highway",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#dadada"
                        }
                    ]
                },
                {
                    featureType: "road.highway",
                    elementType: "labels",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "road.highway",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#616161"
                        }
                    ]
                },
                {
                    featureType: "road.local",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "road.local",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#9e9e9e"
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
                },
                {
                    featureType: "transit.line",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#e5e5e5"
                        }
                    ]
                },
                {
                    featureType: "transit.station",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#eeeeee"
                        }
                    ]
                },
                {
                    featureType: "water",
                    elementType: "geometry",
                    stylers: [
                        {
                            color: "#c9c9c9"
                        }
                    ]
                },
                {
                    featureType: "water",
                    elementType: "labels.text",
                    stylers: [
                        {
                            visibility: "off"
                        }
                    ]
                },
                {
                    featureType: "water",
                    elementType: "labels.text.fill",
                    stylers: [
                        {
                            color: "#9e9e9e"
                        }
                    ]
                }
            ];
        }

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
            map.mapStyle = getMapStyle();

            $log.debug("Map Controller loaded", map);
        }

        /* Run activate when component is loaded. */
        map.$onInit = activate;
    }

    /* Inject dependencies. */
    MapController.$inject = [
        "Program",
        "MapQuery",
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
