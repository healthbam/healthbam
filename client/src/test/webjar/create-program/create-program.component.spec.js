(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.createProgram module's tests. */
    describe("healthBam.createProgram module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.createProgram");
            }
        );

        describe("CreateProgramController", function () {

            var createProgram,
                $componentController,
                locals,
                bindings;

            beforeEach(
                function () {

                    /* Reset variables. */
                    locals = {};
                    bindings = {};

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            $componentController = $injector.get("$componentController");
                            locals.authenticationService = $injector.get("authenticationService");
                            locals.programFormDialogService = $injector.get("programFormDialogService");
                            locals.$log = $injector.get("$log");
                        }
                    );

                    /* Get the controller for the createProgram component. */
                    createProgram = $componentController(
                        "healthBamCreateProgram",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(createProgram).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    createProgram.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        createProgram
                    );
                });

            });

            describe("openProgramForm", function () {

                beforeEach(
                    function () {
                        createProgram.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(createProgram.openProgramForm).toEqual(jasmine.any(Function));
                });

                it("should open dialog and return its promise", function () {

                    var actual,
                        expected,
                        event;

                    expected = {
                        fake: "promise"
                    };

                    event = {
                        mock: "clickEvent"
                    };

                    spyOn(locals.programFormDialogService, "create");
                    locals.programFormDialogService.create.and.returnValue(expected);

                    actual = createProgram.openProgramForm(event);

                    expect(actual).toEqual(expected);

                    expect(locals.programFormDialogService.create).toHaveBeenCalledWith(
                        event
                    );
                });

            });

            describe("isAdmin", function () {

                beforeEach(
                    function () {
                        createProgram.$onInit();
                    }
                );

                it("should alias authenticationService.isAdmin", function () {
                    expect(createProgram.isAdmin).toEqual(locals.authenticationService.isAdmin);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
