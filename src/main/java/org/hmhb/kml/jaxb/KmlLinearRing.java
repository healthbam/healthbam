package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlLinearRing {

    private final String coordinates;

    public KmlLinearRing() {
        this.coordinates = null;
    }

    public KmlLinearRing(
            @Nonnull String coordinates
    ) {
        this.coordinates = requireNonNull(coordinates, "coordinates cannot be null");
    }

    @XmlElement
    public String getCoordinates() {
        return coordinates;
    }

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
