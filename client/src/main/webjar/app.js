(function (angular) {
    "use strict";

    var module;

    /**
     * Configures the locationProvider.
     * @param $locationProvider
     */
    function configureLocationProvider(
        $locationProvider
    ) {
        $locationProvider.html5Mode(true);
    }

    /* Inject dependencies. */
    configureLocationProvider.$inject = [
        "$locationProvider"
    ];

    /**
     * Sets the default view URL.
     * @param $urlRouterProvider
     */
    function configureUrlProvider(
        $urlRouterProvider
    ) {
        $urlRouterProvider.otherwise(
            "/views/map?time=" + Date.now()
        );
    }

    /* Inject dependencies. */
    configureUrlProvider.$inject = [
        "$urlRouterProvider"
    ];

    /**
     * Configures the angular-material color theme.
     * @param $mdThemingProvider
     */
    function configureMdTheme($mdThemingProvider) {
        $mdThemingProvider.theme(
            "default"
        ).primaryPalette(
            "deep-purple"
        ).accentPalette(
            "amber"
        );
    }

    /* Inject dependencies. */
    configureMdTheme.$inject = [
        "$mdThemingProvider"
    ];

    /* Create module. */
    module = angular.module(
        "healthBam",
        [
            "ngAnimate",
            "ngAria",
            "ngMaterial",
            "ngMessages",
            "ngResource",
            "ui.router",
            "healthBam.authentication",
            "healthBam.main",
            "healthBam.map",
            "healthBam.programDetails",
            "healthBam.organizationList",
            "healthBam.organizationDetails"
        ]
    );

    /* Load configuration. */
    module.config(configureLocationProvider);
    module.config(configureUrlProvider);
    module.config(configureMdTheme);

}(window.angular));
