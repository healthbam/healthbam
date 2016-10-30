package org.hmhb.kml;

import javax.annotation.Nonnull;

import org.hmhb.county.County;
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.program.Program;

/**
 * Service to create {@link KmlPlacemark}s to put into KML.
 */
public interface KmlPlacemarkService {

    /**
     * Creates a {@link KmlPlacemark} for a {@link County}.
     *
     * @param countyStyle the ID of the style to apply
     * @param county the {@link County} to provide display info
     * @return the {@link KmlPlacemark}
     */
    KmlPlacemark createCountyPlacemark(
            @Nonnull String countyStyle,
            @Nonnull County county
    );

    /**
     * Creates a {@link KmlPlacemark} for a {@link Program}.
     *
     * @param program the {@link Program} to provide display info
     * @param styleId the ID of the style to apply
     * @return the {@link KmlPlacemark}
     */
    KmlPlacemark createProgramPlacemark(
            @Nonnull Program program,
            @Nonnull String styleId
    );

}
