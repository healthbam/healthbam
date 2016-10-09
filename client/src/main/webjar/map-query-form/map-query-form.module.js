(function (angular) {
    "use strict";

    angular.module(
        "healthBam.mapQueryForm",
        [
            "ngMaterial",
            "ui.router",
            "healthBam.templates",
            "healthBam.mapQuery",
            "healthBam.errorHandling",
            "healthBam.county",
            "healthBam.program",
            "healthBam.programArea",
            "healthBam.organization"
        ]
    );

}(window.angular));
