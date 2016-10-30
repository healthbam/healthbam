(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.authentication module's tests. */
    describe("healthBam.authentication module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.authentication");
            }
        );

        describe("AuthenticationController", function () {

            var auth,
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
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.$log = $injector.get("$log");
                        }
                    );

                    /* Get the controller for the authentication component. */
                    auth = $componentController(
                        "healthBamAuthentication",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(auth).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    auth.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        auth
                    );
                });

            });

            describe("authenticate", function () {

                beforeEach(
                    function () {
                        auth.$onInit();
                    }
                );

                it("should alias authenticationService.authenticate", function () {
                    expect(auth.authenticate).toEqual(locals.authenticationService.authenticate);
                });

            });

            describe("isAuthenticated", function () {

                beforeEach(
                    function () {
                        auth.$onInit();
                    }
                );

                it("should alias authenticationService.isAuthenticated", function () {
                    expect(auth.isAuthenticated).toEqual(locals.authenticationService.isAuthenticated);
                });

            });

            describe("getUser", function () {

                beforeEach(
                    function () {
                        auth.$onInit();
                    }
                );

                it("should alias authenticationService.getUser", function () {
                    expect(auth.getUser).toEqual(locals.authenticationService.getUser);
                });

            });

            describe("logout", function () {

                beforeEach(
                    function () {
                        auth.$onInit();
                    }
                );

                it("should alias authenticationService.logout", function () {
                    expect(auth.logout).toEqual(locals.authenticationService.logout);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
