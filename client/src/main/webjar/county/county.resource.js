(function (angular) {
    "use strict";

    var module = angular.module("healthBam.county");

    /**
     * Resource for counties.
     * @param $resource service.
     * @returns new $resource instance for counties.
     * @constructor
     */
    function County(
        $resource
    ) {
        return $resource(
            "api/counties/:id",
            {
                id: "@id"
            }
        );
    }

    County.$inject = [
        "$resource"
    ];

    module.factory(
        "County",
        County
    );

}(window.angular));
