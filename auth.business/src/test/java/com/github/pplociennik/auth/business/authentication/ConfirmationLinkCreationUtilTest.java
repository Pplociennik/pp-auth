package com.github.pplociennik.auth.business.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.auth.business.authentication.data.AuthenticationValidationRepositoryTestDataSupplier;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * A util test class for {@link ConfirmationLinkCreationUtil}.
 *
 * @author Created by: Pplociennik at 15.02.2024 12:52
 */
class ConfirmationLinkCreationUtilTest {

    private static final String TEST_CLIENT_URL = "https://test-client.com";
    private static final AccountDO TEST_ACCOUNT = AuthenticationValidationRepositoryTestDataSupplier.prepareAccount_1();
    private static final String TEST_TOKEN = "5c968c33-23bb-47b8-8fe6-c535c4a16fa3";
    private static final Instant TEST_EXPIRATION_TIME = Instant.now().plus( 15L, ChronoUnit.MINUTES );
    private static final VerificationTokenDO TEST_VERIFICATION_TOKEN_DO = new VerificationTokenDO( 1L, AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN, TEST_ACCOUNT, TEST_TOKEN, TEST_EXPIRATION_TIME, true );

    @Test
    void shouldCreateValidUrl_whenClientUrlAndVerificationTokenValid() {

        // WHEN
        var result = ConfirmationLinkCreationUtil.createConfirmationLink( TEST_CLIENT_URL, TEST_VERIFICATION_TOKEN_DO );

        // THEN
        String expectedString = TEST_CLIENT_URL + "/?aToken=" + TEST_TOKEN;
        Assertions.assertThat( result ).isEqualTo( expectedString );
    }

    @Test
    void shouldThrowNullPointerException_whenVerificationTokenIsNull() {
        Assertions.assertThatExceptionOfType( NullPointerException.class )
                .isThrownBy( () -> ConfirmationLinkCreationUtil.createConfirmationLink( TEST_CLIENT_URL, null ) );
    }

    @Test
    void shouldThrowNullPointerException_whenClientUrlIsNull() {
        Assertions.assertThatExceptionOfType( NullPointerException.class )
                .isThrownBy( () -> ConfirmationLinkCreationUtil.createConfirmationLink( null, TEST_VERIFICATION_TOKEN_DO ) );
    }

    @Test
    void shouldThrowNullPointerException_whenClientUrlIsBlank() {
        Assertions.assertThatExceptionOfType( NullPointerException.class )
                .isThrownBy( () -> ConfirmationLinkCreationUtil.createConfirmationLink( "", TEST_VERIFICATION_TOKEN_DO ) );
    }

}