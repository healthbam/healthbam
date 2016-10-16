(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.mapConfig module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.mapConfig");
            }
        );

        describe("programFormDialogService", function () {

            var mapConfig;

            beforeEach(
                function () {
                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            mapConfig = $injector.get("mapConfig");
                        }
                    );
                }
            );

            it("should be an Object", function () {
                expect(mapConfig).toEqual(jasmine.any(Object));
            });

            it("should contain a styles array", function () {
                expect(mapConfig.styles).toEqual(jasmine.any(Array));
            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
