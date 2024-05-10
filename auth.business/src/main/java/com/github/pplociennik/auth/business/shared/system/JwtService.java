package com.github.pplociennik.auth.business.shared.system;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;

/**
 * A service providing functionalities related to JWT generation and maintenance.
 *
 * @author Created by: Pplociennik at 10.05.2024 22:02
 */
public interface JwtService {

    /**
     * Returns JWT token for the authenticated account
     *
     * @param aAccountDO
     *         the account which has been successfully authenticated in the system.
     * @return the generated token.
     */
    String generateToken( AccountDO aAccountDO );

    /**
     * Returns the user id decoded from the token.
     *
     * @param aToken
     *         the JWT token of the authenticated account.
     * @return the authenticated account's identifier.
     *
     * @throws JWTVerificationException
     *         when the token is not valid.
     */
    Long getUserIdFromJWT( String aToken ) throws JWTVerificationException;

    /**
     * Returns true if the specified token is valid.
     *
     * @param aToken
     *         a token to be verified.
     * @return TRUE if the specified token is valid, FALSE otherwise.
     */
    boolean validateToken( String aToken );

}
