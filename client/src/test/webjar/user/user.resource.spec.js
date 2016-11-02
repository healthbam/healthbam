(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.user module's tests. */
    describe("healthBam.user module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.user");
            }
        );

        describe("User $resource", function () {

            var User,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating a User instance.
             * @param {number} id of the User.
             * @returns User instance.
             */
            function createUser(id) {
                return new User(
                    {
                        id: id
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/users";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            User = $injector.get("User");
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
                    expect(User.query).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return array on success", function () {

                    var actual,
                        expected = [],
                        promise;

                    expected.push(
                        createUser(1)
                    );
                    expected.push(
                        createUser(2)
                    );
                    expected.push(
                        createUser(3)
                    );

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = User.query();

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

                    actual = User.query();

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
                    expect(User.get).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return User on success", function () {

                    var actual,
                        expected,
                        promise,
                        userId = 789;

                    expected = createUser(userId);

                    $httpBackend.expectGET(
                        endpoint + "/" + userId
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = User.get(
                        {
                            id: userId
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
                        userId = 789;

                    $httpBackend.expectGET(
                        endpoint + "/" + userId
                    ).respond(
                        418
                    );

                    actual = User.get(
                        {
                            id: userId
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

                    var user = createUser(789);

                    expect(user.$save).toEqual(jasmine.any(Function));
                });

                it("should POST new User to endpoint and update it on success", function () {

                    var user,
                        expected,
                        promise;

                    user = new User(
                        {
                            email: "test@mailinator.com"
                        }
                    );

                    expected = angular.copy(user);
                    expected.id = 789;

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = user.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        user
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST new User to endpoint and call catch on error response", function () {

                    var user,
                        promise;

                    user = new User(
                        {
                            email: "test@mailinator.com"
                        }
                    );

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        418
                    );

                    promise = user.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

                it("should POST existing User to endpoint and update it on success", function () {

                    var user,
                        userId = 789,
                        expected,
                        promise;

                    user = createUser(userId);

                    expected = angular.copy(user);
                    expected.email = "old.test@mailinator.com";

                    $httpBackend.expectPOST(
                        endpoint + "/" + userId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = user.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        user
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST existing User to endpoint and call catch on error response", function () {

                    var user,
                        userId = 789,
                        promise;

                    user = createUser(userId);

                    $httpBackend.expectPOST(
                        endpoint + "/" + userId
                    ).respond(
                        418
                    );

                    promise = user.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

            });

            describe("$delete", function () {

                it("should be exposed", function () {

                    var user = createUser(789);

                    expect(user.$delete).toEqual(jasmine.any(Function));
                });

                it("should DELETE existing User to endpoint and update it on success", function () {

                    var user,
                        userId = 789,
                        expected,
                        promise;

                    user = createUser(userId);

                    expected = angular.copy(user);

                    $httpBackend.expectDELETE(
                        endpoint + "/" + userId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = user.$delete();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        user
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should DELETE existing User to endpoint and call catch on error response", function () {

                    var user,
                        userId = 789,
                        promise;

                    user = createUser(userId);

                    $httpBackend.expectDELETE(
                        endpoint + "/" + userId
                    ).respond(
                        418
                    );

                    promise = user.$delete();

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
