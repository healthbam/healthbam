(function (angular) {
    "use strict";

    var module = angular.module("healthBam.programArea");

    /**
     * Resource for program-areas.
     * @param $resource service.
     * @returns new $resource instance for program-areas.
     * @constructor
     */
    function ProgramArea(
        $resource
    ) {
        return $resource(
            "api/program-areas/:id",
            {
                id: "@id"
            }
        );
    }

    ProgramArea.$inject = [
        "$resource"
    ];

    module.factory(
        "ProgramArea",
        ProgramArea
    );

}(window.angular));
