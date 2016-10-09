package org.hmhb.mapquery;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hmhb.program.Program;

/**
 * The results of a map query.
 */
public class MapQueryResult {

    private List<Program> programs;
    private String mapLayerUrl;

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

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public String getMapLayerUrl() {
        return mapLayerUrl;
    }

    public void setMapLayerUrl(String mapLayerUrl) {
        this.mapLayerUrl = mapLayerUrl;
    }

}
