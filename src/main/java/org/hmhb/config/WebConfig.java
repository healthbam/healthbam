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

@Configuration
public class WebConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("WebConfig.AuthFilter");
    private final JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    public WebConfig(
            @Nonnull JwtAuthenticationService jwtAuthenticationService
    ) {
        this.jwtAuthenticationService = requireNonNull(jwtAuthenticationService, "jwtAuthenticationService cannot be null");
    }

    @Bean
    public Filter provideAuthenticationFilter() {
        return new AuthenticationFilter(jwtAuthenticationService);
    }

}
