(function (angular, jasmine, beforeEach, describe, it, spyOn) {
    "use strict";

    /* All of the healthBam.countiesField module's tests. */
    describe("healthBam.countiesField module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.countiesField");
            }
        );

        describe("CountiesFieldController", function () {

            var countiesField,
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
                            locals.$log = $injector.get("$log");
                            locals.County = $injector.get("County");
                        }
                    );

                    /* Set up bound properties. */
                    bindings.counties = [];

                    /* Get the controller for the countiesField component. */
                    countiesField = $componentController(
                        "healthBamCountiesField",
                        locals,
                        bindings
                    );
                }
            );

            it("should exist", function () {
                expect(countiesField).toEqual(jasmine.any(Object));
            });

            describe("$onInit", function () {

                it("should log loading debug message", function () {
                    spyOn(locals.$log, "debug");
                    countiesField.$onInit();
                    expect(locals.$log.debug).toHaveBeenCalledWith(
                        jasmine.any(String),
                        countiesField
                    );
                });

            });

            describe("findMatches", function () {

                beforeEach(
                    function () {
                        countiesField.$onInit();
                    }
                );

                it("should be exposed", function () {
                    expect(countiesField.findMatches).toEqual(jasmine.any(Function));
                });

                it("should call the server for County suggestions and return the promise", function () {

                    var actual,
                        expected,
                        mockCounties,
                        searchText;

                    searchText = "test-search";

                    expected = {
                        mock: "promise"
                    };

                    mockCounties = {
                        $promise: expected
                    };

                    spyOn(locals.County, "query");
                    locals.County.query.and.returnValue(mockCounties);

                    actual = countiesField.findMatches(searchText);

                    expect(actual).toEqual(expected);
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it, window.spyOn));
