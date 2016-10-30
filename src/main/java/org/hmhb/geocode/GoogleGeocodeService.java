package org.hmhb.geocode;

import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.Locale;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import org.hmhb.program.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of {@link GeocodeService} that uses google's geocode API.
 */
@Service
public class GoogleGeocodeService implements GeocodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleGeocodeService.class);

    private final GoogleGeocodeClient googleGeocodeClient;

    /**
     * An injectable constructor.
     *
     * @param googleGeocodeClient the {@link GoogleGeocodeClient} to look up
     *                            geo information
     */
    @Autowired
    public GoogleGeocodeService(
            @Nonnull GoogleGeocodeClient googleGeocodeClient
    ) {
        this.googleGeocodeClient = requireNonNull(googleGeocodeClient, "googleGeocodeClient cannot be null");
    }

    /**
     * Format the {@link Program}'s address information into a single string
     * for google.
     *
     * @param program the {@link Program} with the address info
     * @return the single line of address information for google
     */
    private String formatAddress(
            Program program
    ) {
        String zipCode = program.getZipCode();

        if (zipCode == null) {
            zipCode = "";
        }

        return String.format(
                "%s %s, %s %s",
                program.getStreetAddress(),
                program.getCity(),
                program.getState(),
                zipCode
        );
    }

    @Timed
    @Override
    public LocationInfo getLocationInfo(
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

        LocationInfo info = new LocationInfo();

        for (AddressComponent component : results[0].addressComponents) {
            for (AddressComponentType type : component.types) {
                if (AddressComponentType.POSTAL_CODE.equals(type)) {
                    info.setZipCode(component.longName);
                }
            }
        }

        /* I have to do this instead of using toUrlValue() because the map expects "lng,lat" instead of "lat,lng". */
        info.setLngLat(
                String.format(
                        Locale.ENGLISH,
                        "%.8f,%.8f",
                        results[0].geometry.location.lng,
                        results[0].geometry.location.lat
                )
        );

        return info;
    }

}
