package org.hmhb.organization;

import javax.annotation.Nonnull;

import java.util.List;

public interface OrganizationService {

    Organization getById(@Nonnull Long id);

    List<Organization> getAll();

    Organization delete(@Nonnull Long id);

    Organization save(@Nonnull Organization request);

}
