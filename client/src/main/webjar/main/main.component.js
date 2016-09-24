(function (angular) {
    "use strict";

    var module = angular.module("healthBam.main");

    /**
     * Controller for the healthBamMain component.
     * @param $log
     * @constructor
     */
    function MainController(
        $log
    ) {
        var main = this;

        /**
         * A function for Jessica to make a change to.
         */
        function jessica() {
            $log.debug("TODO");
        }

        /**
         * A function for KP to make a change to.
         */
        function kp() {
            $log.debug("TODO");
        }

        /**
         * A function for John to make a change to.
         */
        function john() {
            $log.debug("TODO");
        }

        /**
         * This function only exists to prevent everyone from modifying the same area in code.
         *
         * TODO - remove this once everyone has submitted a code change
         */
        function training() {
            jessica();
            kp();
            john();
        }

        /**
         * Initializes the controller.
         */
        function activate() {
            $log.debug("Main Controller loaded", main);

            training();
        }

        /* Run activate when component is loaded. */
        main.$onInit = activate;
    }

    /* Inject dependencies. */
    MainController.$inject = [
        "$log"
    ];

    /* Create healthBamMain component. */
    module.component(
        "healthBamMain",
        {
            templateUrl: "main.html",
            controller: MainController,
            controllerAs: "main"
        }
    );

}(window.angular));
