package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An oject to represent a KML innerBoundaryIs.
 */
@Immutable
public class KmlInnerBoundary {

    private final KmlLinearRing linearRing;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlInnerBoundary() {
        this.linearRing = null;
    }

    /**
     * Constructs a {@link KmlInnerBoundary}.
     *
     * @param linearRing the {@link KmlLinearRing} for this inner boundary
     */
    public KmlInnerBoundary(
            @Nonnull KmlLinearRing linearRing
    ) {
        this.linearRing = requireNonNull(linearRing, "linearRing cannot be null");
    }

    @XmlElement(name = "LinearRing")
    public KmlLinearRing getLinearRing() {
        return linearRing;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setLinearRing(KmlLinearRing linearRing) {
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
