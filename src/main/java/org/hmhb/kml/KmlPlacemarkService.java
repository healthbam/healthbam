package org.hmhb.kml;

import javax.annotation.Nonnull;

import org.hmhb.county.County;
import org.hmhb.kml.jaxb.KmlPlacemark;
import org.hmhb.program.Program;

public interface KmlPlacemarkService {

    KmlPlacemark createCountyPlacemark(
            @Nonnull String countyStyle,
            @Nonnull County county
    );

    KmlPlacemark createProgramPlacemark(
            @Nonnull Program program,
            @Nonnull String styleId
    );

}
