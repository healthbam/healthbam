(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.program module's tests. */
    describe("healthBam.program module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.program");
            }
        );

        describe("Program $resource", function () {

            var Program,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating an Program instance.
             * @param {number} id of the Program.
             * @returns Program instance.
             */
            function createProgram(id) {
                return new Program(
                    {
                        id: id
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/programs";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            Program = $injector.get("Program");
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
                    expect(Program.query).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return array on success", function () {

                    var actual,
                        expected = [],
                        promise;

                    expected.push(
                        createProgram(1)
                    );
                    expected.push(
                        createProgram(2)
                    );
                    expected.push(
                        createProgram(3)
                    );

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = Program.query();

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

                    actual = Program.query();

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
                    expect(Program.get).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return Program on success", function () {

                    var actual,
                        expected,
                        promise,
                        programId = 789;

                    expected = createProgram(programId);

                    $httpBackend.expectGET(
                        endpoint + "/" + programId
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = Program.get(
                        {
                            id: programId
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
                        programId = 789;

                    $httpBackend.expectGET(
                        endpoint + "/" + programId
                    ).respond(
                        418
                    );

                    actual = Program.get(
                        {
                            id: programId
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

            describe("$save", function () {

                it("should be exposed", function () {

                    var program = createProgram(789);

                    expect(program.$save).toEqual(jasmine.any(Function));
                });

                it("should POST new Program to endpoint and update it on success", function () {

                    var program,
                        expected,
                        promise;

                    program = new Program(
                        {
                            name: "test-org"
                        }
                    );

                    expected = angular.copy(program);
                    expected.id = 789;

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = program.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        program
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST new Program to endpoint and call catch on error response", function () {

                    var program,
                        promise;

                    program = new Program(
                        {
                            name: "test-org"
                        }
                    );

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        418
                    );

                    promise = program.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

                it("should POST existing Program to endpoint and update it on success", function () {

                    var program,
                        programId = 789,
                        expected,
                        promise;

                    program = createProgram(programId);

                    expected = angular.copy(program);
                    expected.name = "some-name-from-server";

                    $httpBackend.expectPOST(
                        endpoint + "/" + programId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = program.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        program
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST existing Program to endpoint and call catch on error response", function () {

                    var program,
                        programId = 789,
                        promise;

                    program = createProgram(programId);

                    $httpBackend.expectPOST(
                        endpoint + "/" + programId
                    ).respond(
                        418
                    );

                    promise = program.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

            });

            describe("$delete", function () {

                it("should be exposed", function () {

                    var program = createProgram(789);

                    expect(program.$delete).toEqual(jasmine.any(Function));
                });

                it("should DELETE existing Program to endpoint and update it on success", function () {

                    var program,
                        programId = 789,
                        expected,
                        promise;

                    program = createProgram(programId);

                    expected = angular.copy(program);
                    expected.deleted = true;

                    $httpBackend.expectDELETE(
                        endpoint + "/" + programId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = program.$delete();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        program
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should DELETE existing Program to endpoint and call catch on error response", function () {

                    var program,
                        programId = 789,
                        promise;

                    program = createProgram(programId);

                    $httpBackend.expectDELETE(
                        endpoint + "/" + programId
                    ).respond(
                        418
                    );

                    promise = program.$delete();

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
