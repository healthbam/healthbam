(function (angular) {
    "use strict";

    var module = angular.module(
        "healthBam",
        [
            "ngResource"
        ]
    );

    function User(
        $resource
    ) {
        return $resource("api/users");
    }

    User.$inject = [
        "$resource"
    ];

    module.factory(
        "User",
        User
    );

    module.component(
        "displayUser",
        {
            templateUrl: "display-user.html",
            controllerAs: "displayUser",
            bindings: {
                user: '<'
            }
        }
    );

    function ExampleController(
        $log,
        User
    ) {
        var example = this;
        $log.log("it works?????????");
        example.users = User.query();
    }

    ExampleController.$inject = [
        "$log",
        "User"
    ];

    module.controller(
        "ExampleController",
        ExampleController
    );

}(angular));
