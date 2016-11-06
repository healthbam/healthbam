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
                programId,
                event,
                $rootScope,
                programsEndpoint;

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
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.MapQuery = $injector.get("MapQuery");
                            locals.Program = $injector.get("Program");
                            locals.$stateParams = $injector.get("$stateParams");
                            locals.$state = $injector.get("$state");
                            locals.$q = $injector.get("$q");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$mdDialog = $injector.get("$mdDialog");
                            locals.$mdToast = $injector.get("$mdToast");
                            locals.mapConfig = $injector.get("mapConfig");
                            locals.$log = $injector.get("$log");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );

                    programsEndpoint = "api/programs";

                    event = {
                        mock: "event"
                    };

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

                    spyOn(locals.$mdDialog, "show");
                    spyOn(locals.$mdToast, "show");
                    spyOn(locals.$log, "debug");
                    spyOn(locals.errorHandlingService, "handleError");
                    spyOn(locals.$state, "go");
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

            describe("showCountiesServed", function () {

                beforeEach(initController);

                it("should be exposed", function () {
                    expect(programDetails.showCountiesServed).toEqual(jasmine.any(Function));
                });

                it("should return true if program serves any counties and servesAllCounties is false", function () {
                    programDetails.program.servesAllCounties = false;
                    programDetails.program.countiesServed = [
                        {
                            mock: "county"
                        }
                    ];
                    expect(programDetails.showCountiesServed()).toEqual(true);
                });

                it("should return false if program serves any counties and servesAllCounties is true", function () {
                    programDetails.program.servesAllCounties = true;
                    programDetails.program.countiesServed = [
                        {
                            mock: "county"
                        }
                    ];
                    expect(programDetails.showCountiesServed()).toEqual(false);
                });

                it("should return false if program serves no counties and servesAllCounties is false", function () {
                    programDetails.program.servesAllCounties = false;
                    programDetails.program.countiesServed = [];
                    expect(programDetails.showCountiesServed()).toEqual(false);
                });

                it("should return false if program serves no counties and servesAllCounties is true", function () {
                    programDetails.program.servesAllCounties = true;
                    programDetails.program.countiesServed = [];
                    expect(programDetails.showCountiesServed()).toEqual(false);
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

            describe("deleteProgram", function () {

                beforeEach(initController);

                it("should be exposed", function () {
                    expect(programDetails.deleteProgram).toEqual(jasmine.any(Function));
                });

                it("should show confirm dialog and not delete on cancel", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.reject("canceled")
                    );

                    /* Run function to test. */
                    programDetails.deleteProgram(event);

                    /* Execute async code. */
                    $rootScope.$digest();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.$mdToast.show).not.toHaveBeenCalled();
                    expect(locals.$state.go).not.toHaveBeenCalled();
                });

                it("should show confirm dialog, delete, and go to map on confirm", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.resolve("confirmed")
                    );

                    $httpBackend.expectDELETE(
                        programsEndpoint + "/" + programId
                    ).respond(
                        angular.toJson(program)
                    );

                    /* Run function to test. */
                    programDetails.deleteProgram(event);

                    $httpBackend.flush();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.$mdToast.show).toHaveBeenCalled();
                    expect(locals.$state.go).toHaveBeenCalledWith("map");
                });

                it("should show confirm dialog, delete, and go to map on confirm but error", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.resolve("confirmed")
                    );

                    $httpBackend.expectDELETE(
                        programsEndpoint + "/" + programId
                    ).respond(
                        418
                    );

                    /* Run function to test. */
                    programDetails.deleteProgram(event);

                    $httpBackend.flush();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.errorHandlingService.handleError).toHaveBeenCalled();
                    expect(locals.$state.go).not.toHaveBeenCalled();
                });

            });

            describe("isAdmin", function () {

                beforeEach(initController);

                it("should alias authenticationService.isAdmin", function () {
                    expect(programDetails.isAdmin).toEqual(locals.authenticationService.isAdmin);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
