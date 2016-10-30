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

/**
 * An object to represent a KML Placemark.
 */
@Immutable
public class KmlPlacemark {

    private final String id;
    private final String name;
    private final String styleUrl;
    private final String description;
    private final KmlPoint point;
    private final KmlPolygon polygon;
    private final KmlMuliGeometry muliGeometry;

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public KmlPlacemark() {
        this.id = null;
        this.name = null;
        this.styleUrl = null;
        this.description = null;
        this.point = null;
        this.polygon = null;
        this.muliGeometry = null;
    }

    /**
     * Constructs a {@link KmlPlacemark} from a {@link KmlPolygon}.
     *
     * @param id the placemark id
     * @param name the placemark name
     * @param styleUrl the style id to apply to the placemark
     * @param polygon the {@link KmlPolygon} to draw the placemark
     */
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
        this.muliGeometry = null;
    }

    /**
     * Constructs a {@link KmlPlacemark} from a {@link KmlMuliGeometry}.
     *
     * @param id the placemark id
     * @param name the placemark name
     * @param styleUrl the style id to apply to the placemark
     * @param muliGeometry the {@link KmlMuliGeometry} to draw the placemark
     */
    public KmlPlacemark(
            @Nonnull String id,
            @Nonnull String name,
            @Nonnull String styleUrl,
            @Nonnull KmlMuliGeometry muliGeometry
    ) {
        this.id = requireNonNull(id, "id cannot be null");
        this.name = requireNonNull(name, "name cannot be null");
        this.styleUrl = requireNonNull(styleUrl, "styleUrl cannot be null");
        this.description = null;
        this.point = null;
        this.polygon = null;
        this.muliGeometry = requireNonNull(muliGeometry, "multiGeometry cannot be null");
    }

    /**
     * Constructs a {@link KmlPlacemark} from a {@link KmlPoint}.
     *
     * @param id the placemark id
     * @param name the placemark name
     * @param styleUrl the style id to apply to the placemark
     * @param description the placemark description
     * @param point the {@link KmlPoint} to draw the placemark
     */
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
        this.muliGeometry = null;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setId(String id) {
        // empty
    }

    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setName(String name) {
        // empty
    }

    @XmlElement
    public String getStyleUrl() {
        return styleUrl;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setStyleUrl(String styleUrl) {
        // empty
    }

    @XmlElement
    @XmlCDATA
    public String getDescription() {
        return description;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setDescription(String description) {
        // empty
    }

    @XmlElement(name = "Point")
    public KmlPoint getPoint() {
        return point;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setPoint(KmlPoint point) {
        // empty
    }

    @XmlElement(name = "Polygon")
    public KmlPolygon getPolygon() {
        return polygon;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setPolygon(KmlPolygon polygon) {
        // empty
    }

    @XmlElement(name = "MultiGeometry")
    public KmlMuliGeometry getMuliGeometry() {
        return muliGeometry;
    }

    /**
     * Do not use this; it was only implemented to satisfy jaxb.
     */
    public void setMuliGeometry(KmlMuliGeometry muliGeometry) {
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
