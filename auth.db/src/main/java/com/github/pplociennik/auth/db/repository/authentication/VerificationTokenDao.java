package com.github.pplociennik.auth.db.repository.authentication;

import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * A DAO object for Token data transfer.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:09
 */
public interface VerificationTokenDao extends JpaRepository< VerificationToken, Long >, CrudRepository< VerificationToken, Long > {

    /**
     * Returns a {@link VerificationToken} typed object containing the specified token.
     *
     * @param aToken
     *         a token.
     * @return a {@link VerificationToken} typed object.
     */
    Optional< VerificationToken > findByToken( String aToken );
}
