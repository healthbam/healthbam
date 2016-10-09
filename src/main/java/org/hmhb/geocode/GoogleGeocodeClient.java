package org.hmhb.geocode;

import javax.annotation.Nonnull;

import com.google.maps.model.GeocodingResult;

public interface GoogleGeocodeClient {

    GeocodingResult[] geocode(
            @Nonnull String address
    ) throws Exception;

}
