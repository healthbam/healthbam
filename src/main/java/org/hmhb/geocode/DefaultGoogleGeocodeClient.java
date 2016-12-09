package org.hmhb.geocode;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link GoogleGeocodeClient}.
 */
@Service
public class DefaultGoogleGeocodeClient implements GoogleGeocodeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGoogleGeocodeClient.class);

    private final GeoApiContext context;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} for config
     */
    @Autowired
    public DefaultGoogleGeocodeClient(
            @Nonnull ConfigService configService
    ) {
        LOGGER.debug("constructed");
        requireNonNull(configService, "configService cannot be null");
        String googleMapsApiKey = configService.getPublicConfig().getGoogleGeocodeClientId();
        this.context = new GeoApiContext().setApiKey(googleMapsApiKey);
    }

    @Timed
    @Override
    public GeocodingResult[] geocode(
            @Nonnull String address
    ) throws Exception {
        LOGGER.debug("geocode called: address={}", address);
        requireNonNull(address, "address cannot be null");
        return GeocodingApi.geocode(context, address).await();
    }

}
