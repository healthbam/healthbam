package org.hmhb.kml;

import javax.annotation.Nonnull;

/**
 * Service for generating KML for displaying programs and counties on a map.
 */
public interface KmlService {

    /**
     * Generate KML for displaying the passed in programs and county on a map.
     *
     * @param countyId the county to show selected
     * @param programIdsString the comma delimited list of programs to display
     * @return the generated KML
     */
    String getKml(
            @Nonnull String countyId,
            @Nonnull String programIdsString
    );

}
