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
public class KmlDocument {

    private final String name;
    private final String description;
    private final List<KmlStyle> styles;
    private final List<KmlPlacemark> placemarks;

    public KmlDocument() {
        this.name = null;
        this.description = null;
        this.styles = null;
        this.placemarks = null;
    }

    public KmlDocument(
            @Nonnull String name,
            @Nonnull String description,
            @Nonnull List<KmlStyle> styles,
            @Nonnull List<KmlPlacemark> placemarks
    ) {
        this.name = requireNonNull(name, "name cannot be null");
        this.description = requireNonNull(description, "description cannot be null");
        this.styles = requireNonNull(styles, "styles cannot be null");
        this.placemarks = requireNonNull(placemarks, "placemarks cannot be null");
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // empty
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // empty
    }

    @XmlElement(name = "Style")
    public List<KmlStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<KmlStyle> styles) {
        // empty
    }

    @XmlElement(name = "Placemark")
    public List<KmlPlacemark> getPlacemarks() {
        return placemarks;
    }

    public void setPlacemarks(List<KmlPlacemark> placemarks) {
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
