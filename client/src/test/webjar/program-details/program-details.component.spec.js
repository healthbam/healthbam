(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.programDetails module's tests. */
    describe("healthBam.programDetails module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programDetails");
            }
        );

        describe("ProgramDetailsController", function () {

            var programDetails,
                $componentController,
                locals,
                bindings,
                $httpBackend,
                mapQueryEndpoint,
                mapQuery,
                program,
                programId;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            $httpBackend = $injector.get("$httpBackend");
                            locals.MapQuery = $injector.get("MapQuery");
                            locals.Program = $injector.get("Program");
                            locals.$stateParams = $injector.get("$stateParams");
                            locals.$q = $injector.get("$q");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.mapConfig = $injector.get("mapConfig");
                            locals.$log = $injector.get("$log");
                        }
                    );

                    mapQueryEndpoint = "api/map-queries";

                    programId = 1234;
                    locals.$stateParams.programId = programId;

                    program = {
                        id: programId
                    };

                    mapQuery = new locals.MapQuery(
                        {
                            search: {
                                program: {
                                    id: programId
                                }
                            },
                            result: {
                                mapLayerUrl: "foo/bar",
                                programs: [
                                    program
                                ]
                            }
                        }
                    );

                    spyOn(locals.$log, "debug");
                    spyOn(locals.errorHandlingService, "handleError");
                }
            );

            /**
             * Creates controller and mocks request responses.
             */
            function initController() {

                $httpBackend.expectPOST(
                    mapQueryEndpoint
                ).respond(
                    angular.toJson(mapQuery)
                );

                /* Get the controller for the programDetails component. */
                programDetails = $componentController(
                    "healthBamProgramDetails",
                    locals,
                    bindings
                );

                programDetails.$onInit();

                expect(programDetails.loading).toEqual(true);

                $httpBackend.flush();

                expect(programDetails.loading).toEqual(false);
            }

            it("should exist", function () {
                initController();
                expect(programDetails).toEqual(jasmine.any(Object));
            });

            describe("$onInit success", function () {

                beforeEach(initController);

                it("should expose mapQuery", function () {
                    expect(programDetails.mapQuery).toEqual(jasmine.objectContaining(mapQuery));
                });

                it("should expose program", function () {
                    expect(programDetails.program).toEqual(new locals.Program(program));
                });

                it("should expose mapStyles", function () {
                    expect(programDetails.mapStyles).toEqual(locals.mapConfig.styles);
                });

                it("should default countiesHidden to true", function () {
                    expect(programDetails.countiesHidden).toEqual(true);
                });

                it("should log loading debug message", function () {
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        programDetails
                    );
                });

            });

            describe("$onInit failure", function () {

                it("should handle MapQuery error response", function () {

                    $httpBackend.expectPOST(
                        mapQueryEndpoint
                    ).respond(
                        418
                    );

                    /* Get the controller for the programDetails component. */
                    programDetails = $componentController(
                        "healthBamProgramDetails",
                        locals,
                        bindings
                    );

                    programDetails.$onInit();

                    expect(programDetails.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(programDetails.loading).toEqual(false);

                    expect(locals.errorHandlingService.handleError).toHaveBeenCalledWith(jasmine.any(String));
                });

                it("should handle MapQuery response with no programs", function () {

                    mapQuery.result.programs = [];

                    $httpBackend.expectPOST(
                        mapQueryEndpoint
                    ).respond(
                        angular.toJson(mapQuery)
                    );

                    /* Get the controller for the programDetails component. */
                    programDetails = $componentController(
                        "healthBamProgramDetails",
                        locals,
                        bindings
                    );

                    programDetails.$onInit();

                    expect(programDetails.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(programDetails.loading).toEqual(false);

                    expect(locals.errorHandlingService.handleError).toHaveBeenCalledWith(jasmine.any(String));
                });

                it("should handle MapQuery response with multiple programs", function () {

                    mapQuery.result.programs.push(
                        {
                            id: 99999
                        }
                    );

                    $httpBackend.expectPOST(
                        mapQueryEndpoint
                    ).respond(
                        angular.toJson(mapQuery)
                    );

                    /* Get the controller for the programDetails component. */
                    programDetails = $componentController(
                        "healthBamProgramDetails",
                        locals,
                        bindings
                    );

                    programDetails.$onInit();

                    expect(programDetails.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(programDetails.loading).toEqual(false);

                    expect(locals.errorHandlingService.handleError).toHaveBeenCalledWith(jasmine.any(String));
                });

            });

            describe("toggleCounties", function () {

                beforeEach(initController);

                it("should be exposed", function () {
                    expect(programDetails.toggleCounties).toEqual(jasmine.any(Function));
                });

                it("should toggle countiesHidden", function () {
                    expect(programDetails.countiesHidden).toEqual(true);
                    programDetails.toggleCounties();
                    expect(programDetails.countiesHidden).toEqual(false);
                    programDetails.toggleCounties();
                    expect(programDetails.countiesHidden).toEqual(true);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
