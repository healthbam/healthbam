(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.organizationForm module's tests. */
    describe("healthBam.organizationForm module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationForm");
            }
        );

        describe("OrganizationFormController", function () {

            var organizationForm,
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

                    /* Get the controller for the organizationForm component. */
                    organizationForm = $componentController(
                        "healthBamOrganizationForm",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(organizationForm).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    organizationForm.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        organizationForm
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
