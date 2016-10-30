package org.hmhb.config;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Spring {@link Configuration} for JAXB for our KML code.
 */
@Configuration
public class JaxbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JaxbConfig.class);

    /**
     * A default constructor that will register moxy as the jaxb context
     * factory.
     */
    public JaxbConfig() {
        LOGGER.debug("registering moxy's JAXBContextFactory as the jaxb context factory");
        System.setProperty(
                "javax.xml.bind.context.factory",
                JAXBContextFactory.class.getName()
        );
    }

}
