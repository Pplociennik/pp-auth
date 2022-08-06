package com.github.pplociennik.auth.business.authentication.ports;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import org.springframework.lang.NonNull;

/**
 * A Data Access Object for authentication data.
 *
 * @author Created by: Pplociennik at 01.07.2022 13:52
 */
public interface VerificationTokenRepository {

    /**
     * Returns a {@link VerificationTokenDO} typed object containing the specified token.
     *
     * @param aToken
     *         a token.
     * @return a {@link VerificationTokenDO} typed object.
     */
    VerificationTokenDO findByToken( @NonNull String aToken );

    /**
     * Persists the VerificationToken object to the database;
     *
     * @param aVerificationToken
     *         a verification token domain object.
     * @return domain object.
     */
    VerificationTokenDO save( @NonNull VerificationTokenDO aVerificationToken );

    /**
     * Updates the specified VerificationToken in the database.
     *
     * @param aVerificationTokenDO
     *         a verification token domain object.
     * @return domain object.
     */
    VerificationTokenDO update( @NonNull VerificationTokenDO aVerificationTokenDO );
}
