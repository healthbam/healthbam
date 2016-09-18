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

        $log.debug("Main Controller loaded", main);
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
