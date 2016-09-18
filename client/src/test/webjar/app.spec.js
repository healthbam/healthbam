(function (angular, jasmine, beforeEach, describe, it) {
    "use strict";

    /* All of the healthBam module's tests. */
    describe("healthBam module", function () {

        it("should resolve dependencies and load", function () {
            angular.mock.module("healthBam");
        });

    });

}(window.angular, window.jasmine, window.beforeEach, window.describe, window.it));
