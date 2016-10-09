package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlPolygon {

    private final KmlOuterBoundary outerBoundaryIs;

    public KmlPolygon() {
        this.outerBoundaryIs = null;
    }

    public KmlPolygon(
            @Nonnull KmlOuterBoundary outerBoundaryIs
    ) {
        this.outerBoundaryIs = requireNonNull(outerBoundaryIs, "outerBoundaryIs cannot be null");
    }

    @XmlElement
    public KmlOuterBoundary getOuterBoundaryIs() {
        return outerBoundaryIs;
    }

    public void setOuterBoundaryIs(KmlOuterBoundary outerBoundaryIs) {
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
