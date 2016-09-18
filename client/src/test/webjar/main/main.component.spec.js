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

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
