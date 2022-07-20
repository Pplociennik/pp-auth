package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.business.shared.system.EnvironmentPropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static com.github.pplociennik.auth.business.authentication.TokenExpirationDateAmount.ACCOUNT_CONFIRMATION_REQUEST;
import static com.github.pplociennik.auth.business.shared.system.SystemProperty.GLOBAL_CLIENT_URL;
import static com.github.pplociennik.auth.common.auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static java.util.Objects.requireNonNull;

/**
 * A resolver class for verification tokens being used during the authentication process.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:57
 */
class VerificationUrlResolver {

    private final VerificationTokenRepository tokenRepository;
    private final EnvironmentPropertiesProvider propertiesProvider;

    @Autowired
    VerificationUrlResolver( @NonNull VerificationTokenRepository aTokenRepository, @NonNull EnvironmentPropertiesProvider aPropertiesProvider ) {
        tokenRepository = requireNonNull( aTokenRepository );
        propertiesProvider = requireNonNull( aPropertiesProvider );
    }

    String resolveAccountConfirmationLink( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );

        final var parameter = "aToken";

        var verificationToken = resolveUniqueToken( aAccountDO );
        var url = propertiesProvider.getPropertyValue( GLOBAL_CLIENT_URL );

        return url + "/?" + parameter + "=" + verificationToken.getToken();
    }

    private VerificationTokenDO resolveUniqueToken( AccountDO aAccountDO ) {
        var token = generateUniqueToken();

        var verificationToken = VerificationTokenDO.builder()
                .token( token )
                .owner( aAccountDO )
                .type( EMAIL_CONFIRMATION_TOKEN )
                .expirationDate( getExpirationDateForImmediateToken( ACCOUNT_CONFIRMATION_REQUEST ) )
                .build();

        return tokenRepository.save( verificationToken );
    }


    private Instant getExpirationDateForImmediateToken( TokenExpirationDateAmount aExpDateAmount ) {
        return getExpirationDateForToken( Instant.now(), aExpDateAmount );
    }

    private Instant getExpirationDateForToken( @NonNull Instant aStartDate, TokenExpirationDateAmount aTokenExpirationDateAmount ) {
        requireNonNull( aStartDate );

        var zoneId = ZoneId.systemDefault();
        var startDateTime = aStartDate.atZone( zoneId );

        var amountToAdd = aTokenExpirationDateAmount.getAmount();
        var unit = aTokenExpirationDateAmount.getUnit();

        var expirationDateTime = startDateTime.plus( amountToAdd, unit );
        return expirationDateTime.toInstant();
    }

    private String generateUniqueToken() {

        var token = UUID.randomUUID().toString();

        var foundToken = tokenRepository.findByToken( token );

        return foundToken == null
                ? token
                : generateUniqueToken();
    }
}
