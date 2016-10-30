package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An object to represent a KML LinearRing.
 */
@Immutable
public class KmlLinearRing {

    private final String coordinates;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlLinearRing() {
        this.coordinates = null;
    }

    /**
     * Constructs a {@link KmlLinearRing}.
     *
     * @param coordinates the coordinates to put in this linear ring
     */
    public KmlLinearRing(
            @Nonnull String coordinates
    ) {
        this.coordinates = requireNonNull(coordinates, "coordinates cannot be null");
    }

    @XmlElement
    public String getCoordinates() {
        return coordinates;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setCoordinates(String coordinates) {
        // empty
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
