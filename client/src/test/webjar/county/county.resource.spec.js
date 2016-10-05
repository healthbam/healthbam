(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.county module's tests. */
    describe("healthBam.county module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.county");
            }
        );

        describe("County $resource", function () {

            var County,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating an County instance.
             * @param {number} id of the County.
             * @returns County instance.
             */
            function createCounty(id) {
                return new County(
                    {
                        id: id
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/counties";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            County = $injector.get("County");
                            $httpBackend = $injector.get("$httpBackend");
                        }
                    );

                    /* Create handlers spy for promise tracking. */
                    handlers = jasmine.createSpyObj(
                        "handlers",
                        [
                            "onSuccess",
                            "onError"
                        ]
                    );

                }
            );

            describe("query", function () {

                it("should be exposed", function () {
                    expect(County.query).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return array on success", function () {

                    var actual,
                        expected = [],
                        promise;

                    expected.push(
                        createCounty(1)
                    );
                    expected.push(
                        createCounty(2)
                    );
                    expected.push(
                        createCounty(3)
                    );

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = County.query();

                    promise = actual.$promise;

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        actual
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );

                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should GET to endpoint and call catch on error response", function () {

                    var actual,
                        promise;

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        418
                    );

                    actual = County.query();

                    promise = actual.$promise;

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

            });

            describe("get", function () {

                it("should be exposed", function () {
                    expect(County.get).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return County on success", function () {

                    var actual,
                        expected,
                        promise,
                        countyId = 789;

                    expected = createCounty(countyId);

                    $httpBackend.expectGET(
                        endpoint + "/" + countyId
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = County.get(
                        {
                            id: countyId
                        }
                    );

                    promise = actual.$promise;

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        actual
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );

                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should GET to endpoint and call catch on error response", function () {

                    var actual,
                        promise,
                        countyId = 789;

                    $httpBackend.expectGET(
                        endpoint + "/" + countyId
                    ).respond(
                        418
                    );

                    actual = County.get(
                        {
                            id: countyId
                        }
                    );

                    promise = actual.$promise;

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

            });

        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
