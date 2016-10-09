package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlPolyStyle {

    private final String color;
    private final String colorMode;
    private final String fill;
    private final String outline;

    public KmlPolyStyle() {
        this.color = null;
        this.colorMode = null;
        this.fill = null;
        this.outline = null;
    }

    public KmlPolyStyle(
            @Nonnull String color,
            @Nonnull String colorMode,
            @Nonnull String fill,
            @Nonnull String outline
    ) {
        this.color = requireNonNull(color, "color cannot be null");
        this.colorMode = requireNonNull(colorMode, "colorMode cannot be null");
        this.fill = requireNonNull(fill, "fill cannot be null");
        this.outline = requireNonNull(outline, "outline cannot be null");
    }

    @XmlElement
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        // empty
    }

    @XmlElement
    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        // empty
    }

    @XmlElement
    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        // empty
    }

    @XmlElement
    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
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
