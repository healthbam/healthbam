package org.hmhb.geocode;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultGoogleGeocodeClient implements GoogleGeocodeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGoogleGeocodeClient.class);

    private final GeoApiContext context;

    public DefaultGoogleGeocodeClient() {
        // TODO - I created a different unrestricted key for this; figure out the right way later
        this.context = new GeoApiContext().setApiKey("AIzaSyCZWf5alpEBF39ae49x1KIaDuMA8JiSa4o");
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
