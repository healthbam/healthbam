(function (angular) {
    "use strict";

    angular.module(
        "healthBam.main",
        [
            "ngMaterial",
            "ui.router",
            "ngMap",
            "healthBam.templates",
            "healthBam.toolbar",
            "healthBam.sidenav"
        ]
    );

}(window.angular));
