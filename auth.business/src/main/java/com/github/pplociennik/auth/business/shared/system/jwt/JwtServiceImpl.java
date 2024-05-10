package com.github.pplociennik.auth.business.shared.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.shared.system.JwtService;
import com.github.pplociennik.auth.business.shared.system.SystemProperties;
import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * A service providing functionalities related to JWT generation and maintenance.
 *
 * @author Created by: Pplociennik at 10.05.2024 22:03
 */
@Service
public class JwtServiceImpl implements JwtService {

    private final SystemPropertiesProvider propertiesProvider;
    private final String issuer;
    private final Algorithm algorithm;

    @Autowired
    public JwtServiceImpl( SystemPropertiesProvider propertiesProvider ) {
        this.propertiesProvider = propertiesProvider;
        this.issuer = "SYSTEM";
        this.algorithm = Algorithm.HMAC512( getJWTSecret() );
    }

    /**
     * Returns JWT token for the authenticated account
     *
     * @param aAccountDO
     *         the account which has been successfully authenticated in the system.
     * @return the generated token.
     */
    public String generateToken( AccountDO aAccountDO ) {

        Algorithm algorithm = Algorithm.HMAC256( getJWTSecret() );

        Date now = new Date();
        Date expiryDate = new Date( now.getTime() + getJWTExpirationTime() );

        return JWT.create()
                .withIssuer( issuer )
                .withSubject( "Authentication Details" )
                .withClaim( "userId", aAccountDO.getId() )
                .withIssuedAt( new Date() )
                .withExpiresAt( expiryDate )
                .withJWTId( UUID.randomUUID()
                        .toString() )
                .sign( algorithm );
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
    public Long getUserIdFromJWT( String aToken ) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require( algorithm )
                .withIssuer( issuer )
                .build();

        DecodedJWT decodedJWT = verifier.verify( aToken );
        Claim userId = decodedJWT.getClaim( "userId" );
        return userId.asLong();
    }

    /**
     * Returns true if the specified token is valid.
     *
     * @param aToken
     *         a token to be verified.
     * @return TRUE if the specified token is valid, FALSE otherwise.
     */
    public boolean validateToken( String aToken ) {
        JWTVerifier verifier = JWT.require( algorithm )
                .withIssuer( issuer )
                .build();

        try {
            verifier.verify( aToken );
        } catch ( JWTVerificationException aE ) {
            return false;
        }

        return true;
    }

    private String getJWTSecret() {
        return propertiesProvider.getPropertyValue( SystemProperties.GLOBAL_JWT_SECRET );
    }

    private long getJWTExpirationTime() {
        var stringValue = propertiesProvider.getPropertyValue( SystemProperties.GLOBAL_JWT_EXPIRATION_TIME );
        return Long.parseLong( stringValue );
    }
}
