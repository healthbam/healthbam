(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.organizationDetails module's tests. */
    describe("healthBam.organizationDetails module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationDetails");
            }
        );

        describe("OrganizationDetailsController", function () {

            var organizationDetails,
                $componentController,
                locals,
                bindings,
                $httpBackend,
                expectedOrg,
                orgId,
                event,
                $rootScope,
                organizationEndpoint;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            $httpBackend = $injector.get("$httpBackend");
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.Organization = $injector.get("Organization");
                            locals.$stateParams = $injector.get("$stateParams");
                            locals.$state = $injector.get("$state");
                            locals.$q = $injector.get("$q");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$mdDialog = $injector.get("$mdDialog");
                            locals.$mdToast = $injector.get("$mdToast");
                            locals.mapConfig = $injector.get("mapConfig");
                            locals.$log = $injector.get("$log");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );
                    orgId = 1234;
                    locals.$stateParams.orgId = orgId;
                    organizationEndpoint = "api/organizations/" + orgId;

                    event = {
                        mock: "event"
                    };

                    expectedOrg = new locals.Organization(
                        {
                            id: orgId
                        }
                    );

                    spyOn(locals.$mdDialog, "show");
                    spyOn(locals.$mdToast, "show");
                    spyOn(locals.$log, "debug");
                    spyOn(locals.errorHandlingService, "handleError");
                    spyOn(locals.$state, "go");
                }
            );

            /**
             * Creates controller and mocks request responses.
             */
            function initController() {

                $httpBackend.expectGET(
                    organizationEndpoint
                ).respond(
                    angular.toJson(expectedOrg)
                );

                /* Get the controller for the organizationDetails component. */
                organizationDetails = $componentController(
                    "healthBamOrganizationDetails",
                    locals,
                    bindings
                );

                organizationDetails.$onInit();

                expect(organizationDetails.loading).toEqual(true);

                $httpBackend.flush();

                expect(organizationDetails.loading).toEqual(false);
            }

            it("should exist", function () {
                initController();
                expect(organizationDetails).toEqual(jasmine.any(Object));
            });

            describe("$onInit success", function () {

                beforeEach(initController);

                it("should expose organization", function () {
                    expect(organizationDetails.organization).toEqual(jasmine.objectContaining(expectedOrg));
                });

                // it("should expose mapStyles", function () {
                //     expect(organizationDetails.mapStyles).toEqual(locals.mapConfig.styles);
                // });

                it("should log loading debug message", function () {
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        organizationDetails
                    );
                });
            });

            describe("$onInit failure", function () {

                it("should handle error response when failing to get organization", function () {

                    $httpBackend.expectGET(
                        organizationEndpoint
                    ).respond(
                        418
                    );

                    /* Get the controller for the organizationDetails component. */
                    organizationDetails = $componentController(
                        "healthBamOrganizationDetails",
                        locals,
                        bindings
                    );

                    organizationDetails.$onInit();

                    expect(organizationDetails.loading).toEqual(true);

                    $httpBackend.flush();

                    expect(organizationDetails.loading).toEqual(false);

                    expect(locals.errorHandlingService.handleError).toHaveBeenCalledWith(jasmine.any(String));
                });
            });

            describe("deleteOrganization", function () {

                beforeEach(initController);

                it("should be exposed", function () {
                    expect(organizationDetails.deleteOrganization).toEqual(jasmine.any(Function));
                });

                it("should show confirm dialog and not delete on cancel", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.reject("canceled")
                    );

                    /* Run function to test. */
                    organizationDetails.deleteOrganization(event);

                    /* Execute async code. */
                    $rootScope.$digest();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.$mdToast.show).not.toHaveBeenCalled();
                    expect(locals.$state.go).not.toHaveBeenCalled();
                });

                it("should show confirm dialog, delete, and go to map on confirm", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.resolve("confirmed")
                    );

                    $httpBackend.expectDELETE(
                        organizationEndpoint
                    ).respond(
                        angular.toJson(expectedOrg)
                    );

                    /* Run function to test. */
                    organizationDetails.deleteOrganization(event);

                    $httpBackend.flush();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.$mdToast.show).toHaveBeenCalled();
                    expect(locals.$state.go).toHaveBeenCalledWith("map");
                });

                it("should show confirm dialog, delete, and go to map on confirm but error", function () {

                    /* Train mocks. */
                    locals.$mdDialog.show.and.returnValue(
                        locals.$q.resolve("confirmed")
                    );

                    $httpBackend.expectDELETE(
                        organizationEndpoint
                    ).respond(
                        418
                    );

                    /* Run function to test. */
                    organizationDetails.deleteOrganization(event);

                    $httpBackend.flush();

                    /* Verify results. */
                    $httpBackend.verifyNoOutstandingRequest();
                    expect(locals.$mdDialog.show).toHaveBeenCalled();
                    expect(locals.errorHandlingService.handleError).toHaveBeenCalled();
                    expect(locals.$state.go).not.toHaveBeenCalled();
                });

            });

            describe("isAdmin", function () {

                beforeEach(initController);

                it("should alias authenticationService.isAdmin", function () {
                    expect(organizationDetails.isAdmin).toEqual(locals.authenticationService.isAdmin);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
