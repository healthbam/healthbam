package org.hmhb.kml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;


@XmlRootElement(
//        namespace = "http://www.opengis.net/kml/2.2",
        name = "kml"
)
@Immutable
public class KmlRoot {

    private final KmlDocument document;

    public KmlRoot() {
        this.document = null;
    }

    public KmlRoot(
            @Nonnull KmlDocument document
    ) {
        this.document = requireNonNull(document, "document cannot be null");
    }

    @XmlElement(name = "Document")
    public KmlDocument getDocument() {
        return document;
    }

    public void setDocument(KmlDocument document) {
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
