(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.navigationList module's tests. */
    describe("healthBam.navigationList module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.navigationList");
            }
        );

        describe("NavigationListController", function () {

            var navigationList,
                $componentController,
                locals,
                bindings;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.$log = $injector.get("$log");
                            locals.$state = $injector.get("$state");
                        }
                    );

                    spyOn(locals.$state, "go");

                    bindings.organization = {
                        mock: "org"
                    };

                    /* Get the controller for the navigationList component. */
                    navigationList = $componentController(
                        "healthBamNavigationList",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(navigationList).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    navigationList.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        navigationList
                    );
                });

            });

            describe("viewMap", function () {

                beforeEach(
                    function () {
                        navigationList.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(navigationList.viewMap).toEqual(jasmine.any(Function));
                });

                it("should navigate to the map state", function () {
                    navigationList.viewMap();
                    expect(
                        locals.$state.go
                    ).toHaveBeenCalledWith(
                        "map"
                    );
                });

            });

            describe("viewPrograms", function () {

                beforeEach(
                    function () {
                        navigationList.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(navigationList.viewPrograms).toEqual(jasmine.any(Function));
                });

                it("should navigate to the programList state", function () {
                    navigationList.viewPrograms();
                    expect(
                        locals.$state.go
                    ).toHaveBeenCalledWith(
                        "programList"
                    );
                });

            });

            describe("viewOrganizations", function () {

                beforeEach(
                    function () {
                        navigationList.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(navigationList.viewOrganizations).toEqual(jasmine.any(Function));
                });

                it("should navigate to the organizationList state", function () {
                    navigationList.viewOrganizations();
                    expect(
                        locals.$state.go
                    ).toHaveBeenCalledWith(
                        "organizationList"
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
