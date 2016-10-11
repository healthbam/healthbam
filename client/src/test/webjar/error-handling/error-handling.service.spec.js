(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.errorHandling module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.errorHandling");
            }
        );

        describe("errorHandlingService", function () {

            var errorHandlingService,
                $mdToast;

            beforeEach(
                function () {

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            errorHandlingService = $injector.get("errorHandlingService");
                            $mdToast = $injector.get("$mdToast");
                        }
                    );
                }
            );

            describe("errorHandlingService.handleError", function () {

                it("should exist", function () {
                    expect(errorHandlingService.handleError).toEqual(jasmine.any(Function));
                });

                it("should show toast", function () {
                    spyOn($mdToast, "show");
                    errorHandlingService.handleError("Test");
                    expect($mdToast.show).toHaveBeenCalled();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
