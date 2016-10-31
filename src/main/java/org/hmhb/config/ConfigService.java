package org.hmhb.config;

/**
 * Service consolidate where to get config.
 */
public interface ConfigService {

    /**
     * Returns the config relevant and allowed to be shared with the client.
     *
     * @return the client-sharable config
     */
    PublicConfig getPublicConfig();

    /**
     * Returns the config too sensitive to share with the client, or irrelevant
     * to the client.
     *
     * @return the non-client-sharable config
     */
    PrivateConfig getPrivateConfig();

}
