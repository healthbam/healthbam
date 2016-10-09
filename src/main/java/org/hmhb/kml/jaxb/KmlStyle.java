package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlStyle {

    private final String id;
    private final KmlPolyStyle polyStyle;
    private final KmlIconStyle iconStyle;

    public KmlStyle() {
        this.id = null;
        this.polyStyle = null;
        this.iconStyle = null;
    }

    public KmlStyle(
            @Nonnull String id,
            @Nonnull KmlPolyStyle polyStyle
    ) {
        this.id = requireNonNull(id, "id cannot be null");
        this.polyStyle = requireNonNull(polyStyle, "polyStyle cannot be null");
        this.iconStyle = null;
    }

    public KmlStyle(
            @Nonnull String id,
            @Nonnull KmlIconStyle iconStyle
    ) {
        this.id = requireNonNull(id, "id cannot be null");
        this.polyStyle = null;
        this.iconStyle = requireNonNull(iconStyle, "iconStyle cannot be null");
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // empty
    }

    @XmlElement(name = "PolyStyle")
    public KmlPolyStyle getPolyStyle() {
        return polyStyle;
    }

    public void setPolyStyle(KmlPolyStyle polyStyle) {
        // empty
    }

    @XmlElement(name = "IconStyle")
    public KmlIconStyle getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(KmlIconStyle iconStyle) {
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
