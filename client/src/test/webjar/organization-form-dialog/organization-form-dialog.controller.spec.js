(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.organizationFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationFormDialog");
            }
        );

        describe("OrganizationFormDialogController", function () {

            var organizationFormDialog,
                $controller,
                locals,
                bindings,
                Organization,
                organizationsEndpoint,
                organization,
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
                            Organization = $injector.get("Organization");
                            $httpBackend = $injector.get("$httpBackend");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );

                    organizationsEndpoint = "api/organizations";

                    /* Set bindings. */
                    bindings.organization = new Organization();
                }
            );

            /**
             * Creates controller and mocks request responses.
             */
            function initController() {
                /* Get the organizationFormDialog controller. */
                organizationFormDialog = $controller(
                    "OrganizationFormDialogController",
                    locals,
                    bindings
                );
            }

            it("should exist", function () {
                initController();
                expect(organizationFormDialog).toEqual(jasmine.any(Object));
            });

            describe("organizationFormDialog.cancel", function () {

                beforeEach(initController);

                it("should exist", function () {
                    expect(organizationFormDialog.cancel).toEqual(jasmine.any(Function));
                });

                it("should cancel dialog with organization", function () {

                    var actual,
                        expected = {
                            x: "stuff"
                        };

                    spyOn(locals.$mdDialog, "cancel");

                    locals.$mdDialog.cancel.and.returnValue(expected);

                    actual = organizationFormDialog.cancel();

                    expect(locals.$mdDialog.cancel).toHaveBeenCalledWith(
                        organizationFormDialog.organization
                    );

                    expect(actual).toEqual(expected);
                });

            });

            describe("organizationFormDialog.save", function () {

                beforeEach(initController);

                it("should exist", function () {
                    expect(organizationFormDialog.save).toEqual(jasmine.any(Function));
                });

                it("should save and hide dialog with organization promise on success", function () {

                    var actualPromise,
                        expected,
                        promiseSuccess = false;

                    expected = {
                        x: "stuff"
                    };

                    spyOn(locals.$mdDialog, "hide");

                    locals.$mdDialog.hide.and.returnValue(expected);

                    $httpBackend.expectPOST(
                        organizationsEndpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actualPromise = organizationFormDialog.save();

                    expect(organizationFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(organizationFormDialog.loading).toEqual(false);

                    actualPromise.then(
                        function (actual) {
                            promiseSuccess = true;

                            expect(actual).toEqual(expected);

                            expect(locals.$mdDialog.hide).toHaveBeenCalledWith(
                                organizationFormDialog.organization
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
                        organizationsEndpoint
                    ).respond(
                        417
                    );

                    actualPromise = organizationFormDialog.save();

                    expect(organizationFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(organizationFormDialog.loading).toEqual(false);

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
