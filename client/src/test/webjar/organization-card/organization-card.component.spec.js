(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.organizationCard module's tests. */
    describe("healthBam.organizationCard module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationCard");
            }
        );

        describe("OrganizationCardController", function () {

            var organizationCard,
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

                    bindings.organization = {
                        mock: "org"
                    };

                    /* Get the controller for the organizationCard component. */
                    organizationCard = $componentController(
                        "healthBamOrganizationCard",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(organizationCard).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    organizationCard.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        organizationCard
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
