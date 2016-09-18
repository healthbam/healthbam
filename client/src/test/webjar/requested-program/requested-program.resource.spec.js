(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.requestedProgram module's tests. */
    describe("healthBam.requestedProgram module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.requestedProgram");
            }
        );

        describe("RequestedProgram $resource", function () {

            var RequestedProgram;

            beforeEach(
                function () {
                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            RequestedProgram = $injector.get("RequestedProgram");
                        }
                    );
                }
            );

            /* TODO TEST! */
            xit("should be tested", function () {

                var requestedProgram = new RequestedProgram();

                expect(requestedProgram.tested).toEqual(true);
            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
