(function (angular) {
    "use strict";

    var module = angular.module("healthBam.requestedProgram");

    /**
     * Resource for new programs or program changes that have not yet been published.
     * @param $resource service.
     * @returns new $resource instance for requested programs.
     * @constructor
     */
    function RequestedProgram(
        $resource
    ) {
        var basePath = "api/requested-programs/:id";

        return $resource(
            basePath,
            {
                id: "@id"
            },
            {
                publish: {
                    method: "POST",
                    url: basePath + "/publish"
                }
            }
        );
    }

    RequestedProgram.$inject = [
        "$resource"
    ];

    module.factory(
        "RequestedProgram",
        RequestedProgram
    );

}(window.angular));
