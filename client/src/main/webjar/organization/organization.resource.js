(function (angular) {
    "use strict";

    var module = angular.module("healthBam.organization");

    /**
     * Resource for organizations.
     * @param $resource service.
     * @returns new $resource instance for organizations.
     * @constructor
     */
    function Organization(
        $resource
    ) {
        return $resource(
            "api/organizations/:id",
            {
                id: "@id"
            }
        );
    }

    Organization.$inject = [
        "$resource"
    ];

    module.factory(
        "Organization",
        Organization
    );

}(window.angular));
