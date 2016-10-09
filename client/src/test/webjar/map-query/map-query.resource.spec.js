(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.mapQuery module's tests. */
    describe("healthBam.mapQuery module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.mapQuery");
            }
        );

        describe("MapQuery $resource", function () {

            var MapQuery,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating an MapQuery instance.
             * @returns MapQuery instance.
             */
            function createMapQuery() {
                return new MapQuery(
                    {
                        search: {
                            programArea: {
                                id: 12,
                                name: "Neonatal Care"
                            }
                        }
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/map-queries";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            MapQuery = $injector.get("MapQuery");
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

            describe("$save", function () {

                it("should be exposed", function () {

                    var mapQuery = createMapQuery();

                    expect(mapQuery.$save).toEqual(jasmine.any(Function));
                });

                it("should POST MapQuery to endpoint and update it with response", function () {

                    var mapQuery,
                        expected,
                        promise;

                    mapQuery = createMapQuery();

                    expected = angular.copy(mapQuery);
                    expected.result = {
                        programs: [],
                        mapLayerUrl: "some/url.kml"
                    };

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = mapQuery.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        mapQuery
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST MapQuery to endpoint and call catch on error response", function () {

                    var mapQuery,
                        promise;

                    mapQuery = createMapQuery();

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        418
                    );

                    promise = mapQuery.$save();

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
