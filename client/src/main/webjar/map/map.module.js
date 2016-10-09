(function (angular) {
    "use strict";

    angular.module(
        "healthBam.map",
        [
            "ngMaterial",
            "ui.router",
            "ngMap",
            "healthBam.templates",
            "healthBam.main",
            "healthBam.createProgram",
            "healthBam.program",
            "healthBam.mapQuery"
        ]
    );

}(window.angular));
