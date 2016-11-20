(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.createUser module's tests. */
    describe("healthBam.createUser module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.createUser");
            }
        );

        describe("CreateUserController", function () {

            var createUser,
                $componentController,
                $q,
                $rootScope,
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
                            locals.userFormDialogService = $injector.get("userFormDialogService");
                            locals.$log = $injector.get("$log");
                            locals.$state = $injector.get("$state");
                            $q = $injector.get("$q");
                            $rootScope = $injector.get("$rootScope");
                        }
                    );

                    /* Get the controller for the createUser component. */
                    createUser = $componentController(
                        "healthBamCreateUser",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(createUser).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    createUser.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        createUser
                    );
                });

            });

            describe("openUserForm", function () {

                beforeEach(
                    function () {
                        createUser.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(createUser.openUserForm).toEqual(jasmine.any(Function));
                });

                it("should open dialog and return its promise", function () {

                    var actual,
                        expected,
                        event;

                    spyOn(locals.$state, "reload");

                    expected = {
                        fake: "promise"
                    };

                    event = {
                        mock: "clickEvent"
                    };

                    spyOn(locals.userFormDialogService, "create");
                    locals.userFormDialogService.create.and.returnValue(
                        $q.when(expected)
                    );

                    createUser.openUserForm(event).then(
                        function (data) {
                            actual = data;
                        }
                    );

                    expect(locals.userFormDialogService.create).toHaveBeenCalledWith(event);

                    /* Flush the promise. */
                    $rootScope.$digest();

                    expect(actual).toEqual(expected);
                    expect(locals.$state.reload).toHaveBeenCalled();
                });

            });

            describe("isAdmin", function () {

                beforeEach(
                    function () {
                        createUser.$onInit();
                    }
                );

                it("should alias authenticationService.isAdmin", function () {
                    expect(createUser.isAdmin).toEqual(locals.authenticationService.isAdmin);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
