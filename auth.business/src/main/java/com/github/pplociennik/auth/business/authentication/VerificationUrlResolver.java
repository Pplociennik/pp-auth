package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static com.github.pplociennik.auth.business.authentication.TokenExpirationDateCalculationStrategy.ACCOUNT_CONFIRMATION_REQUEST;
import static com.github.pplociennik.auth.common.auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static java.util.Objects.requireNonNull;

/**
 * A resolver class for verification tokens being used during the authentication process.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:57
 */
class VerificationUrlResolver {

    private final VerificationTokenRepository tokenRepository;
    private final SystemPropertiesProvider propertiesProvider;

    @Autowired
    VerificationUrlResolver( @NonNull VerificationTokenRepository aTokenRepository, @NonNull SystemPropertiesProvider aPropertiesProvider ) {
        tokenRepository = requireNonNull( aTokenRepository );
        propertiesProvider = requireNonNull( aPropertiesProvider );
    }

    String resolveAccountConfirmationLink( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );

        final var parameter = "aToken";

        var verificationToken = resolveUniqueToken( aAccountDO );
        var url = propertiesProvider.getFrontendUrl();

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


    private Instant getExpirationDateForImmediateToken( TokenExpirationDateCalculationStrategy aAccountConfirmationRequest ) {
        return getExpirationDateForToken( Instant.now(), aAccountConfirmationRequest );
    }

    private Instant getExpirationDateForToken( @NonNull Instant aStartDate, TokenExpirationDateCalculationStrategy aAccountConfirmationRequest ) {
        requireNonNull( aStartDate );

        var zoneId = ZoneId.systemDefault();
        var startDateTime = aStartDate.atZone( zoneId );

        var expirationDateTime = aAccountConfirmationRequest.calculate( startDateTime );
        return expirationDateTime.toInstant();
    }

    private String generateUniqueToken() {
        var token = StringUtils.EMPTY;

        do {
            token = UUID.randomUUID().toString();
        }
        while ( tokenRepository.findByToken( token ) != null );

        return token;
    }
}
