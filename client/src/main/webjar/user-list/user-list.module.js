(function (angular) {
    "use strict";

    angular.module(
        "healthBam.userList",
        [
            "ngMaterial",
            "ui.router",
            "healthBam.authentication",
            "healthBam.errorHandling",
            "healthBam.createUser",
            "healthBam.user",
            "healthBam.userManagement"
        ]
    );

}(window.angular));
