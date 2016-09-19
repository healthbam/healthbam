(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.createProgram module's tests. */
    describe("healthBam.createProgram module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.createProgram");
            }
        );

        describe("CreateProgramController", function () {

            var createProgram,
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

                    /* Get the controller for the createProgram component. */
                    createProgram = $componentController(
                        "healthBamCreateProgram",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(createProgram).toEqual(jasmine.any(Object));
            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
