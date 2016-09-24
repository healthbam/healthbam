(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.sidenav module's tests. */
    describe("healthBam.sidenav module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.sidenav");
            }
        );

        describe("SidenavController", function () {

            var sidenav,
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

                    locals.$mdMedia = jasmine.createSpy(
                        "$mdMedia"
                    );

                    /* Get the controller for the sidenav component. */
                    sidenav = $componentController(
                        "healthBamSidenav",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(sidenav).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    sidenav.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        sidenav
                    );
                });

            });

            describe("sidenav.isLockedOpen", function () {

                beforeEach(
                    function () {
                        sidenav.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(sidenav.isLockedOpen).toEqual(jasmine.any(Function));
                });

                it("should return true for screens larger than small", function () {

                    var actual,
                        expected = true;

                    locals.$mdMedia.and.returnValue(expected);
                    actual = sidenav.isLockedOpen();
                    expect(actual).toEqual(expected);
                    expect(locals.$mdMedia).toHaveBeenCalledWith(
                        "gt-sm"
                    );
                });

                it("should return false for screens small or smaller", function () {

                    var actual,
                        expected = false;

                    locals.$mdMedia.and.returnValue(expected);
                    actual = sidenav.isLockedOpen();
                    expect(actual).toEqual(expected);
                    expect(locals.$mdMedia).toHaveBeenCalledWith(
                        "gt-sm"
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
