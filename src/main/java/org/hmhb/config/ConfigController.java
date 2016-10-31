package org.hmhb.config;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

/**
 * Spring MVC {@link Controller} for serving our our common configuration
 * between the client and server in a javascript file (so the client can
 * load this synchronously and use the data in angular's config phase).
 */
@RestController
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    private final HttpServletResponse response;
    private final ConfigService configService;
    private final ObjectMapper objectMapper;

    /**
     * An injectable constructor.
     *
     * @param response the {@link HttpServletResponse} to turn off caching
     * @param configService the {@link ConfigService} for config
     * @param objectMapper the {@link ObjectMapper} to convert our config to
     *                     JSON for the client
     */
    @Autowired
    public ConfigController(
            @Nonnull HttpServletResponse response,
            @Nonnull ConfigService configService,
            @Nonnull ObjectMapper objectMapper
    ) {
        this.response = requireNonNull(response, "response cannot be null");
        this.configService = requireNonNull(configService, "configService cannot be null");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper cannot be null");
    }

    /**
     * Serves our single page app for any sub-views.
     *
     * @return our single page app's index.html
     * @throws JsonProcessingException if the {@link ObjectMapper} failed to
     * serialize the config
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/config.js"
    )
    public String getConfigJs() throws JsonProcessingException {
        LOGGER.debug("getConfigJs called");

        /*
         * We don't want the browser to treat this like a normal javascript
         * file.  Instead, we want to disable cache for this because config
         * is expected to change.  As for max-age vs. no-cache, it looks
         * like max-age is the better one to use:
         * http://stackoverflow.com/questions/1046966/whats-the-difference-between-cache-control-max-age-0-and-no-cache
         */
        response.setHeader("Cache-Control", "max-age=0, must-revalidate");

        return "(function (angular) {\n"
                + "    \"use strict\";\n"
                + "\n"
                + "    var module = angular.module(\"healthBam.config\", []);\n"
                + "\n"
                + "    function getConfig() {\n"
                + "        return " + objectMapper.writeValueAsString(configService.getPublicConfig()) + ";\n"
                + "    }\n"
                + "\n"
                + "    module.constant(\"getConfig\", getConfig);\n"
                + "\n"
                + "}(window.angular));\n";
    }

}
