(function (angular) {
    "use strict";

    angular.module(
        "healthBam.userAccountActions",
        [
            "ngMaterial",
            "ui.router",
            "healthBam.authentication",
            "healthBam.userAccountDetails",
            "healthBam.userManagement"
        ]
    );

}(window.angular));
