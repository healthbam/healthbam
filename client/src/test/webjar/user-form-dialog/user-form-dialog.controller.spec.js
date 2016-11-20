(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.userFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userFormDialog");
            }
        );

        describe("UserFormDialogController", function () {

            var userFormDialog,
                $controller,
                locals,
                bindings,
                User,
                usersEndpoint,
                $httpBackend,
                $rootScope;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $controller = $injector.get("$controller");
                            locals.$mdDialog = $injector.get("$mdDialog");
                            locals.$mdToast = $injector.get("$mdToast");
                            User = $injector.get("User");
                            $httpBackend = $injector.get("$httpBackend");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );

                    usersEndpoint = "api/users";

                    /* Set bindings. */
                    bindings.user = new User(
                        {
                            admin: true
                        }
                    );
                }
            );

            /**
             * Creates controller and mocks request responses.
             */
            function initController() {

                /* Get the userFormDialog controller. */
                userFormDialog = $controller(
                    "UserFormDialogController",
                    locals,
                    bindings
                );

            }

            it("should exist", function () {
                initController();
                expect(userFormDialog).toEqual(jasmine.any(Object));
            });

            describe("userFormDialog.cancel", function () {

                beforeEach(initController);

                it("should exist", function () {
                    expect(userFormDialog.cancel).toEqual(jasmine.any(Function));
                });

                it("should cancel dialog with user", function () {

                    var actual,
                        expected = {
                            x: "stuff"
                        };

                    spyOn(locals.$mdDialog, "cancel");

                    locals.$mdDialog.cancel.and.returnValue(expected);

                    actual = userFormDialog.cancel();

                    expect(locals.$mdDialog.cancel).toHaveBeenCalledWith(
                        userFormDialog.user
                    );

                    expect(actual).toEqual(expected);
                });

            });

            describe("userFormDialog.save", function () {

                beforeEach(initController);

                it("should exist", function () {
                    expect(userFormDialog.save).toEqual(jasmine.any(Function));
                });

                it("should save and hide dialog with user promise on success", function () {

                    var actualPromise,
                        expected,
                        promiseSuccess = false;

                    expected = {
                        x: "stuff"
                    };

                    spyOn(locals.$mdDialog, "hide");

                    locals.$mdDialog.hide.and.returnValue(expected);

                    $httpBackend.expectPOST(
                        usersEndpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actualPromise = userFormDialog.save();

                    expect(userFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(userFormDialog.loading).toEqual(false);

                    actualPromise.then(
                        function (actual) {
                            promiseSuccess = true;

                            expect(actual).toEqual(expected);

                            expect(locals.$mdDialog.hide).toHaveBeenCalledWith(
                                userFormDialog.user
                            );
                        }
                    );

                    /* Ensure async assert above runs. */
                    $rootScope.$digest();
                    expect(promiseSuccess).toEqual(true);
                });

                it("should show toast and leave dialog open on error", function () {

                    var actualPromise,
                        promiseRejected = false;

                    spyOn(locals.$mdDialog, "hide");
                    spyOn(locals.$mdDialog, "cancel");
                    spyOn(locals.$mdToast, "show");

                    $httpBackend.expectPOST(
                        usersEndpoint
                    ).respond(
                        417
                    );

                    actualPromise = userFormDialog.save();

                    expect(userFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(userFormDialog.loading).toEqual(false);

                    actualPromise.catch(
                        function () {
                            promiseRejected = true;
                            expect(locals.$mdToast.show).toHaveBeenCalled();
                            expect(locals.$mdDialog.hide).not.toHaveBeenCalled();
                            expect(locals.$mdDialog.cancel).not.toHaveBeenCalled();
                        }
                    );

                    /* Ensure async assert above runs. */
                    $rootScope.$digest();
                    expect(promiseRejected).toEqual(true);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
