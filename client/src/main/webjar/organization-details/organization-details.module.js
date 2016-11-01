(function (angular) {
    "use strict";

    angular.module(
        "healthBam.organizationDetails",
        [
            "ngMaterial",
            "ui.router",
            "ngMap",
            "healthBam.templates",
            "healthBam.main",
            "healthBam.errorHandling",
            "healthBam.mapConfig",
            "healthBam.authentication",
            "healthBam.organizationFormDialog",
            "healthBam.organizationCard"
        ]
    );

}(window.angular));
