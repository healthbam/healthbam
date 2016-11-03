(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.programCard module's tests. */
    describe("healthBam.programCard module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programCard");
            }
        );

        describe("ProgramCardController", function () {

            var programCard,
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

                    bindings.program = {
                        mock: "org"
                    };

                    /* Get the controller for the programCard component. */
                    programCard = $componentController(
                        "healthBamProgramCard",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(programCard).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    programCard.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        programCard
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
