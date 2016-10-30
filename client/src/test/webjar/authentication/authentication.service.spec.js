(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.authentication module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.authentication");
            }
        );

        describe("authenticationService", function () {

            var authenticationService,
                $auth,
                $q,
                $rootScope,
                errorHandlingService;

            beforeEach(
                function () {
                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            authenticationService = $injector.get("authenticationService");
                            $auth = $injector.get("$auth");
                            $q = $injector.get("$q");
                            $rootScope = $injector.get("$rootScope");
                            errorHandlingService = $injector.get("errorHandlingService");
                        }
                    );

                    spyOn($auth, "authenticate");
                    spyOn($auth, "isAuthenticated");
                    spyOn($auth, "getPayload");
                    spyOn($auth, "getToken");
                    spyOn($auth, "logout");

                    spyOn(errorHandlingService, "handleError");
                }
            );

            describe("authenticate", function () {

                var deferred;

                beforeEach(
                    function () {
                        deferred = $q.defer();
                        $auth.authenticate.and.returnValue(deferred.promise);
                    }
                );

                it("should exist", function () {
                    expect(authenticationService.authenticate).toEqual(jasmine.any(Function));
                });

                it("should authenticate with Google using satellizer and return promise on success", function () {

                    var logonPromise,
                        logonData = "logon success",
                        promiseSucceeded = false;

                    logonPromise = authenticationService.authenticate();
                    expect($auth.authenticate).toHaveBeenCalledWith("google");

                    logonPromise.then(
                        function (data) {
                            promiseSucceeded = true;
                            expect(data).toEqual(logonData);
                        }
                    );

                    deferred.resolve(logonData);
                    $rootScope.$digest();
                    expect(promiseSucceeded).toEqual(true);
                });

                it("should authenticate, return promise, and handle error on failure", function () {

                    var logonPromise,
                        logonData = "logon failure",
                        promiseFailed = false;

                    logonPromise = authenticationService.authenticate();
                    expect($auth.authenticate).toHaveBeenCalledWith("google");

                    logonPromise.catch(
                        function (data) {
                            promiseFailed = true;
                            expect(data).toEqual(logonData);
                            expect(errorHandlingService.handleError).toHaveBeenCalledWith(jasmine.any(String));
                        }
                    );

                    deferred.reject(logonData);
                    $rootScope.$digest();
                    expect(promiseFailed).toEqual(true);
                });

            });

            describe("isAuthenticated", function () {

                it("should exist", function () {
                    expect(authenticationService.isAuthenticated).toEqual(jasmine.any(Function));
                });

                it("should call $auth.isAuthenticated and return result", function () {

                    var actual,
                        expected = true;

                    $auth.isAuthenticated.and.returnValue(expected);

                    actual = authenticationService.isAuthenticated();
                    expect(actual).toEqual(expected);
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                });

            });

            describe("isAdmin", function () {

                it("should exist", function () {
                    expect(authenticationService.isAdmin).toEqual(jasmine.any(Function));
                });

                it("should return false if not authenticated", function () {

                    var actual,
                        expected = false;

                    $auth.isAuthenticated.and.returnValue(false);

                    actual = authenticationService.isAdmin();
                    expect(actual).toEqual(expected);
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                    expect($auth.getPayload).not.toHaveBeenCalled();
                });

                it("should return false if authenticated but not an admin", function () {

                    var actual,
                        expected = false;

                    $auth.isAuthenticated.and.returnValue(true);
                    $auth.getPayload.and.returnValue(
                        {
                            admin: false
                        }
                    );

                    actual = authenticationService.isAdmin();
                    expect(actual).toEqual(expected);
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                    expect($auth.getPayload).toHaveBeenCalled();
                });

                it("should return true if authenticated and an admin", function () {

                    var actual,
                        expected = true;

                    $auth.isAuthenticated.and.returnValue(true);
                    $auth.getPayload.and.returnValue(
                        {
                            admin: true
                        }
                    );

                    actual = authenticationService.isAdmin();
                    expect(actual).toEqual(expected);
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                    expect($auth.getPayload).toHaveBeenCalled();
                });

            });

            describe("getUser", function () {

                it("should exist", function () {
                    expect(authenticationService.getUser).toEqual(jasmine.any(Function));
                });

                it("should return undefined if not authenticated", function () {

                    var actual;

                    $auth.isAuthenticated.and.returnValue(false);

                    actual = authenticationService.getUser();
                    expect(actual).toBeUndefined();
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                    expect($auth.getPayload).not.toHaveBeenCalled();
                });

                it("should return payload if user authenticated", function () {

                    var actual,
                        expected;

                    expected = {
                        displayName: "Mock User"
                    };

                    $auth.isAuthenticated.and.returnValue(true);
                    $auth.getPayload.and.returnValue(expected);

                    actual = authenticationService.getUser();
                    expect(actual).toEqual(expected);
                    expect($auth.isAuthenticated).toHaveBeenCalled();
                    expect($auth.getPayload).toHaveBeenCalled();
                });

            });

            describe("getToken", function () {

                it("should exist", function () {
                    expect(authenticationService.getToken).toEqual(jasmine.any(Function));
                });

                it("should call $auth.getToken and return the result", function () {

                    var actual,
                        expected;

                    expected = "my-mock-token";
                    $auth.getToken.and.returnValue(expected);

                    actual = authenticationService.getToken();

                    expect(actual).toEqual(expected);
                    expect($auth.getToken).toHaveBeenCalled();
                });

            });

            describe("logout", function () {

                it("should exist", function () {
                    expect(authenticationService.logout).toEqual(jasmine.any(Function));
                });

                it("should call $auth.logout and return the result", function () {

                    var actual,
                        expected;

                    expected = $q.resolve("logout promise");
                    $auth.logout.and.returnValue(expected);

                    actual = authenticationService.logout();

                    expect(actual).toEqual(expected);
                    expect($auth.logout).toHaveBeenCalled();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
