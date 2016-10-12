package org.hmhb.config;

import org.hmhb.https.PreferHttpsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {

    @Bean
    public FilterRegistrationBean provideHttpsFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PreferHttpsFilter());
        registration.setOrder(100);
        return registration;
    }

}
