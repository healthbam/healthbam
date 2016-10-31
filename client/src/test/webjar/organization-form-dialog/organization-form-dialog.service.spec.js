(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    describe("healthBam.organizationFormDialog module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organizationFormDialog");
            }
        );

        describe("organizationFormDialogService", function () {

            var organizationFormDialogService,
                $mdDialog,
                $mdMedia,
                organization,
                Organization,
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
                            organizationFormDialogService = $injector.get("organizationFormDialogService");
                            Organization = $injector.get("Organization");
                        }
                    );

                    $event = {
                        stuff: "here"
                    };

                    organization = new Organization();

                    dialogOptions = {
                        templateUrl: "organization-form-dialog.html",
                        controller: "OrganizationFormDialogController",
                        controllerAs: "organizationFormDialog",
                        targetEvent: $event,
                        escapeToClose: false,
                        bindToController: true,
                        locals: {
                            organization: organization
                        }
                    };
                }
            );

            describe("organizationFormDialogService.create", function () {

                it("should exist", function () {
                    expect(organizationFormDialogService.create).toEqual(
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

                    actual = organizationFormDialogService.create($event);

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

                    actual = organizationFormDialogService.create($event);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

            });

            describe("organizationFormDialogService.edit", function () {

                it("should exist", function () {
                    expect(organizationFormDialogService.edit).toEqual(
                        jasmine.any(Function)
                    );
                });

                it("should open not-fullscreen dialog on screens larger than small", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    organization.id = 12345;

                    dialogOptions.fullscreen = false;
                    $mdMedia.and.returnValue(true);
                    $mdDialog.show.and.returnValue(expected);

                    actual = organizationFormDialogService.edit($event, organization);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

                it("should open fullscreen dialog on screens small and smaller", function () {

                    var actual,
                        expected = {
                            my: "data"
                        };

                    organization.id = 12345;

                    dialogOptions.fullscreen = true;
                    $mdMedia.and.returnValue(false);
                    $mdDialog.show.and.returnValue(expected);

                    actual = organizationFormDialogService.edit($event, organization);

                    expect(actual).toEqual(expected);
                    expect($mdMedia).toHaveBeenCalledWith("gt-sm");
                    expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
