package org.hmhb.config;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ConfigService}.
 */
@Service
public class DefaultConfigService implements ConfigService {

    private final PublicConfig publicConfig;
    private final PrivateConfig privateConfig;

    /**
     * An injectable constructor.
     *
     * @param environment the {@link Environment} to get config from
     */
    @Autowired
    public DefaultConfigService(
            @Nonnull Environment environment
    ) {
        requireNonNull(environment, "environment cannot be null");

        this.publicConfig = new PublicConfig(
                environment.getProperty("google.oauth.client.id"),
                environment.getProperty("hmhb.url.prefix")
        );

        this.privateConfig = new PrivateConfig(
                environment.getProperty("google.oauth.client.secret"),
                environment.getProperty("hmhb.jwt.domain"),
                environment.getProperty("hmhb.jwt.secret")
        );
    }

    @Override
    public PublicConfig getPublicConfig() {
        return publicConfig;
    }

    @Override
    public PrivateConfig getPrivateConfig() {
        return privateConfig;
    }

}
