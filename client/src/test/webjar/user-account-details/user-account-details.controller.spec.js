(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.userAccountDetails module's tests. */
    describe("healthBam.userAccountDetails module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userAccountDetails");
            }
        );

        describe("UserAccountDetailsController", function () {

            var userAccountDetails,
                $controller,
                locals,
                bindings,
                User,
                currentUser,
                userData;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $controller = $injector.get("$controller");
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.userManagementService = $injector.get("userManagementService");
                            locals.$log = $injector.get("$log");
                            locals.$mdDialog = $injector.get("$mdDialog");
                            User = $injector.get("User");
                        }
                    );

                    userData = {
                        userId: 12345,
                        displayName: "displayName",
                        email: "email",
                        imageUrl: "imageUrl",
                        admin: true,
                        superAdmin: false
                    };

                    currentUser = new User(
                        {
                            id: userData.userId,
                            displayName: userData.displayName,
                            email: userData.email,
                            imageUrl: userData.imageUrl,
                            admin: userData.admin,
                            superAdmin: userData.superAdmin
                        }
                    );

                    spyOn(locals.$mdDialog, "hide");
                    spyOn(locals.authenticationService, "getUser");
                    locals.authenticationService.getUser.and.returnValue(userData);

                    /* Get the UserAccountDetailsController instance to test. */
                    userAccountDetails = $controller(
                        "UserAccountDetailsController",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(userAccountDetails).toEqual(jasmine.any(Object));
            });

            it("should expose current user", function () {
                expect(userAccountDetails.user).toEqual(currentUser);
            });

            describe("close", function () {

                it("should be exposed", function () {
                    expect(userAccountDetails.close).toEqual(jasmine.any(Function));
                });

                it("should hide dialog", function () {
                    userAccountDetails.close();
                    expect(locals.$mdDialog.hide).toHaveBeenCalled();
                });

            });

            describe("deleteUser", function () {

                it("should alias userManagementService.deleteUser", function () {
                    expect(userAccountDetails.deleteUser).toEqual(locals.userManagementService.deleteUser);
                });

            });

            describe("getRole", function () {

                it("should alias userManagementService.getRole", function () {
                    expect(userAccountDetails.getRole).toEqual(locals.userManagementService.getRole);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
