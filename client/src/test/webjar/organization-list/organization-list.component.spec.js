(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.organizationList module's tests. */
    describe("healthBam.organizationList module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationList");
            }
        );

        describe("OrganizationListController", function () {

            var organizationList,
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
                            locals.Organization = $injector.get("Organization");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$state = $injector.get("$state");
                            locals.$log = $injector.get("$log");
                            $httpBackend = $injector.get("$httpBackend");
                        }
                    );

                    /* Get the controller for the organizationList component. */
                    organizationList = $componentController(
                        "healthBamOrganizationList",
                        locals
                    );

                    spyOn(locals.errorHandlingService, "handleError");
                }
            );

            it("should exist", function () {
                organizationList.$onInit();
                expect(organizationList).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    organizationList.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        organizationList
                    );
                });

                it("should load & expose the list of organizations from server", function () {

                    var list = [
                        new locals.Organization(
                            {
                                id: 1,
                                name: "TestOrganizations"
                            }
                        )
                    ];

                    $httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        list
                    );

                    organizationList.$onInit();

                    $httpBackend.flush();

                    expect(
                        organizationList.list
                    ).toEqual(
                        jasmine.objectContaining(list)
                    );

                    expect(
                        locals.errorHandlingService.handleError
                    ).not.toHaveBeenCalled();
                });

                it("should handle error fetching Organizations", function () {

                    $httpBackend.expectGET(
                        "api/organizations"
                    ).respond(
                        418
                    );

                    organizationList.$onInit();

                    $httpBackend.flush();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();
                });

            });

            describe("viewDetails", function () {

                var organization;

                beforeEach(
                    function () {
                        organizationList.$onInit();
                        spyOn(locals.$state, "go");
                        organization = new locals.Organization(
                            {
                                id: 12345
                            }
                        );
                    }
                );

                it("should be exposed", function () {
                    expect(organizationList.viewDetails).toEqual(jasmine.any(Function));
                });

                it("should navigate to the organizationDetails state", function () {
                    organizationList.viewDetails(organization);
                    expect(
                        locals.$state.go
                    ).toHaveBeenCalledWith(
                        "organizationDetails",
                        {
                            orgId: organization.id
                        }
                    );
                });

            });

        });
    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
