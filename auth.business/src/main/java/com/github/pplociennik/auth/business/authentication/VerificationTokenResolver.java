package com.github.pplociennik.auth.business.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * A service for verification tokens' generation.
 *
 * @author Created by: Pplociennik at 06.08.2022 00:48
 */
class VerificationTokenResolver {

    private final VerificationTokenRepository tokenRepository;
    private final TimeService timeService;

    @Autowired
    VerificationTokenResolver( @NonNull VerificationTokenRepository aTokenRepository, TimeService aTimeService ) {
        tokenRepository = aTokenRepository;
        timeService = aTimeService;
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

        var verificationToken = VerificationTokenDO
                .builder()
                .token( token )
                .owner( aAccountDO )
                .type( aVerificationTokenType )
                .expirationDate( getExpirationDateForImmediateToken( aVerificationTokenType ) )
                .zoneId( timeService.getSystemZoneId() )
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

        var zoneId = timeService.getSystemZoneId();
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
