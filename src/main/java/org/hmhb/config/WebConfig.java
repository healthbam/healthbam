package org.hmhb.config;

import javax.annotation.Nonnull;
import javax.servlet.Filter;

import org.hmhb.authentication.AuthenticationFilter;
import org.hmhb.authentication.JwtAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

/**
 * Spring {@link Configuration} for our authentication filter.
 */
@Configuration
public class WebConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    private final JwtAuthenticationService jwtAuthenticationService;

    /**
     * An injectable constructor.
     *
     * @param jwtAuthenticationService the {@link JwtAuthenticationService} to
     *                                 authenticate the http request
     */
    @Autowired
    public WebConfig(
            @Nonnull JwtAuthenticationService jwtAuthenticationService
    ) {
        LOGGER.debug("constructed");
        requireNonNull(jwtAuthenticationService, "jwtAuthenticationService cannot be null");
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    /**
     * Provides the {@link AuthenticationFilter} to authenticate http requests.
     *
     * @return the {@link AuthenticationFilter}
     */
    @Bean
    public Filter provideAuthenticationFilter() {
        LOGGER.debug("providing authentication filter");
        return new AuthenticationFilter(jwtAuthenticationService);
    }

}
