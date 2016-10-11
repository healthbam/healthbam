(function (angular) {
    "use strict";

    var module = angular.module("healthBam.mapQueryForm");

    /**
     * Controller for the healthBamMapQueryForm component.
     * @param MapQuery
     * @param County
     * @param Organization
     * @param ProgramArea
     * @param errorHandlingService
     * @param $mdSidenav
     * @param $q
     * @param $state
     * @param $log
     * @constructor
     */
    function MapQueryFormController(
        MapQuery,
        County,
        Organization,
        ProgramArea,
        errorHandlingService,
        $mdSidenav,
        $q,
        $state,
        $log
    ) {

        var mapQueryForm = this;

        /**
         * Runs the search by sending the MapQuery to the server.
         */
        function search() {
            $log.debug("MapQueryForm.search called");
            $state.go(
                "map",
                {
                    mapQuery: mapQueryForm.mapQuery,
                    time: Date.now()
                }
            );

            $mdSidenav("healthBamSidenav").close();
        }

        /**
         * Handles failure loading Counties.
         * @param error
         * @returns rejected {Promise} of error.
         */
        function handleCountiesQueryError(error) {
            $log.debug("counties query error", error);
            errorHandlingService.handleError("Failed to retrieve Counties.");
            return $q.reject(error);
        }

        /**
         * Handles failure loading Organizations.
         * @param error
         * @returns rejected {Promise} of error.
         */
        function handleOrganizationsQueryError(error) {
            $log.debug("organizations query error", error);
            errorHandlingService.handleError("Failed to retrieve Organizations.");
            return $q.reject(error);
        }

        /**
         * Handles failure loading ProgramAreas.
         * @param error
         * @returns rejected {Promise} of error.
         */
        function handleProgramAreasQueryError(error) {
            $log.debug("program area query error", error);
            errorHandlingService.handleError("Failed to retrieve Program Areas.");
            return $q.reject(error);
        }

        /**
         * Loads the data required from the server.
         */
        function load() {

            $log.debug("MapQueryForm loading data from server");

            mapQueryForm.loading = true;

            mapQueryForm.counties = County.query();
            mapQueryForm.counties.$promise.catch(handleCountiesQueryError);

            mapQueryForm.organizations = Organization.query();
            mapQueryForm.organizations.$promise.catch(handleOrganizationsQueryError);

            mapQueryForm.programAreas = ProgramArea.query();
            mapQueryForm.programAreas.$promise.catch(handleProgramAreasQueryError);

            $q.all(
                [
                    mapQueryForm.counties.$promise,
                    mapQueryForm.organizations.$promise,
                    mapQueryForm.programAreas.$promise
                ]
            ).finally(
                function () {
                    mapQueryForm.loading = false;
                    $log.debug("MapQueryForm done loading from server");
                }
            );
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            load();

            mapQueryForm.mapQuery = new MapQuery(
                {
                    search: {}
                }
            );

            mapQueryForm.search = search;

            $log.debug("MapQueryForm Controller loaded", mapQueryForm);
        }

        /* Run activate when component is loaded. */
        mapQueryForm.$onInit = activate;
    }

    /* Inject dependencies. */
    MapQueryFormController.$inject = [
        "MapQuery",
        "County",
        "Organization",
        "ProgramArea",
        "errorHandlingService",
        "$mdSidenav",
        "$q",
        "$state",
        "$log"
    ];

    /* Create healthBamMapQueryForm component. */
    module.component(
        "healthBamMapQueryForm",
        {
            templateUrl: "map-query-form.html",
            controller: MapQueryFormController,
            controllerAs: "mapQueryForm"
        }
    );

}(window.angular));
