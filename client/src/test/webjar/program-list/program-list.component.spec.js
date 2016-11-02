(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.programList module's tests. */
    describe("healthBam.programList module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programList");
            }
        );

        describe("ProgramListController", function () {

            var programList,
                $componentController,
                $httpBackend,
                locals;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.Program = $injector.get("Program");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$state = $injector.get("$state");
                            locals.$log = $injector.get("$log");
                            $httpBackend = $injector.get("$httpBackend");
                        }
                    );

                    /* Get the controller for the programList component. */
                    programList = $componentController(
                        "healthBamProgramList",
                        locals
                    );

                    spyOn(locals.errorHandlingService, "handleError");
                }
            );

            it("should exist", function () {
                programList.$onInit();
                expect(programList).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    programList.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        programList
                    );
                });

                it("should load & expose the list of programs from server", function () {

                    var list = [
                        new locals.Program(
                            {
                                id: 1,
                                name: "TestPrograms",
                                organization: {}
                            }
                        )
                    ];

                    $httpBackend.expectGET(
                        "api/programs"
                    ).respond(
                        list
                    );

                    programList.$onInit();

                    $httpBackend.flush();

                    expect(
                        programList.list
                    ).toEqual(
                        jasmine.objectContaining(list)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching Programs", function () {

                    $httpBackend.expectGET(
                        "api/programs"
                    ).respond(
                        418
                    );

                    programList.$onInit();

                    $httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

            });

            describe("viewDetails", function () {

                var program;

                beforeEach(
                    function () {
                        programList.$onInit();
                        spyOn(locals.$state, "go");
                        program = new locals.Program(
                            {
                                id: 12345,
                                organization: {}
                            }
                        );
                    }
                );

                it("should be exposed", function () {
                    expect(programList.viewDetails).toEqual(jasmine.any(Function));
                });

                it("should navigate to the programDetails state", function () {
                    programList.viewDetails(program);
                    expect(
                        locals.$state.go
                    ).toHaveBeenCalledWith(
                        "programDetails",
                        {
                            programId: program.id
                        }
                    );
                });

            });

        });
    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
