(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.userAccountActions module's tests. */
    describe("healthBam.userAccountActions module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userAccountActions");
            }
        );

        describe("UserAccountActionsController", function () {

            var userAccountActions,
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
                            locals.$mdDialog = $injector.get("$mdDialog");
                        }
                    );

                    spyOn(locals.$mdDialog, "show");

                    locals.$mdMedia = jasmine.createSpy(
                        "$mdMedia"
                    );

                    /* Get the controller for the userAccountActions component. */
                    userAccountActions = $componentController(
                        "healthBamUserAccountActions",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(userAccountActions).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    userAccountActions.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        userAccountActions
                    );
                });

            });

            describe("authenticate", function () {

                beforeEach(
                    function () {
                        userAccountActions.$onInit();
                    }
                );

                it("should alias authenticationService.authenticate", function () {
                    expect(userAccountActions.authenticate).toEqual(locals.authenticationService.authenticate);
                });

            });

            describe("isAuthenticated", function () {

                beforeEach(
                    function () {
                        userAccountActions.$onInit();
                    }
                );

                it("should alias authenticationService.isAuthenticated", function () {
                    expect(userAccountActions.isAuthenticated).toEqual(locals.authenticationService.isAuthenticated);
                });

            });

            describe("getUser", function () {

                beforeEach(
                    function () {
                        userAccountActions.$onInit();
                    }
                );

                it("should alias authenticationService.getUser", function () {
                    expect(userAccountActions.getUser).toEqual(locals.authenticationService.getUser);
                });

            });

            describe("logout", function () {

                beforeEach(
                    function () {
                        userAccountActions.$onInit();
                    }
                );

                it("should alias authenticationService.logout", function () {
                    expect(userAccountActions.logout).toEqual(locals.authenticationService.logout);
                });

            });

            describe("viewAccount", function () {

                var dialogOptions,
                    event;

                beforeEach(
                    function () {
                        userAccountActions.$onInit();

                        event = {
                            mock: "event"
                        };

                        dialogOptions = {
                            templateUrl: "/user-account-details.html",
                            controller: "UserAccountDetailsController",
                            controllerAs: "userAccountDetails",
                            targetEvent: event,
                            fullscreen: false,
                            escapeToClose: true
                        };
                    }
                );

                it("should be exposed", function () {
                    expect(userAccountActions.viewAccount).toEqual(jasmine.any(Function));
                });

                it("should open the account details dialog fullscreen", function () {
                    dialogOptions.fullscreen = false;
                    locals.$mdMedia.and.returnValue(true);

                    userAccountActions.viewAccount(event);

                    expect(locals.$mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                    expect(locals.$mdMedia).toHaveBeenCalledWith("gt-sm");
                });

                it("should open the account details dialog not fullscreen", function () {
                    dialogOptions.fullscreen = true;
                    locals.$mdMedia.and.returnValue(false);

                    userAccountActions.viewAccount(event);

                    expect(locals.$mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                    expect(locals.$mdMedia).toHaveBeenCalledWith("gt-sm");
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
