(function (angular) {
    "use strict";

    angular.module(
        "healthBam.userList",
        [
            "ngMaterial",
            "ui.router",
            "healthBam.authentication",
            "healthBam.errorHandling",
            "healthBam.user",
            "healthBam.userManagement"
        ]
    );

}(window.angular));
