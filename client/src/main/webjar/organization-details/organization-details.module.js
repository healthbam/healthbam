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
            "healthBam.mapQuery",
            "healthBam.errorHandling",
            "healthBam.mapConfig"
     //       "healthBam.organizationFormDialog"
        ]
    );

}(window.angular));
