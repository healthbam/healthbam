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
                ProgramArea,
                Organization,
                programsEndpoint,
                programAreasEndpoint,
                programAreas,
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
                            ProgramArea = $injector.get("ProgramArea");
                            Organization = $injector.get("Organization");
                        }
                    );

                    programsEndpoint = "api/programs";
                    organizationsEndpoint = "api/organizations";
                    programAreasEndpoint = "api/program-areas";

                    organizations = [
                        new Organization(
                            {
                                id: 100,
                                name: "Org-1"
                            }
                        )
                    ];

                    programAreas = [
                        new ProgramArea(
                            {
                                id: 1,
                                name: "ProgramArea-1"
                            }
                        )
                    ];

                    /* Set bindings. */
                    bindings.program = new Program();
                }
            );

            /**
             * Creates controller and mocks request responses.
             */
            function initController() {

                /* ProgramAreas fetched in activate() */
                $httpBackend.expectGET(
                    programAreasEndpoint
                ).respond(
                    angular.toJson(
                        programAreas
                    )
                );

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

            it("should exist", function () {
                initController();
                expect(programFormDialog).toEqual(jasmine.any(Object));
            });

            describe("programFormDialog.cancel", function () {

                beforeEach(initController);

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

                beforeEach(initController);

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

                beforeEach(initController);

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

                beforeEach(initController);

                it("should equal program-areas response", function () {
                    expect(programFormDialog.programAreas).toEqual(
                        jasmine.objectContaining(programAreas)
                    );
                });

            });

            describe("load failure error handling", function () {

                it("should show toast on ProgramAreas fetch failure", function () {

                    spyOn(locals.$mdToast, "show");

                    /* ProgramAreas fetched in activate() */
                    $httpBackend.expectGET(
                        programAreasEndpoint
                    ).respond(
                        418
                    );

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

                    expect(locals.$mdToast.show).toHaveBeenCalled();

                });

                it("should show toast on Organization fetch failure", function () {

                    spyOn(locals.$mdToast, "show");

                    /* ProgramAreas fetched in activate() */
                    $httpBackend.expectGET(
                        programAreasEndpoint
                    ).respond(
                        angular.toJson(
                            programAreas
                        )
                    );

                    /* Organizations fetched in activate() */
                    $httpBackend.expectGET(
                        organizationsEndpoint
                    ).respond(
                        418
                    );

                    /* Get the programFormDialog controller. */
                    programFormDialog = $controller(
                        "ProgramFormDialogController",
                        locals,
                        bindings
                    );

                    $httpBackend.flush();

                    expect(locals.$mdToast.show).toHaveBeenCalled();

                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
