package org.hmhb.config;

import org.hmhb.https.PreferHttpsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring {@link Configuration} for https forwarding.
 */
@Configuration
public class HttpsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsConfig.class);

    /**
     * Provides the {@link FilterRegistrationBean} with the
     * {@link PreferHttpsFilter} registered in it.
     *
     * @return the {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean provideHttpsFilter() {
        LOGGER.debug("providing https filter registration");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PreferHttpsFilter());
        registration.setOrder(100);
        return registration;
    }

}
