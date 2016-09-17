(function (angular) {
    "use strict";

    var module = angular.module(
        "healthBam",
        [
            "ngResource"
        ]
    );

    function ExampleController(
        $log
    ) {
        var example = this;
        $log.log("it works?????????");
    }

    ExampleController.$inject = [
        "$log"
    ];

    module.controller(
        "ExampleController",
        ExampleController
    );

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

    function AllProgramsController(
        PublishedProgram,
        RequestedProgram
    ) {
        var allPrograms = this;

        function refreshProgramsAndRequests() {
            allPrograms.programs = PublishedProgram.query();
            allPrograms.programRequests = RequestedProgram.query();
        }

        function saveProgram(program) {
            program.$save();
        }

        function removeProgram(program) {
            program.$delete().then(refreshProgramsAndRequests);
        }

        function addNewProgramRequest() {
            allPrograms.programRequests.push(
                new RequestedProgram()
            );
        }

        function publishProgramRequest(request) {
            request.$publish().then(refreshProgramsAndRequests);
        }

        function saveProgramRequest(request) {
            request.$save();
        }

        function removeProgramRequest(request) {
            request.$delete().then(refreshProgramsAndRequests);
        }

        function activate() {

            allPrograms.saveProgram = saveProgram;
            allPrograms.removeProgram = removeProgram;
            allPrograms.addNewProgramRequest = addNewProgramRequest;
            allPrograms.publishProgramRequest = publishProgramRequest;
            allPrograms.saveProgramRequest = saveProgramRequest;
            allPrograms.removeProgramRequest = removeProgramRequest;
            allPrograms.refreshProgramsAndRequests = refreshProgramsAndRequests;

            refreshProgramsAndRequests();
        }

        activate();
    }

    AllProgramsController.$inject = [
        "PublishedProgram",
        "RequestedProgram"
    ];

    module.component(
        "allPrograms",
        {
            templateUrl: "all-programs.html",
            controller: AllProgramsController,
            controllerAs: "allPrograms",
            bindings: {}
        }
    );

    module.component(
        "publishedProgram",
        {
            templateUrl: "program.html",
            controllerAs: "publishedProgram",
            bindings: {
                program: "<"
            }
        }
    );

    module.component(
        "programRequest",
        {
            templateUrl: "program-request.html",
            controllerAs: "programRequest",
            bindings: {
                request: "<"
            }
        }
    );

}(angular));
