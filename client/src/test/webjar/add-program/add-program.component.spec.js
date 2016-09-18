(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.addProgram module's tests. */
    describe("healthBam.addProgram module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.addProgram");
            }
        );

        describe("AddProgramController", function () {

            var addProgram,
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

                    /* Get the controller for the addProgram component. */
                    addProgram = $componentController(
                        "healthBamAddProgram",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(addProgram).toEqual(jasmine.any(Object));
            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
