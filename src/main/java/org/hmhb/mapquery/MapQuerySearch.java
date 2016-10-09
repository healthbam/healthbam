package org.hmhb.mapquery;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hmhb.county.County;
import org.hmhb.organization.Organization;
import org.hmhb.programarea.ProgramArea;

/**
 * Criteria for filtering the results on the map.
 */
public class MapQuerySearch {

    private County county;

    private Organization organization;

    private ProgramArea programArea;

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

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ProgramArea getProgramArea() {
        return programArea;
    }

    public void setProgramArea(ProgramArea programArea) {
        this.programArea = programArea;
    }

}
