package org.hmhb.geocode;

import javax.annotation.Nonnull;

import java.util.Locale;

import com.google.maps.model.GeocodingResult;
import org.hmhb.program.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class GoogleGeocodeService implements GeocodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleGeocodeService.class);

    private final GoogleGeocodeClient googleGeocodeClient;

    @Autowired
    public GoogleGeocodeService(
            @Nonnull GoogleGeocodeClient googleGeocodeClient
    ) {
        this.googleGeocodeClient = requireNonNull(googleGeocodeClient, "googleGeocodeClient cannot be null");
    }

    private String formatAddress(
            Program program
    ) {
        return String.format(
                "%s %s, %s %s",
                program.getStreetAddress(),
                program.getCity(),
                program.getState(),
                program.getZipCode()
        );
    }

    @Override
    public String getLngLat(
            @Nonnull Program program
    ) {
        LOGGER.debug("getLngLat called: program={}", program);
        requireNonNull(program, "program cannot be null");

        String address = formatAddress(program);

        GeocodingResult[] results;

        try {

            results = googleGeocodeClient.geocode(address);

        } catch (Exception e) {
            throw new RuntimeException("Failed to geocode address: " + address, e);
        }

        /* I have to do this instead of using toUrlValue() because the map expects "lng,lat" instead of "lat,lng". */
        return String.format(
                Locale.ENGLISH,
                "%.8f,%.8f",
                results[0].geometry.location.lng,
                results[0].geometry.location.lat
        );
    }

}
