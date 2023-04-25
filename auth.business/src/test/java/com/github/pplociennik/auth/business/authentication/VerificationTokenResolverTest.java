package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryTimeService;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryVerificationTokenRepository;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link VerificationTokenResolver}.
 *
 * @author Created by: Pplociennik at 06.08.2022 01:40
 */
class VerificationTokenResolverTest {

    private static final ZoneId TEST_ZONE_ID = ZoneId.of( "UTC" );

    private InMemoryVerificationTokenRepository verificationTokenRepository = new InMemoryVerificationTokenRepository();
    private InMemoryTimeService timeService = new InMemoryTimeService();
    private VerificationTokenResolver sut = new VerificationTokenResolver( verificationTokenRepository, timeService );

    @Test
    void shouldThrowNullPointerException_whenAccountParameterIsNull() {

        // WHEN
        // THEN
        assertThatThrownBy( () -> sut.resolveUniqueToken( null, EMAIL_CONFIRMATION_TOKEN ) ).isInstanceOf(
                NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenTypeParameterIsNull() {

        // GIVEN
        var accountDO = AccountDO
                .builder()
                .emailAddress( "test@email.com" )
                .build();
        // WHEN
        // THEN
        assertThatThrownBy( () -> sut.resolveUniqueToken( accountDO, null ) ).isInstanceOf(
                NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenBothParametersNull() {

        // WHEN
        // THEN
        assertThatThrownBy( () -> sut.resolveUniqueToken( null, null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldReturnNewlyCreatedToken_whenDataValid() {

        // GIVEN
        timeService.setSystemZoneId( TEST_ZONE_ID );
        var accountDO = AccountDO
                .builder()
                .emailAddress( "test@email.com" )
                .build();

        // WHEN
        // THEN
        var result = sut.resolveUniqueToken( accountDO, EMAIL_CONFIRMATION_TOKEN );
        assertThat( result ).isNotNull();
    }

}