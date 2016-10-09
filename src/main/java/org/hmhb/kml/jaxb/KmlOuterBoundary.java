package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlOuterBoundary {

    private final KmlLinearRing linearRing;

    public KmlOuterBoundary() {
        this.linearRing = null;
    }

    public KmlOuterBoundary(
            @Nonnull KmlLinearRing linearRing
    ) {
        this.linearRing = requireNonNull(linearRing, "linearRing cannot be null");
    }

    @XmlElement(name = "LinearRing")
    public KmlLinearRing getLinearRing() {
        return linearRing;
    }

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
