/*
 * MIT License
 *
 * Copyright (c) 2021 Przemysław Płóciennik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAccountRepository;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryTimeService;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryVerificationTokenRepository;
import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link AuthService}.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:06
 */
class AuthServiceTest {

    private static final String TEST_VALID_USERNAME = "ValidUserName";
    private static final String TEST_VALID_EMAIL = "testEmail@gmail.com";
    private static final String TEST_VALID_PASSWORD = "TestValidPassword1!";
    private static final String TEST_ENCODED_PASSWORD = "EncodedPass";
    private static final long TEST_TOKEN_ID = 1L;
    private static final String TEST_TOKEN_UNIQUE_ID = "VerificationToken#221222#210700";
    private static final String TEST_TOKEN = "testToken";
    private static final ZoneId TEST_ZONE_ID = ZoneId.of( "UTC" );
    private static final ZonedDateTime TEST_CURRENT_TIME = ZonedDateTime.now( TEST_ZONE_ID );

    private List< VerificationTokenDO > TEST_TOKEN_DATABASE = new LinkedList<>();
    private List< AccountDO > TEST_ACCOUNT_DATABASE = new LinkedList<>();

    private SystemPropertiesProvider propertiesProvider = mock( SystemPropertiesProvider.class );
    private PasswordEncoder encoder = mock( PasswordEncoder.class );
    private InMemoryAccountRepository accountRepository = new InMemoryAccountRepository( TEST_ACCOUNT_DATABASE );
    private InMemoryVerificationTokenRepository verificationTokenRepository = new InMemoryVerificationTokenRepository(
            TEST_TOKEN_DATABASE );
    private InMemoryTimeService timeService = new InMemoryTimeService();
    private VerificationTokenResolver tokenResolver = new VerificationTokenResolver( verificationTokenRepository,
            timeService );
    private AuthService sut = new AuthService( encoder, accountRepository, tokenResolver, verificationTokenRepository,
            propertiesProvider, timeService );

    @Test
    void shouldAddValidBasePrivilidges_whenDataValid() {

        // GIVEN
        timeService.setSystemZoneId( TEST_ZONE_ID );
        timeService.setCurrentSystemDateTime( TEST_CURRENT_TIME );
        when( encoder.encode( TEST_VALID_PASSWORD ) ).thenReturn( TEST_ENCODED_PASSWORD );
        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                TEST_VALID_PASSWORD );

        // WHEN
        var registeredAccountDO = sut.registerNewAccount( registrationDO );

        // THEN
        assertThat( registeredAccountDO.isAccountNonExpired() ).isTrue();
        assertThat( registeredAccountDO.isAccountNonLocked() ).isTrue();
        assertThat( registeredAccountDO.isCredentialsNonExpired() ).isTrue();
        assertThat( registeredAccountDO.isEnabled() ).isFalse();
    }

    @Test
    void shouldThrowAccountConfirmationException_whenUserAttachedToTokenDoesNotExist() {

        // GIVEN
        var accountDO = getDummyAccountDO();
        var token = new VerificationTokenDO( TEST_TOKEN_ID, TEST_TOKEN_UNIQUE_ID, EMAIL_CONFIRMATION_TOKEN, accountDO,
                TEST_TOKEN, Instant
                .now()
                .plus( 15, MINUTES ), true );
        TEST_TOKEN_DATABASE.add( token );

        // THEN
        assertThatThrownBy( () -> sut.confirmRegistration( TEST_TOKEN ) ).isInstanceOf(
                AccountConfirmationException.class );
    }

    @Test
    void shouldThrowAccountConfirmationException_whenTokenExpired() {

        // GIVEN
        var accountDO = getDummyAccountDO();
        TEST_ACCOUNT_DATABASE.add( accountDO );
        var token = new VerificationTokenDO( TEST_TOKEN_ID, TEST_TOKEN_UNIQUE_ID, EMAIL_CONFIRMATION_TOKEN, accountDO,
                TEST_TOKEN, Instant
                .now()
                .minus( 15, MINUTES ), true );
        TEST_TOKEN_DATABASE.add( token );

        // THEN
        assertThatThrownBy( () -> sut.confirmRegistration( TEST_TOKEN ) ).isInstanceOf(
                AccountConfirmationException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenTryingToConfirmAccountAndTokenIsNull() {
        assertThatThrownBy( () -> sut.confirmRegistration( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenTryingToConfirmAccountAndTokenIsEmpty() {
        assertThatThrownBy( () -> sut.confirmRegistration( EMPTY ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenRegistrationDOIsNull() {
        assertThatThrownBy( () -> sut.registerNewAccount( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenGeneratingConfirmationLinkAndAccountDOIsNull() {
        assertThatThrownBy( () -> sut.generateConfirmationLink( null ) ).isInstanceOf( NullPointerException.class );
    }

    private AccountDO getDummyAccountDO() {
        return AccountDO
                .builder()
                .username( TEST_VALID_USERNAME )
                .emailAddress( TEST_VALID_EMAIL )
                .enabled( false )
                .build();
    }
}