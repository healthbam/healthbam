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
         * Returns the current time in milliseconds so we can bust google's caching of our generated KML files.
         * @returns {Number} current time in milliseconds.
         */
        function getMsTime() {
            return Date.now();
        }

        /**
         * Returns the program IDs that the user is interested in as a comma delimited string.
         * @returns {String} comma delimited string of the IDS of the programs to display.
         */
        function getProgramIdsString() {
            return "1,2";
        }

        /**
         * Returns the protocol, host, and port part of our single-page-app's URL.
         */
        function getUrlPrefix() {
            /* location.protocol includes the colon and location.host includes the port */
            return location.protocol + "//" + location.host + "/";
        }

        /**
         * Returns the url for google to get a generated KML file.
         * @returns {String} full URL to generate a KML file.
         */
        function getKmlUrl() {
            return getUrlPrefix() + "api/kml?bc=" + getMsTime() + "&programIds=" + getProgramIdsString();
        }

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

            map.kmlUrl = getKmlUrl();

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
