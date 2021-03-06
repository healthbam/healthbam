package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * An object to represent a KML IconStyle.
 */
@Immutable
public class KmlIconStyle {

    private final KmlIcon icon;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlIconStyle() {
        this.icon = null;
    }

    /**
     * Constructs a {@link KmlIconStyle}.
     *
     * @param icon the {@link KmlIcon} for this style
     */
    public KmlIconStyle(
            @Nonnull KmlIcon icon
    ) {
        this.icon = requireNonNull(icon, "icon cannot be null");
    }

    @XmlElement(name = "Icon")
    public KmlIcon getIcon() {
        return icon;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setIcon(KmlIcon icon) {
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
