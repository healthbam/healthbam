(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.main module's tests. */
    describe("healthBam.main module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.main");
            }
        );

        describe("MainController", function () {

            var main,
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

                    /* Get the controller for the main component. */
                    main = $componentController(
                        "healthBamMain",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(main).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    main.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        main
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
