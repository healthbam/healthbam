(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.programArea module's tests. */
    describe("healthBam.programArea module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.programArea");
            }
        );

        describe("ProgramArea $resource", function () {

            var ProgramArea,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating an ProgramArea instance.
             * @param {number} id of the ProgramArea.
             * @returns ProgramArea instance.
             */
            function createProgramArea(id) {
                return new ProgramArea(
                    {
                        id: id
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/program-areas";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            ProgramArea = $injector.get("ProgramArea");
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
                    expect(ProgramArea.query).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return array on success", function () {

                    var actual,
                        expected = [],
                        promise;

                    expected.push(
                        createProgramArea(1)
                    );
                    expected.push(
                        createProgramArea(2)
                    );
                    expected.push(
                        createProgramArea(3)
                    );

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = ProgramArea.query();

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

                    actual = ProgramArea.query();

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
                    expect(ProgramArea.get).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return ProgramArea on success", function () {

                    var actual,
                        expected,
                        promise,
                        programAreaId = 789;

                    expected = createProgramArea(programAreaId);

                    $httpBackend.expectGET(
                        endpoint + "/" + programAreaId
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = ProgramArea.get(
                        {
                            id: programAreaId
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
                        programAreaId = 789;

                    $httpBackend.expectGET(
                        endpoint + "/" + programAreaId
                    ).respond(
                        418
                    );

                    actual = ProgramArea.get(
                        {
                            id: programAreaId
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
