package com.github.pplociennik.auth.db.repository.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
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
     *
     * @return a {@link VerificationToken} typed object.
     */
    Optional< VerificationToken > findByToken( String aToken );

    /**
     * Returns a list of the tokens with the specified type, owner and "Active" flag.
     *
     * @param aTokenType
     *         the type of the tokens being searched for.
     * @param aAccount
     *         an Account being the owner of the tokens.
     * @param aActive
     *         the flag determining whether the tokens being searched should be active.
     *
     * @return a list of the tokens.
     */
    List< VerificationToken > findAllByTypeAndOwnerAndActive( AuthVerificationTokenType aTokenType, Account aAccount, boolean aActive );
}
