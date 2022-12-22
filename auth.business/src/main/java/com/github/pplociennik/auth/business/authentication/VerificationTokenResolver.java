package com.github.pplociennik.auth.business.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.inside.VerificationTokenRepository;
import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import com.github.pplociennik.commons.utility.identifier.UniqueIdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * A service for verification tokens' generation.
 *
 * @author Created by: Pplociennik at 06.08.2022 00:48
 */
class VerificationTokenResolver {

    private final VerificationTokenRepository tokenRepository;

    @Autowired
    VerificationTokenResolver( @NonNull VerificationTokenRepository aTokenRepository ) {
        tokenRepository = aTokenRepository;
    }

    /**
     * Generates a new unique verification token of the specified type assigned to the specified account and persists it
     * to the database.
     *
     * @param aAccountDO
     *         the account the token is being assigned to
     * @param aVerificationTokenType
     *         a type of the token being generated
     * @return a domain object being the representation of the verification token
     */
    VerificationTokenDO resolveUniqueToken(
            @NonNull AccountDO aAccountDO, @NonNull AuthVerificationTokenType aVerificationTokenType ) {
        requireNonNull( aAccountDO );
        requireNonNull( aVerificationTokenType );

        var token = generateUniqueToken();
        var identifier = UniqueIdentifierGenerator.generateIdentifier( VerificationToken.class, EMPTY );

        var verificationToken = VerificationTokenDO
                .builder()
                .uniqueObjectIdentifier( identifier )
                .token( token )
                .owner( aAccountDO )
                .type( aVerificationTokenType )
                .expirationDate( getExpirationDateForImmediateToken( aVerificationTokenType ) )
                .zoneId( ZoneId.of( "UTC" ) )
                .isActive( true )
                .build();

        return tokenRepository.persist( verificationToken );
    }

    private Instant getExpirationDateForImmediateToken( AuthVerificationTokenType aVerificationTokenType ) {
        return getExpirationDateForToken( Instant.now(), aVerificationTokenType );
    }

    private Instant getExpirationDateForToken(
            @NonNull Instant aStartDate, AuthVerificationTokenType aVerificationTokenType ) {
        requireNonNull( aStartDate );

        var zoneId = ZoneId.systemDefault();
        var startDateTime = aStartDate.atZone( zoneId );

        var amountToAdd = aVerificationTokenType.getAmount();
        var unit = aVerificationTokenType.getUnit();

        var expirationDateTime = startDateTime.plus( amountToAdd, unit );
        return expirationDateTime.toInstant();
    }

    private String generateUniqueToken() {

        var token = UUID
                .randomUUID()
                .toString();

        var foundToken = tokenRepository.findByToken( token );

        return foundToken == null
               ? token
               : generateUniqueToken();
    }
}
