(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.reportList module's tests. */
    describe("healthBam.reportList module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.reportList");
            }
        );

        describe("ReportListController", function () {

            var reportList,
                $componentController,
                locals,
                authToken;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.errorHandlingService = $injector.get("errorHandlingService");
                            locals.$state = $injector.get("$state");
                            locals.$log = $injector.get("$log");
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.$window = $injector.get("$window");
                        }
                    );

                    /* Get the controller for the reportList component. */
                    reportList = $componentController(
                        "healthBamReportList",
                        locals
                    );

                    authToken = "mock-auth-token";

                    spyOn(locals.$window, "open");
                    spyOn(locals.errorHandlingService, "handleError");
                    spyOn(locals.authenticationService, "isAdmin");
                    spyOn(locals.authenticationService, "getToken");
                    locals.authenticationService.getToken.and.returnValue(authToken);

                }
            );

            it("should exist", function () {
                reportList.$onInit();
                expect(reportList).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    reportList.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        reportList
                    );
                });

                it("should not load Reports when not admin, handle error, and load map state", function () {

                    locals.authenticationService.isAdmin.and.returnValue(false);

                    spyOn(locals.$state, "go");

                    reportList.$onInit();

                    expect(
                        locals.errorHandlingService.handleError
                    ).toHaveBeenCalled();

                    expect(locals.$state.go).toHaveBeenCalledWith("map");
                });

            });

            describe("downloadProgramReport", function () {

                beforeEach(
                    function () {
                        reportList.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(reportList.downloadProgramReport).toEqual(jasmine.any(Function));
                });

                it("should download report in new tab", function () {
                    reportList.downloadProgramReport();
                    expect(locals.$window.open).toHaveBeenCalledWith(
                        "api/programs/csv?token=" + authToken,
                        "_blank"
                    );
                });

            });

            describe("downloadUserReport", function () {

                beforeEach(
                    function () {
                        reportList.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(reportList.downloadUserReport).toEqual(jasmine.any(Function));
                });

                it("should download report in new tab", function () {
                    reportList.downloadUserReport();
                    expect(locals.$window.open).toHaveBeenCalledWith(
                        "api/users/csv?token=" + authToken,
                        "_blank"
                    );
                });

            });

        });
    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
