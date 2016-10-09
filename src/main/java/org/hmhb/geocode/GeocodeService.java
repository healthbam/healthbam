package org.hmhb.geocode;

import javax.annotation.Nonnull;

import org.hmhb.program.Program;

public interface GeocodeService {

    String getLngLat(
            @Nonnull Program program
    );

}
