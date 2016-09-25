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
                Program;

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
                            Program = $injector.get("Program");
                        }
                    );

                    /* Set bindings. */
                    bindings.program = new Program();

                    /* Get the programFormDialog controller. */
                    programFormDialog = $controller(
                        "ProgramFormDialogController",
                        locals,
                        bindings
                    );
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

                it("should cancel dialog with program", function () {

                    var actual,
                        expected = {
                            x: "stuff"
                        };

                    spyOn(locals.$mdDialog, "hide");

                    locals.$mdDialog.hide.and.returnValue(expected);

                    actual = programFormDialog.save();

                    expect(locals.$mdDialog.hide).toHaveBeenCalledWith(
                        programFormDialog.program
                    );

                    expect(actual).toEqual(expected);
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
