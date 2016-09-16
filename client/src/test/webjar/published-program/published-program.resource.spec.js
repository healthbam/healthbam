(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.publishedProgram module's tests. */
    describe("healthBam.publishedProgram module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.publishedProgram");
            }
        );

        describe("PublishedProgram $resource", function () {

            var PublishedProgram;

            beforeEach(
                function () {
                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            PublishedProgram = $injector.get("PublishedProgram");
                        }
                    );
                }
            );

            /* TODO TEST! */
            xit("should be tested", function () {

                var publishedProgram = new PublishedProgram();

                expect(publishedProgram.tested).toEqual(true);
            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
