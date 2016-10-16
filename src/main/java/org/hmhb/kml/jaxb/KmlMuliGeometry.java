package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlMuliGeometry {

    private final List<KmlPolygon> polygons;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlMuliGeometry() {
        this.polygons = null;
    }

    public KmlMuliGeometry(
            @Nonnull List<KmlPolygon> polygons
    ) {
        this.polygons = requireNonNull(polygons, "polygons cannot be null");
    }

    @XmlElement(name = "Polygon")
    public List<KmlPolygon> getPolygons() {
        return polygons;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setPolygons(List<KmlPolygon> polygons) {
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
