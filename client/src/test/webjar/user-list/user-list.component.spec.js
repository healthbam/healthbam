(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.userList module's tests. */
    describe("healthBam.userList module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userList");
            }
        );

        describe("UserListController", function () {

            var userList,
                $componentController,
                $httpBackend,
                locals;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.User = $injector.get("User");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$state = $injector.get("$state");
                            locals.$log = $injector.get("$log");
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.userManagementService = $injector.get("userManagementService");
                            $httpBackend = $injector.get("$httpBackend");
                        }
                    );

                    /* Get the controller for the userList component. */
                    userList = $componentController(
                        "healthBamUserList",
                        locals
                    );

                    spyOn(locals.authenticationService, "isAdmin");
                    spyOn(locals.errorHandlingService, "handleError");
                }
            );

            it("should exist", function () {
                userList.$onInit();
                expect(userList).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    userList.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        userList
                    );
                });

                it("should load & expose the list of users from server if admin", function () {

                    var list = [
                        new locals.User(
                            {
                                id: 1,
                                displayName: "TestUser"
                            }
                        )
                    ];

                    locals.authenticationService.isAdmin.and.returnValue(true);

                    $httpBackend.expectGET(
                        "api/users"
                    ).respond(
                        list
                    );

                    userList.$onInit();

                    $httpBackend.flush();

                    expect(
                        userList.list
                    ).toEqual(
                        jasmine.objectContaining(list)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching Users when admin", function () {

                    locals.authenticationService.isAdmin.and.returnValue(true);

                    $httpBackend.expectGET(
                        "api/users"
                    ).respond(
                        418
                    );

                    userList.$onInit();

                    $httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

                it("should not fetch Users when not admin, handle error, and load map state", function () {

                    locals.authenticationService.isAdmin.and.returnValue(false);

                    spyOn(locals.$state, "go");

                    userList.$onInit();

                    $httpBackend.verifyNoOutstandingRequest();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();

                    expect(locals.$state.go).toHaveBeenCalledWith("map");
                });

            });

            describe("getRole", function () {

                var user;

                beforeEach(
                    function () {
                        userList.$onInit();
                        user = new locals.User(
                            {
                                id: 12345
                            }
                        );
                    }
                );

                it("should be exposed", function () {
                    expect(userList.getRole).toEqual(jasmine.any(Function));
                });

                it("should return 'Super admin' when superAdmin is true", function () {
                    user.superAdmin = true;
                    user.admin = true;
                    expect(userList.getRole(user)).toEqual("Super admin");
                });

                it("should return 'Admin' when superAdmin is false and admin is true", function () {
                    user.superAdmin = false;
                    user.admin = true;
                    expect(userList.getRole(user)).toEqual("Admin");
                });

                it("should return 'General user' when both superAdmin and admin are false", function () {
                    user.superAdmin = false;
                    user.admin = false;
                    expect(userList.getRole(user)).toEqual("General user");
                });

            });

            describe("grantAdmin", function () {

                it("should alias userManagementService.grantAdmin", function () {
                    userList.$onInit();
                    expect(userList.grantAdmin).toEqual(locals.userManagementService.grantAdmin);
                });

            });

            describe("revokeAdmin", function () {

                it("should alias userManagementService.revokeAdmin", function () {
                    userList.$onInit();
                    expect(userList.revokeAdmin).toEqual(locals.userManagementService.revokeAdmin);
                });

            });

            describe("deleteUser", function () {

                it("should alias userManagementService.deleteUser", function () {
                    userList.$onInit();
                    expect(userList.deleteUser).toEqual(locals.userManagementService.deleteUser);
                });

            });

        });
    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
