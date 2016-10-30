(function (angular) {
    "use strict";

    var module = angular.module("healthBam.user");

    /**
     * Resource for users.
     * @param $resource service.
     * @returns new $resource instance for users.
     * @constructor
     */
    function User(
        $resource
    ) {
        return $resource(
            "api/users/:id",
            {
                id: "@id"
            }
        );
    }

    User.$inject = [
        "$resource"
    ];

    module.factory(
        "User",
        User
    );

}(window.angular));
