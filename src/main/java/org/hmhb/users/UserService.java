package org.hmhb.users;

import javax.annotation.Nonnull;

import java.util.List;

public interface UserService {

    HmhbUser getById(@Nonnull Long id);

    List<HmhbUser> getAll();

    HmhbUser delete(@Nonnull Long id);

    HmhbUser save(@Nonnull HmhbUser user);

}
