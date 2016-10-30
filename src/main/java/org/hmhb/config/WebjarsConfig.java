package org.hmhb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring {@link Configuration} for serving webjars from the classpath.
 */
@Configuration
public class WebjarsConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebjarsConfig.class);

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/",
    };

    /**
     * Registers mappings for our client code to be able to retrieve webjars.
     *
     * @param registry the {@link ResourceHandlerRegistry} to register mappings
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.debug("registering webjar mappings");

        super.addResourceHandlers(registry);

        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**")
                    .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        }

    }

}
