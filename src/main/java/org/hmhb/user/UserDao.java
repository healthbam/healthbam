package org.hmhb.user;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<HmhbUser, Long> {

    HmhbUser findByEmail(String email);

}
