(function (angular) {
    "use strict";

    angular.module(
        "healthBam.programDetails",
        [
            "ngMaterial",
            "ui.router",
            "ngMap",
            "healthBam.templates",
            "healthBam.main",
            "healthBam.mapQuery",
            "healthBam.errorHandling",
            "healthBam.countiesField",
            "healthBam.mapConfig"
        ]
    );

}(window.angular));
