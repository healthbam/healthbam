package org.hmhb.kml;

import javax.annotation.Nonnull;

public interface KmlService {

    String getKml(
            @Nonnull String countyId,
            @Nonnull String programIdsString
    );

}
