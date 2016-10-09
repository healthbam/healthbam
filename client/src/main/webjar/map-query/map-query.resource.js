(function (angular) {
    "use strict";

    var module = angular.module("healthBam.mapQuery");

    /**
     * Resource for map-queries.
     * @param $resource service.
     * @returns new $resource instance for map-queries.
     * @constructor
     */
    function MapQuery(
        $resource
    ) {
        return $resource(
            "api/map-queries"
        );
    }

    MapQuery.$inject = [
        "$resource"
    ];

    module.factory(
        "MapQuery",
        MapQuery
    );

}(window.angular));
