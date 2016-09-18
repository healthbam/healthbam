package org.hmhb.program.published;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hmhb.program.requested.RequestedProgram;

@Entity
public class PublishedProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String organizationName;

    private String mission;

    @NotNull
    private String geo;

    private String summary;

    private String outcomes;

    private String publicEmail;

    private String publicPhone;

    private String privateEmail;

    private String privatePhone;

    @NotNull
    private Date createdOn;

    @NotNull
    private String createdBy;

    private Date updatedOn;

    private String updatedBy;

    @OneToMany(mappedBy = "programId")
    private List<RequestedProgram> requestedPrograms = new ArrayList<>();

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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String outcomes) {
        this.outcomes = outcomes;
    }

    public String getPublicEmail() {
        return publicEmail;
    }

    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

    public String getPublicPhone() {
        return publicPhone;
    }

    public void setPublicPhone(String publicPhone) {
        this.publicPhone = publicPhone;
    }

    public String getPrivateEmail() {
        return privateEmail;
    }

    public void setPrivateEmail(String privateEmail) {
        this.privateEmail = privateEmail;
    }

    public String getPrivatePhone() {
        return privatePhone;
    }

    public void setPrivatePhone(String privatePhone) {
        this.privatePhone = privatePhone;
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

    public List<RequestedProgram> getRequestedPrograms() {
        return requestedPrograms;
    }

    public void setRequestedPrograms(List<RequestedProgram> requestedPrograms) {
        this.requestedPrograms = requestedPrograms;
    }

}
