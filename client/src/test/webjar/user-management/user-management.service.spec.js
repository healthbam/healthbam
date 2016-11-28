(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.authentication module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userManagement");
            }
        );

        describe("userManagementService", function () {

            var userManagementService,
                $log,
                $mdDialog,
                $q,
                $state,
                errorHandlingService,
                $httpBackend,
                $rootScope,
                User,
                user,
                userUrl,
                event;

            beforeEach(
                function () {
                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            userManagementService = $injector.get("userManagementService");
                            $log = $injector.get("$log");
                            $mdDialog = $injector.get("$mdDialog");
                            $q = $injector.get("$q");
                            $state = $injector.get("$state");
                            errorHandlingService = $injector.get("errorHandlingService");
                            $httpBackend = $injector.get("$httpBackend");
                            $rootScope = $injector.get("$rootScope");
                            User = $injector.get("User");
                        }
                    );

                    spyOn(errorHandlingService, "handleError");
                    spyOn($mdDialog, "show");
                    spyOn($state, "reload");

                    user = new User(
                        {
                            id: 123,
                            displayName: "No Body",
                            email: "noreply@example.org",
                            admin: true,
                            superAdmin: false
                        }
                    );

                    userUrl = "api/users/" + user.id;

                    event = {
                        mock: "event"
                    };
                }
            );

            describe("getRole", function () {

                it("should exist", function () {
                    expect(userManagementService.getRole).toEqual(jasmine.any(Function));
                });

                it("should return 'Super admin' when superAdmin is true", function () {
                    user.superAdmin = true;
                    user.admin = true;
                    expect(userManagementService.getRole(user)).toEqual("Super admin");
                });

                it("should return 'Admin' when superAdmin is false and admin is true", function () {
                    user.superAdmin = false;
                    user.admin = true;
                    expect(userManagementService.getRole(user)).toEqual("Admin");
                });

                it("should return 'General user' when both superAdmin and admin are false", function () {
                    user.superAdmin = false;
                    user.admin = false;
                    expect(userManagementService.getRole(user)).toEqual("General user");
                });

            });

            describe("grantAdmin", function () {

                it("should exist", function () {
                    expect(userManagementService.grantAdmin).toEqual(jasmine.any(Function));
                });

                it("should prompt and do nothing on cancel", function () {
                    var dialogPromise = $q.reject("rejected");

                    $mdDialog.show.and.returnValue(dialogPromise);

                    /* Run test. */
                    userManagementService.grantAdmin(user, event);

                    $rootScope.$digest();

                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).not.toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should set admin to true, save, & reload", function () {
                    var dialogPromise = $q.resolve("resolved"),
                        userCopy = angular.copy(user);

                    $mdDialog.show.and.returnValue(dialogPromise);
                    user.admin = false;
                    userCopy.admin = true;

                    $httpBackend.expectPOST(
                        userUrl,
                        userCopy
                    ).respond(
                        angular.toJson(userCopy)
                    );

                    /* Run test. */
                    userManagementService.grantAdmin(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(user.admin).toEqual(true);
                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should handle error on failed save & reload", function () {
                    var dialogPromise = $q.resolve("resolved"),
                        userCopy = angular.copy(user);

                    $mdDialog.show.and.returnValue(dialogPromise);
                    user.admin = false;
                    userCopy.admin = true;

                    $httpBackend.expectPOST(
                        userUrl,
                        userCopy
                    ).respond(
                        418
                    );

                    /* Run test. */
                    userManagementService.grantAdmin(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(errorHandlingService.handleError).toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

            });

            describe("revokeAdmin", function () {

                it("should exist", function () {
                    expect(userManagementService.revokeAdmin).toEqual(jasmine.any(Function));
                });

                it("should prompt and do nothing on cancel", function () {
                    var dialogPromise = $q.reject("rejected");

                    $mdDialog.show.and.returnValue(dialogPromise);

                    /* Run test. */
                    userManagementService.revokeAdmin(user, event);

                    $rootScope.$digest();

                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).not.toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should set admin to false, save, & reload", function () {
                    var dialogPromise = $q.resolve("resolved"),
                        userCopy = angular.copy(user);

                    $mdDialog.show.and.returnValue(dialogPromise);
                    user.admin = true;
                    userCopy.admin = false;

                    $httpBackend.expectPOST(
                        userUrl,
                        userCopy
                    ).respond(
                        angular.toJson(userCopy)
                    );

                    /* Run test. */
                    userManagementService.revokeAdmin(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(user.admin).toEqual(false);
                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should handle error on failed save & reload", function () {
                    var dialogPromise = $q.resolve("resolved"),
                        userCopy = angular.copy(user);

                    $mdDialog.show.and.returnValue(dialogPromise);
                    user.admin = true;
                    userCopy.admin = false;

                    $httpBackend.expectPOST(
                        userUrl,
                        userCopy
                    ).respond(
                        418
                    );

                    /* Run test. */
                    userManagementService.revokeAdmin(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(errorHandlingService.handleError).toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

            });

            describe("deleteUser", function () {

                it("should exist", function () {
                    expect(userManagementService.deleteUser).toEqual(jasmine.any(Function));
                });

                it("should prompt and do nothing on cancel", function () {
                    var dialogPromise = $q.reject("rejected");

                    $mdDialog.show.and.returnValue(dialogPromise);

                    /* Run test. */
                    userManagementService.deleteUser(user, event);

                    $rootScope.$digest();

                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).not.toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should delete, & reload", function () {
                    var dialogPromise = $q.resolve("resolved");

                    $mdDialog.show.and.returnValue(dialogPromise);

                    $httpBackend.expectDELETE(
                        userUrl
                    ).respond(
                        angular.toJson(user)
                    );

                    /* Run test. */
                    userManagementService.deleteUser(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(errorHandlingService.handleError).not.toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

                it("should handle error on failed delete & reload", function () {
                    var dialogPromise = $q.resolve("resolved");

                    $mdDialog.show.and.returnValue(dialogPromise);

                    $httpBackend.expectDELETE(
                        userUrl
                    ).respond(
                        418
                    );

                    /* Run test. */
                    userManagementService.deleteUser(user, event);

                    $rootScope.$digest();
                    $httpBackend.flush();

                    expect(errorHandlingService.handleError).toHaveBeenCalled();
                    expect($state.reload).toHaveBeenCalled();
                    $httpBackend.verifyNoOutstandingRequest();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
