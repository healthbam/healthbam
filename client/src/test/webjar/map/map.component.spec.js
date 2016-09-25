(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.map module's tests. */
    describe("healthBam.map module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.map");
            }
        );

        describe("MapController", function () {

            var map,
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

                    /* Get the controller for the map component. */
                    map = $componentController(
                        "healthBamMap",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(map).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    map.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        map
                    );
                });

                it("should expose mapStyle", function () {
                    map.$onInit();
                    expect(map.mapStyle).toEqual(jasmine.any(Array));
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
