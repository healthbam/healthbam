package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An object to represent a KML Polygon.
 */
@Immutable
public class KmlPolygon {

    private final KmlInnerBoundary innerBoundaryIs;
    private final KmlOuterBoundary outerBoundaryIs;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlPolygon() {
        this.outerBoundaryIs = null;
        this.innerBoundaryIs = null;
    }

    /**
     * Constructs a {@link KmlPolygon} with only an outer boundary.
     *
     * @param outerBoundaryIs the outer boundary
     */
    public KmlPolygon(
            @Nonnull KmlOuterBoundary outerBoundaryIs
    ) {
        this.outerBoundaryIs = requireNonNull(outerBoundaryIs, "outerBoundaryIs cannot be null");
        this.innerBoundaryIs = null;
    }

    /**
     * Constructs a {@link KmlPolygon} with an outer and inner boundary.
     *
     * @param outerBoundaryIs the outer boundary
     * @param innerBoundaryIs the inner boundary
     */
    public KmlPolygon(
            @Nonnull KmlOuterBoundary outerBoundaryIs,
            @Nonnull KmlInnerBoundary innerBoundaryIs
    ) {
        this.outerBoundaryIs = requireNonNull(outerBoundaryIs, "outerBoundaryIs cannot be null");
        this.innerBoundaryIs = requireNonNull(innerBoundaryIs, "innerBoundaryIs cannot be null");
    }

    @XmlElement
    public KmlOuterBoundary getOuterBoundaryIs() {
        return outerBoundaryIs;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setOuterBoundaryIs(KmlOuterBoundary outerBoundaryIs) {
        // empty
    }

    @XmlElement
    public KmlInnerBoundary getInnerBoundaryIs() {
        return innerBoundaryIs;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setInnerBoundaryIs(KmlInnerBoundary innerBoundaryIs) {
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
