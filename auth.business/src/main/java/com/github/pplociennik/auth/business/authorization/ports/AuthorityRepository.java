package com.github.pplociennik.auth.business.authorization.ports;

import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * A Data Access Object for authorization data.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:16
 */
public interface AuthorityRepository {

    /**
     * Returns user's authorities by email address linked to the account.
     *
     * @param aEmailAddress
     *         an email address.
     * @return a set of authorities.
     */
    Set< AuthorityDO > findUserAuthorities( @NonNull String aEmailAddress );
}
