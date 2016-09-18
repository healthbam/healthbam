module.exports = function (config) {
    "use strict";

    config.set(
        {
            basePath: "../../..",
            frameworks: [
                "jasmine"
            ],
            files: [
                "node_modules/angular/angular.js",
                "node_modules/angular-animate/angular-animate.js",
                "node_modules/angular-aria/angular-aria.js",
                "node_modules/angular-messages/angular-messages.js",
                "node_modules/angular-mocks/angular-mocks.js",
                "node_modules/angular-resource/angular-resource.js",
                "node_modules/angular-ui-router/release/angular-ui-router.js",
                "node_modules/angular-material/angular-material.js",
                "node_modules/ngmap/build/scripts/ng-map.js",
                "src/main/**/*.module.js",
                "src/main/**/*.js",
                "src/test/**/*.spec.js"
            ],
            reporters: [
                "progress",
                "kjhtml",
                "junit"
            ],
            junitReporter: {
                outputDir: "build",
                useBrowserName: true
            },
            browsers: [
                "PhantomJS"
            ],
            logLevel: "debug"
        }
    );
};
