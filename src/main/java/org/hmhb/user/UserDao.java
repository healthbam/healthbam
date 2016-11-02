package org.hmhb.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Database access object for {@link HmhbUser} objects.
 *
 * spring-data-jpa is providing the implementation of this dao:
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface UserDao extends CrudRepository<HmhbUser, Long> {

    /**
     * Returns the {@link HmhbUser}s associated with the passed in email.
     *
     * @return the {@link HmhbUser}s
     */
    HmhbUser findByEmailIgnoreCase(String email);

    /**
     * Returns all {@link HmhbUser}s ordered ascending by their display names,
     * then emails.
     *
     * @return all {@link HmhbUser}s
     */
    List<HmhbUser> findAllByOrderByDisplayNameAscEmailAsc();

}
