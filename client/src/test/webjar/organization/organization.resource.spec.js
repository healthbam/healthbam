(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam.organization module's tests. */
    describe("healthBam.organization module", function () {

        beforeEach(
            function () {
                /* Load the module to test. */
                angular.mock.module("healthBam.organization");
            }
        );

        describe("Organization $resource", function () {

            var Organization,
                $httpBackend,
                endpoint,
                handlers;

            /**
             * Helper function for creating an Organization instance.
             * @param {number} id of the Organization.
             * @returns Organization instance.
             */
            function createOrganization(id) {
                return new Organization(
                    {
                        id: id
                    }
                );
            }

            beforeEach(
                function () {

                    endpoint = "api/organizations";

                    /* Inject dependencies. */
                    angular.mock.inject(
                        function ($injector) {
                            Organization = $injector.get("Organization");
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
                    expect(Organization.query).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return array on success", function () {

                    var actual,
                        expected = [],
                        promise;

                    expected.push(
                        createOrganization(1)
                    );
                    expected.push(
                        createOrganization(2)
                    );
                    expected.push(
                        createOrganization(3)
                    );

                    $httpBackend.expectGET(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = Organization.query();

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

                    actual = Organization.query();

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
                    expect(Organization.get).toEqual(jasmine.any(Function));
                });

                it("should GET to endpoint and return Organization on success", function () {

                    var actual,
                        expected,
                        promise,
                        organizationId = 789;

                    expected = createOrganization(organizationId);

                    $httpBackend.expectGET(
                        endpoint + "/" + organizationId
                    ).respond(
                        angular.toJson(expected)
                    );

                    actual = Organization.get(
                        {
                            id: organizationId
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
                        organizationId = 789;

                    $httpBackend.expectGET(
                        endpoint + "/" + organizationId
                    ).respond(
                        418
                    );

                    actual = Organization.get(
                        {
                            id: organizationId
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

                    var organization = createOrganization(789);

                    expect(organization.$save).toEqual(jasmine.any(Function));
                });

                it("should POST new Organization to endpoint and update it on success", function () {

                    var organization,
                        expected,
                        promise;

                    organization = new Organization(
                        {
                            name: "test-org"
                        }
                    );

                    expected = angular.copy(organization);
                    expected.id = 789;

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = organization.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        organization
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST new Organization to endpoint and call catch on error response", function () {

                    var organization,
                        promise;

                    organization = new Organization(
                        {
                            name: "test-org"
                        }
                    );

                    $httpBackend.expectPOST(
                        endpoint
                    ).respond(
                        418
                    );

                    promise = organization.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

                it("should POST existing Organization to endpoint and update it on success", function () {

                    var organization,
                        organizationId = 789,
                        expected,
                        promise;

                    organization = createOrganization(organizationId);

                    expected = angular.copy(organization);
                    expected.name = "some-name-from-server";

                    $httpBackend.expectPOST(
                        endpoint + "/" + organizationId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = organization.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        organization
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should POST existing Organization to endpoint and call catch on error response", function () {

                    var organization,
                        organizationId = 789,
                        promise;

                    organization = createOrganization(organizationId);

                    $httpBackend.expectPOST(
                        endpoint + "/" + organizationId
                    ).respond(
                        418
                    );

                    promise = organization.$save();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(handlers.onSuccess).not.toHaveBeenCalled();
                    expect(handlers.onError).toHaveBeenCalled();
                });

            });

            describe("$delete", function () {

                it("should be exposed", function () {

                    var organization = createOrganization(789);

                    expect(organization.$delete).toEqual(jasmine.any(Function));
                });

                it("should DELETE existing Organization to endpoint and update it on success", function () {

                    var organization,
                        organizationId = 789,
                        expected,
                        promise;

                    organization = createOrganization(organizationId);

                    expected = angular.copy(organization);
                    expected.deleted = true;

                    $httpBackend.expectDELETE(
                        endpoint + "/" + organizationId
                    ).respond(
                        angular.toJson(expected)
                    );

                    promise = organization.$delete();

                    promise.then(handlers.onSuccess);
                    promise.catch(handlers.onError);

                    $httpBackend.flush();

                    expect(
                        organization
                    ).toEqual(
                        jasmine.objectContaining(expected)
                    );
                    expect(handlers.onSuccess).toHaveBeenCalled();
                    expect(handlers.onError).not.toHaveBeenCalled();
                });

                it("should DELETE existing Organization to endpoint and call catch on error response", function () {

                    var organization,
                        organizationId = 789,
                        promise;

                    organization = createOrganization(organizationId);

                    $httpBackend.expectDELETE(
                        endpoint + "/" + organizationId
                    ).respond(
                        418
                    );

                    promise = organization.$delete();

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
