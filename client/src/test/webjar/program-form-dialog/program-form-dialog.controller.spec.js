(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.programFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programFormDialog");
            }
        );

        describe("ProgramFormDialogController", function () {

            var programFormDialog,
                $controller,
                locals,
                bindings,
                Program,
                programsEndpoint,
                organizationsEndpoint,
                organizations,
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
                            Program = $injector.get("Program");
                            $httpBackend = $injector.get("$httpBackend");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );

                    programsEndpoint = "api/programs";
                    organizationsEndpoint = "";

                    organizations = [
                        {
                            id: 100,
                            name: "Org-1"
                        }
                    ];

                    /* Set bindings. */
                    bindings.program = new Program();

                    /* Organizations fetched in activate() */
                    $httpBackend.expectGET(
                        organizationsEndpoint
                    ).respond(
                        angular.toJson(
                            organizations
                        )
                    );

                    /* Get the programFormDialog controller. */
                    programFormDialog = $controller(
                        "ProgramFormDialogController",
                        locals,
                        bindings
                    );

                    $httpBackend.flush();
                }
            );

            it("should exist", function () {
                expect(programFormDialog).toEqual(jasmine.any(Object));
            });

            describe("programFormDialog.cancel", function () {

                it("should exist", function () {
                    expect(programFormDialog.cancel).toEqual(jasmine.any(Function));
                });

                it("should cancel dialog with program", function () {

                    var actual,
                        expected = {
                            x: "stuff"
                        };

                    spyOn(locals.$mdDialog, "cancel");

                    locals.$mdDialog.cancel.and.returnValue(expected);

                    actual = programFormDialog.cancel();

                    expect(locals.$mdDialog.cancel).toHaveBeenCalledWith(
                        programFormDialog.program
                    );

                    expect(actual).toEqual(expected);
                });

            });

            describe("programFormDialog.save", function () {

                it("should exist", function () {
                    expect(programFormDialog.save).toEqual(jasmine.any(Function));
                });

                it("should save and hide dialog with program promise on success", function () {

                    var actualPromise,
                        expected,
                        promiseSuccess = false;

                    expected = {
                        x: "stuff"
                    };

                    spyOn(locals.$mdDialog, "hide");

                    locals.$mdDialog.hide.and.returnValue(expected);

                    $httpBackend.expectPOST(
                        programsEndpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actualPromise = programFormDialog.save();

                    $httpBackend.flush();

                    actualPromise.then(
                        function (actual) {
                            promiseSuccess = true;

                            expect(actual).toEqual(expected);

                            expect(locals.$mdDialog.hide).toHaveBeenCalledWith(
                                programFormDialog.program
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
                        programsEndpoint
                    ).respond(
                        417
                    );

                    actualPromise = programFormDialog.save();

                    $httpBackend.flush();

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

            describe("programFormDialog.getCurrentYear", function () {

                it("should exist", function () {
                    expect(programFormDialog.getCurrentYear).toEqual(
                        jasmine.any(Function)
                    );
                });

                it("should return the current year", function () {
                    expect(
                        programFormDialog.getCurrentYear()
                    ).toEqual(
                        new Date().getFullYear()
                    );
                });

            });

            describe("programFormDialog.programAreas", function () {

                it("should exist", function () {
                    expect(programFormDialog.programAreas).toEqual(
                        jasmine.any(Array)
                    );
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
