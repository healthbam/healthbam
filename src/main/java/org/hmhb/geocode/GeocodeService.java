package org.hmhb.geocode;

import javax.annotation.Nonnull;

import org.hmhb.program.Program;

public interface GeocodeService {

    LocationInfo getLocationInfo(
            @Nonnull Program program
    );

}
