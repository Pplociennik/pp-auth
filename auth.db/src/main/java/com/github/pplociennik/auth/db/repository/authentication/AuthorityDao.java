package com.github.pplociennik.auth.db.repository.authentication;

import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * A DAO object for Authority data transfer.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:23
 */
public interface AuthorityDao extends JpaRepository< Authority, Long >, CrudRepository< Authority, Long > {

    /**
     * Returns a set of {@link Authority} typed objects tied to the user with the specified email address.
     *
     * @param aEmailAddress
     *         an email address of the user.
     * @return a set of authorities.
     */
    Set< Authority > findAllByAuthoritiesOwner_EmailAddress( @NonNull String aEmailAddress );
}
