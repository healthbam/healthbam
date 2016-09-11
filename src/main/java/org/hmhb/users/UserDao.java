package org.hmhb.users;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by ryan on 9/2/16.
 */
public interface UserDao extends CrudRepository<HmhbUser, Long> {

}
