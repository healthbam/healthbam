package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An object to represent a KML LineStyle.
 */
@Immutable
public class KmlLineStyle {

    private final String color;
    private final String colorMode;
    private final String width;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlLineStyle() {
        this.color = null;
        this.colorMode = null;
        this.width = null;
    }

    /**
     * Constructs a {@link KmlLineStyle}.
     *
     * @param color the color (hex: alpha blue green red)
     * @param colorMode the color mode (normal or random)
     * @param width the width of the line
     */
    public KmlLineStyle(
            @Nonnull String color,
            @Nonnull String colorMode,
            @Nonnull String width
    ) {
        this.color = requireNonNull(color, "color cannot be null");
        this.colorMode = requireNonNull(colorMode, "colorMode cannot be null");
        this.width = requireNonNull(width, "width cannot be null");
    }

    @XmlElement
    public String getColor() {
        return color;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setColor(String color) {
        // empty
    }

    @XmlElement
    public String getColorMode() {
        return colorMode;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setColorMode(String colorMode) {
        // empty
    }

    @XmlElement
    public String getWidth() {
        return width;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setWidth(String width) {
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
