(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.programFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programFormDialog");
            }
        );

        describe("programFormDialogService", function () {

            var programFormDialogService,
                $mdDialog,
                $mdMedia,
                program,
                Program,
                dialogOptions,
                $event;

            beforeEach(
                function () {

                    /* Mock services. */
                    angular.mock.module(
                        function ($provide) {

                            $mdDialog = jasmine.createSpyObj(
                                "$mdDialog",
                                [
                                    "show"
                                ]
                            );

                            $mdMedia = jasmine.createSpy();

                            /* Provide mock services. */
                            $provide.factory(
                                "$mdDialog",
                                function () {
                                    return $mdDialog;
                                }
                            );

                            $provide.factory(
                                "$mdMedia",
                                function () {
                                    return $mdMedia;
                                }
                            );
                        }
                    );

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            programFormDialogService = $injector.get("programFormDialogService");
                            Program = $injector.get("Program");
                        }
                    );

                    $event = {
                        stuff: "here"
                    };

                    program = new Program(
                        {
                            countiesServed: [],
                            programAreas: [],
                            state: "GA"
                        }
                    );

                    dialogOptions = {
                        templateUrl: "program-form-dialog.html",
                        controller: "ProgramFormDialogController",
                        controllerAs: "programFormDialog",
                        targetEvent: $event,
                        escapeToClose: false,
                        bindToController: true,
                        locals: {
                            program: program
                        }
                    };
                }
            );

            describe("programFormDialogService.create", function () {

                it("should exist", function () {
                    expect(programFormDialogService.create).toEqual(
                        jasmine.any(Function)
                    );
                });

                it("should open not-fullscreen dialog on screens larger than small", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    dialogOptions.fullscreen = false;
                    $mdMedia.and.returnValue(true);
                    $mdDialog.show.and.returnValue(expected);

                    actual = programFormDialogService.create($event);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

                it("should open fullscreen dialog on screens small and smaller", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    dialogOptions.fullscreen = true;
                    $mdMedia.and.returnValue(false);
                    $mdDialog.show.and.returnValue(expected);

                    actual = programFormDialogService.create($event);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

            });

            describe("programFormDialogService.edit", function () {

                it("should exist", function () {
                    expect(programFormDialogService.edit).toEqual(
                        jasmine.any(Function)
                    );
                });

                it("should open not-fullscreen dialog on screens larger than small", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    program.id = 12345;

                    dialogOptions.fullscreen = false;
                    $mdMedia.and.returnValue(true);
                    $mdDialog.show.and.returnValue(expected);

                    actual = programFormDialogService.edit($event, program);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

                it("should open fullscreen dialog on screens small and smaller", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    program.id = 12345;

                    dialogOptions.fullscreen = true;
                    $mdMedia.and.returnValue(false);
                    $mdDialog.show.and.returnValue(expected);

                    actual = programFormDialogService.edit($event, program);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
