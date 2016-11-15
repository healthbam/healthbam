(function (angular) {
    "use strict";

    angular.module(
        "healthBam.userManagement",
        [
            "ngMaterial",
            "ui.router",
            "healthBam.authentication",
            "healthBam.errorHandling",
            "healthBam.user"
        ]
    );

}(window.angular));
