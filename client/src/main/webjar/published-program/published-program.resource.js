(function (angular) {
    "use strict";

    var module = angular.module("healthBam.publishedProgram");

    /**
     * Resource for programs that are publicly visible.
     * @param $resource service.
     * @returns new $resource instance for published programs.
     * @constructor
     */
    function PublishedProgram(
        $resource
    ) {
        return $resource(
            "api/published-programs/:id",
            {
                id: "@id"
            }
        );
    }

    PublishedProgram.$inject = [
        "$resource"
    ];

    module.factory(
        "PublishedProgram",
        PublishedProgram
    );

}(window.angular));
