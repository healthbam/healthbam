package org.hmhb.config;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JaxbConfig.class);

    public JaxbConfig() {
        LOGGER.debug("registering moxy's JAXBContextFactory as the jaxb context factory");
        System.setProperty(
                "javax.xml.bind.context.factory",
                JAXBContextFactory.class.getName()
        );
    }

}
