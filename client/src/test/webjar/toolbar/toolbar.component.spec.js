(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.toolbar module's tests. */
    describe("healthBam.toolbar module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.toolbar");
            }
        );

        describe("ToolbarController", function () {

            var toolbar,
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
                        }
                    );

                    locals.$mdSidenav = jasmine.createSpy(
                        "$mdSidenav"
                    );

                    /* Get the controller for the toolbar component. */
                    toolbar = $componentController(
                        "healthBamToolbar",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(toolbar).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    toolbar.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        toolbar
                    );
                });

            });

            describe("toggleSidenav", function () {

                beforeEach(
                    function () {
                        toolbar.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(toolbar.toggleSidenav).toEqual(jasmine.any(Function));
                });

                it("should toggle sidenav and return its promise", function () {

                    var actual,
                        expected,
                        healthBamSidenav;

                    expected = {
                        fake: "promise"
                    };

                    healthBamSidenav = jasmine.createSpyObj(
                        "healthBamSidenav",
                        [
                            "toggle"
                        ]
                    );

                    locals.$mdSidenav.and.returnValue(healthBamSidenav);

                    healthBamSidenav.toggle.and.returnValue(expected);

                    actual = toolbar.toggleSidenav();

                    expect(actual).toEqual(expected);

                    expect(locals.$mdSidenav).toHaveBeenCalledWith(
                        "healthBamSidenav"
                    );

                    expect(healthBamSidenav.toggle).toHaveBeenCalled();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
