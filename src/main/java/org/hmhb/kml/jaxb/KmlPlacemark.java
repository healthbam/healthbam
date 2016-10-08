package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import static java.util.Objects.requireNonNull;

@Immutable
public class KmlPlacemark {

    private final String id;
    private final String name;
    private final String styleUrl;
    private final String description;
    private final KmlPoint point;
    private final KmlPolygon polygon;

    public KmlPlacemark() {
        this.id = null;
        this.name = null;
        this.styleUrl = null;
        this.description = null;
        this.point = null;
        this.polygon = null;
    }

    public KmlPlacemark(
            @Nonnull String id,
            @Nonnull String name,
            @Nonnull String styleUrl,
            @Nonnull KmlPolygon polygon
    ) {
        this.id = requireNonNull(id, "id cannot be null");
        this.name = requireNonNull(name, "name cannot be null");
        this.styleUrl = requireNonNull(styleUrl, "styleUrl cannot be null");
        this.description = null;
        this.point = null;
        this.polygon = requireNonNull(polygon, "polygon cannot be null");
    }

    public KmlPlacemark(
            @Nonnull String id,
            @Nonnull String name,
            @Nonnull String styleUrl,
            @Nonnull String description,
            @Nonnull KmlPoint point
    ) {
        this.id = requireNonNull(id, "id cannot be null");
        this.name = requireNonNull(name, "name cannot be null");
        this.styleUrl = requireNonNull(styleUrl, "styleUrl cannot be null");
        this.description = requireNonNull(description, "description cannot be null");
        this.point = requireNonNull(point, "point cannot be null");
        this.polygon = null;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // empty
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // empty
    }

    @XmlElement
    public String getStyleUrl() {
        return styleUrl;
    }

    public void setStyleUrl(String styleUrl) {
        // empty
    }

    @XmlElement
    @XmlCDATA
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // empty
    }

    @XmlElement(name = "Point")
    public KmlPoint getPoint() {
        return point;
    }

    @XmlElement(name = "Polygon")
    public KmlPolygon getPolygon() {
        return polygon;
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
