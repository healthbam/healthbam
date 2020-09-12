(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.userFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.userFormDialog");
            }
        );

        describe("userFormDialogService", function () {

            var userFormDialogService,
                $mdDialog,
                $mdMedia,
                user,
                User,
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
                            userFormDialogService = $injector.get("userFormDialogService");
                            User = $injector.get("User");
                        }
                    );

                    $event = {
                        stuff: "here"
                    };

                    user = new User(
                        {
                            admin: true
                        }
                    );

                    dialogOptions = {
                        templateUrl: "/user-form-dialog.html",
                        controller: "UserFormDialogController",
                        controllerAs: "userFormDialog",
                        targetEvent: $event,
                        escapeToClose: false,
                        bindToController: true,
                        locals: {
                            user: user
                        }
                    };
                }
            );

            describe("userFormDialogService.create", function () {

                it("should exist", function () {
                    expect(userFormDialogService.create).toEqual(
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

                    actual = userFormDialogService.create($event);

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

                    actual = userFormDialogService.create($event);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
