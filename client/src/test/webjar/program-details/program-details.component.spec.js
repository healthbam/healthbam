(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.programDetails module's tests. */
    describe("healthBam.programDetails module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programDetails");
            }
        );

        describe("ProgramDetailsController", function () {

            var programDetails,
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

                    /* Get the controller for the programDetails component. */
                    programDetails = $componentController(
                        "healthBamProgramDetails",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(programDetails).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    programDetails.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        programDetails
                    );
                });

                it("should expose program", function () {
                    programDetails.$onInit();
                    expect(programDetails.program).toEqual(jasmine.any(Object));
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
