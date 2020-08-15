(function (angular) {
    "use strict";

    var module = angular.module("healthBam.reportList");

    /**
     * Controller for the healthBam reportList component.
     * @param authenticationService
     * @param errorHandlingService
     * @param $window
     * @param $state
     * @param $log
     * @constructor
     */
    function ReportListController(
        authenticationService,
        errorHandlingService,
        $window,
        $state,
        $log
    ) {
        var reportList = this;

        /**
         * Navigates to the map state.
         */
        function viewMap() {
            $state.go(
                "map"
            );
        }

        /**
         * Adds the current user's authentication token as a query parameter to the URL.
         * @param url to which to add the token.
         * @returns {string} url with token query parameter.
         */
        function addAuthenticationToken(url) {
            return url + "?token=" + authenticationService.getToken();
        }

        /**
         * Downloads a file by opening the provided URL in a new tab.
         * @param url - for file to download.
         */
        function downloadFile(url) {
            $window.open(
                addAuthenticationToken(url),
                "_blank"
            );
        }

        /**
         * Downloads the program report.
         */
        function downloadProgramReport() {
            downloadFile("api/programs/csv");
        }

        /**
         * Downloads the user report.
         */
        function downloadUserReport() {
            downloadFile("api/users/csv");
        }

        /**
         * Initializes the controller.
         */
        function activate() {

            reportList.downloadProgramReport = downloadProgramReport;
            reportList.downloadUserReport = downloadUserReport;

            if (!authenticationService.isAdmin()) {
                errorHandlingService.handleError("Insufficient privileges to view reports.");
                viewMap();
            }

            $log.debug("ReportList Controller loaded", reportList);
        }

        /* Run activate when component is loaded. */
        reportList.$onInit = activate;
    }

    /* Inject dependencies. */
    ReportListController.$inject = [
        "authenticationService",
        "errorHandlingService",
        "$window",
        "$state",
        "$log"
    ];

    /* Create healthBamReportList component. */
    module.component(
        "healthBamReportList",
        {
            templateUrl: "/report-list.html",
            controller: ReportListController,
            controllerAs: "reportList"
        }
    );

}(window.angular));
