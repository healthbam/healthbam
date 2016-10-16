(function (angular) {
    "use strict";

    var module = angular.module("healthBam.countiesField");

    /**
     * Controller for the healthBamCountiesField component.
     * @param County
     * @param $log
     * @constructor
     */
    function CountiesFieldController(
        County,
        $log
    ) {

        var countiesField = this;

        /**
         * Searches for counties that match the provided searchText.
         * @param searchText {string} - partial county name to search.
         * @returns {promise} - the promise resolved with the results to suggest.
         */
        function findMatches(searchText) {
            return County.query(
                {
                    name: searchText
                }
            ).$promise;
        }

        /**
         * Initializes the controller.
         */
        function activate() {
            countiesField.findMatches = findMatches;
            $log.debug("CountiesField Controller loaded", countiesField);
        }

        /* Run activate when component is loaded. */
        countiesField.$onInit = activate;
    }

    /* Inject dependencies. */
    CountiesFieldController.$inject = [
        "County",
        "$log"
    ];

    /* Create healthBamCountiesField component. */
    module.component(
        "healthBamCountiesField",
        {
            templateUrl: "counties-field.html",
            controller: CountiesFieldController,
            controllerAs: "countiesField",
            bindings: {
                counties: "<",
                readonly: "<?"
            }
        }
    );

}(window.angular));
