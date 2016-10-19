package org.hmhb.program;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hmhb.county.County;
import org.hmhb.organization.Organization;
import org.hmhb.programarea.ProgramArea;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private Integer startYear;

    @NotNull
    private String streetAddress;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zipCode;

    @NotNull
    private String coordinates;

    @NotNull
    private String primaryGoal1;

    private String primaryGoal2;

    private String primaryGoal3;

    @NotNull
    private String measurableOutcome1;

    private String measurableOutcome2;

    private String measurableOutcome3;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "program_county",
            joinColumns = @JoinColumn(
                    name = "program_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "county_id",
                    referencedColumnName = "id"
            )
    )
    private Set<County> countiesServed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "program_program_area",
            joinColumns = @JoinColumn(
                    name = "program_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "program_area_id",
                    referencedColumnName = "id"
            )
    )
    private Set<ProgramArea> programAreas;

    @NotNull
    private Date createdOn;

    @NotNull
    private String createdBy;

    private Date updatedOn;

    private String updatedBy;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getPrimaryGoal1() {
        return primaryGoal1;
    }

    public void setPrimaryGoal1(String primaryGoal1) {
        this.primaryGoal1 = primaryGoal1;
    }

    public String getPrimaryGoal2() {
        return primaryGoal2;
    }

    public void setPrimaryGoal2(String primaryGoal2) {
        this.primaryGoal2 = primaryGoal2;
    }

    public String getPrimaryGoal3() {
        return primaryGoal3;
    }

    public void setPrimaryGoal3(String primaryGoal3) {
        this.primaryGoal3 = primaryGoal3;
    }

    public String getMeasurableOutcome1() {
        return measurableOutcome1;
    }

    public void setMeasurableOutcome1(String measurableOutcome1) {
        this.measurableOutcome1 = measurableOutcome1;
    }

    public String getMeasurableOutcome2() {
        return measurableOutcome2;
    }

    public void setMeasurableOutcome2(String measurableOutcome2) {
        this.measurableOutcome2 = measurableOutcome2;
    }

    public String getMeasurableOutcome3() {
        return measurableOutcome3;
    }

    public void setMeasurableOutcome3(String measurableOutcome3) {
        this.measurableOutcome3 = measurableOutcome3;
    }

    public Set<County> getCountiesServed() {
        return countiesServed;
    }

    public void setCountiesServed(Set<County> countiesServed) {
        this.countiesServed = countiesServed;
    }

    public Set<ProgramArea> getProgramAreas() {
        return programAreas;
    }

    public void setProgramAreas(Set<ProgramArea> programAreas) {
        this.programAreas = programAreas;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
