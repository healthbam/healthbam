package org.hmhb.county;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain object representing a county of a state.
 */
@Entity
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String state;

    @NotNull
    private String outerBoundary1;

    private String innerBoundary1;

    private String outerBoundary2;

    private String outerBoundary3;

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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("state", state)
                /* I'm leaving out "shape info" because it is very spammy. */
                .toString();
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOuterBoundary1() {
        return outerBoundary1;
    }

    public void setOuterBoundary1(String outerBoundary1) {
        this.outerBoundary1 = outerBoundary1;
    }

    public String getInnerBoundary1() {
        return innerBoundary1;
    }

    public void setInnerBoundary1(String innerBoundary1) {
        this.innerBoundary1 = innerBoundary1;
    }

    public String getOuterBoundary2() {
        return outerBoundary2;
    }

    public void setOuterBoundary2(String outerBoundary2) {
        this.outerBoundary2 = outerBoundary2;
    }

    public String getOuterBoundary3() {
        return outerBoundary3;
    }

    public void setOuterBoundary3(String outerBoundary3) {
        this.outerBoundary3 = outerBoundary3;
    }

}
