(function (angular) {
    "use strict";

    var module = angular.module("healthBam.program");

    /**
     * Resource for programs.
     * @param $resource service.
     * @returns new $resource instance for programs.
     * @constructor
     */
    function Program(
        $resource
    ) {
        return $resource(
            "api/programs/:id",
            {
                id: "@id"
            }
        );
    }

    Program.$inject = [
        "$resource"
    ];

    module.factory(
        "Program",
        Program
    );

}(window.angular));
