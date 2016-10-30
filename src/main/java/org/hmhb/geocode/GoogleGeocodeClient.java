package org.hmhb.geocode;

import javax.annotation.Nonnull;

import com.google.maps.model.GeocodingResult;

/**
 * Service to use google's geocode service to look up address information.
 */
public interface GoogleGeocodeClient {

    /**
     * Looks up location information for an address via google's geocode API.
     *
     * @param address the address to look up
     * @return the geocode information
     * @throws Exception if the call into google's geocode API failed
     */
    GeocodingResult[] geocode(
            @Nonnull String address
    ) throws Exception;

}
