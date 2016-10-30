package org.hmhb.geocode;

import javax.annotation.Nonnull;

import org.hmhb.program.Program;

/**
 * Service to look up lat-lng and zip code of a {@link Program}'s address.
 */
public interface GeocodeService {

    /**
     * Looks up latitude, longitude, and zip code of a {@link Program}'s
     * address.
     *
     * @param program the {@link Program} to use
     * @return the {@link Program}'s location information
     */
    LocationInfo getLocationInfo(
            @Nonnull Program program
    );

}
