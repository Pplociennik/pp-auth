package com.github.pplociennik.auth.business.authentication.testimpl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.shared.system.JwtService;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 11.05.2024 01:04
 */
public class InMemoryJwtService implements JwtService {

    private String generatedToken;
    private long userId;
    private boolean tokenValid;

    /**
     * Returns JWT token for the authenticated account
     *
     * @param aAccountDO
     *         the account which has been successfully authenticated in the system.
     * @return the generated token.
     */
    @Override
    public String generateToken( AccountDO aAccountDO ) {
        return generatedToken;
    }

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
    @Override
    public Long getUserIdFromJWT( String aToken ) throws JWTVerificationException {
        return userId;
    }

    /**
     * Returns true if the specified token is valid.
     *
     * @param aToken
     *         a token to be verified.
     * @return TRUE if the specified token is valid, FALSE otherwise.
     */
    @Override
    public boolean validateToken( String aToken ) {
        return tokenValid;
    }

    public void setGeneratedToken( String generatedToken ) {
        this.generatedToken = generatedToken;
    }

    public void setUserId( long userId ) {
        this.userId = userId;
    }

    public void setTokenValid( boolean tokenValid ) {
        this.tokenValid = tokenValid;
    }
}
