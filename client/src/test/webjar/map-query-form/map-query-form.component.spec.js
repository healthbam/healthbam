(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.mapQueryForm module's tests. */
    describe("healthBam.mapQueryForm module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.mapQueryForm");
            }
        );

        describe("MapQueryFormController", function () {

            var mapQueryForm,
                $componentController,
                locals,
                bindings,
                healthBamSidenav;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.MapQuery = $injector.get("MapQuery");
                            locals.County = $injector.get("County");
                            locals.Organization = $injector.get("Organization");
                            locals.ProgramArea = $injector.get("ProgramArea");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$q = $injector.get("$q");
                            locals.$state = $injector.get("$state");
                            locals.$log = $injector.get("$log");
                            locals.$httpBackend = $injector.get("$httpBackend");
                        }
                    );

                    locals.$mdSidenav = jasmine.createSpy(
                        "$mdSidenav"
                    );

                    healthBamSidenav = jasmine.createSpyObj(
                        "healthBamSidenav",
                        [
                            "close"
                        ]
                    );

                    spyOn(locals.errorHandlingService, "handleError");

                    locals.$mdSidenav.and.returnValue(healthBamSidenav);

                    /* Get the controller for the mapQueryForm component. */
                    mapQueryForm = $componentController(
                        "healthBamMapQueryForm",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(mapQueryForm).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    mapQueryForm.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        mapQueryForm
                    );
                });

                it("should expose mapQuery", function () {
                    mapQueryForm.$onInit();
                    expect(mapQueryForm.mapQuery).toEqual(jasmine.any(Object));
                });

                it("should load & expose Counties from server", function () {

                    var counties = [
                        new locals.County(
                            {
                                id: 1,
                                name: "TestCounty"
                            }
                        )
                    ];

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        angular.toJson(counties)
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        angular.toJson([])
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        mapQueryForm.counties
                    ).toEqual(
                        jasmine.objectContaining(counties)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching Counties", function () {

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        418
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        angular.toJson([])
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

                it("should load & expose Organizations from server", function () {

                    var organizations = [
                        new locals.Organization(
                            {
                                id: 1,
                                name: "TestOrganizations"
                            }
                        )
                    ];

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        organizations
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        angular.toJson([])
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        mapQueryForm.organizations
                    ).toEqual(
                        jasmine.objectContaining(organizations)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching Organizations", function () {

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        418
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        angular.toJson([])
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

                it("should load & expose ProgramAreas from server", function () {

                    var programAreas = [
                        new locals.ProgramArea(
                            {
                                id: 1,
                                name: "TestProgramAreas"
                            }
                        )
                    ];

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        programAreas
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        mapQueryForm.programAreas
                    ).toEqual(
                        jasmine.objectContaining(programAreas)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching ProgramAreas", function () {

                    locals.$httpBackend.expectGET(
                        "api/counties"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        angular.toJson([])
                    );

                    locals.$httpBackend.expectGET(
                        "api/program-areas"
                    ).respond(
                        418
                    );

                    mapQueryForm.$onInit();

                    locals.$httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

            });

            describe("search", function () {

                beforeEach(
                    function () {
                        mapQueryForm.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(mapQueryForm.search).toEqual(jasmine.any(Function));
                });

                it("should navigate to map with mapQuery and close sideNav", function () {

                    spyOn(locals.$state, "go");

                    mapQueryForm.search();

                    expect(locals.$state.go).toHaveBeenCalledWith(
                        "map",
                        {
                            mapQuery: mapQueryForm.mapQuery,
                            time: jasmine.any(Number)
                        }
                    );

                    expect(healthBamSidenav.close).toHaveBeenCalled();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
