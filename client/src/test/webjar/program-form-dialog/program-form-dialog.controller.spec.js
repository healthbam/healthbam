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
                                name: "Org-100"
                            }
                        ),
                        new Organization(
                            {
                                id: 101,
                                name: "Org-101"
                            }
                        ),
                        new Organization(
                            {
                                id: 102,
                                name: "Org-102"
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
                    bindings.program = new Program(
                        {
                            countiesServed: [],
                            programAreas: [],
                            state: "GA"
                        }
                    );
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

                expect(programFormDialog.loading).toEqual(true);

                $httpBackend.flush();

                expect(programFormDialog.loading).toEqual(false);
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

                    expect(programFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(programFormDialog.loading).toEqual(false);

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

                    expect(programFormDialog.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(programFormDialog.loading).toEqual(false);

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

                it("should equal program-areas response with value:false for all programAreas", function () {

                    initController();

                    programAreas.forEach(
                        function (programArea) {
                            programArea.value = false;
                        }
                    );

                    expect(programFormDialog.programAreas).toEqual(
                        jasmine.objectContaining(programAreas)
                    );
                });

                it("should equal program-areas response with value:true for all programAreas", function () {

                    bindings.program.programAreas = angular.copy(programAreas);

                    initController();

                    programAreas.forEach(
                        function (programArea) {
                            programArea.value = true;
                        }
                    );

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

            describe("programFormDialog.setProgramOrganization", function () {

                beforeEach(initController);

                it("should exist", function () {
                    expect(programFormDialog.setProgramOrganization).toEqual(jasmine.any(Function));
                });

                it("should not alter program when no existing organization", function () {
                    delete programFormDialog.program.organization;
                    programFormDialog.setProgramOrganization();
                    expect(programFormDialog.program.organization).toBeUndefined();
                });

                it("should set program's organization if IDs match", function () {
                    programFormDialog.program.organization = {
                        id: organizations[1].id
                    };
                    programFormDialog.setProgramOrganization();
                    expect(programFormDialog.program.organization).toEqual(organizations[1]);
                });

                it("should set not alter program if no IDs match", function () {

                    var originalOrg = {
                        id: 7890
                    };

                    programFormDialog.program.organization = angular.copy(originalOrg);
                    programFormDialog.setProgramOrganization();
                    expect(programFormDialog.program.organization).toEqual(originalOrg);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
