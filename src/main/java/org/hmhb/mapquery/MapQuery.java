package org.hmhb.mapquery;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain object representing a query for map data.
 */
public class MapQuery {

    private MapQuerySearch search;
    private MapQueryResult result;

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

    public MapQuerySearch getSearch() {
        return search;
    }

    public void setSearch(MapQuerySearch search) {
        this.search = search;
    }

    public MapQueryResult getResult() {
        return result;
    }

    public void setResult(MapQueryResult result) {
        this.result = result;
    }

}
