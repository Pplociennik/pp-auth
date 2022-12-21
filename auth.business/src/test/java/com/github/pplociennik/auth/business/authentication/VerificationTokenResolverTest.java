package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryVerificationTokenRepository;
import org.junit.jupiter.api.Test;

import static auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link VerificationTokenResolver}.
 *
 * @author Created by: Pplociennik at 06.08.2022 01:40
 */
class VerificationTokenResolverTest {

    private InMemoryVerificationTokenRepository verificationTokenRepository = new InMemoryVerificationTokenRepository();
    private VerificationTokenResolver sut = new VerificationTokenResolver( verificationTokenRepository );

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