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

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAccountRepository;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryTimeService;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryVerificationTokenRepository;
import com.github.pplociennik.auth.business.shared.system.EnvironmentPropertiesProvider;
import com.github.pplociennik.util.utility.LanguageUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.github.pplociennik.auth.business.authentication.data.AuthenticationFacadeTestDataProvider.prepareDummyRegistrationDO;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.business.shared.system.SystemProperty.GLOBAL_CLIENT_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link AuthenticationFacade}.
 *
 * @author Created by: Pplociennik at 23.04.2022 11:04
 */
class AuthenticationFacadeTest {

    private static final ZoneId TEST_ZONE_ID = ZoneId.of( "UTC" );
    private static final ZonedDateTime TEST_CURRENT_TIME = ZonedDateTime.now( TEST_ZONE_ID );

    private AuthService authService;
    private AuthenticationValidator validator;
    private PasswordEncoder encoder;
    private InMemoryAccountRepository accountRepository;
    private InMemoryAuthenticationValidationRepository validationRepository;
    private ApplicationEventPublisher eventPublisher;
    private VerificationTokenResolver tokenResolver;
    private InMemoryVerificationTokenRepository verificationTokenRepository;
    private EnvironmentPropertiesProvider propertiesProvider;
    private InMemoryTimeService timeService;
    private AuthenticationFacade sut;

    @BeforeEach
    void prepare() {
        prepareEncoder();
        prepareValidator();
        prepareRepository();
        prepareEventPublisher();
        preparePropertiesProvider();
        prepareTokenRepository();
        prepareTokenResolver();
        prepareTimeService();

        authService = new AuthService( encoder, accountRepository, tokenResolver, verificationTokenRepository,
                                       propertiesProvider, timeService );
        validator = new AuthenticationValidator( validationRepository );

        sut = new AuthenticationFacade( authService, validator, eventPublisher );
        LanguageUtil.setLocale( Locale.ENGLISH );
    }

    @Nested
    class Registration {

        @Test
        void shouldRegisterNewAccount_whenAllDataValid() {

            // GIVEN
            timeService.setSystemZoneId( TEST_ZONE_ID );
            timeService.setCurrentSystemDateTime( TEST_CURRENT_TIME );
            final var hashedPass = "encodedPassword";
            var registrationDO = prepareDummyRegistrationDO();
            when( encoder.encode( registrationDO.getPassword() ) ).thenReturn( hashedPass );

            // WHEN
            final var registeredAccount = sut.registerNewAccount( registrationDO );

            // THEN
            final var expectedAccount = AccountDto
                    .builder()
                    .username( registrationDO.getUsername() )
                    .emailAddress( registrationDO.getEmail() )
                    .accountNonExpired( true )
                    .accountNonLocked( true )
                    .credentialsNonExpired( true )
                    .enabled( false )
                    .authorities( Set.of( AUTH_USER_ROLE.getName() ) )
                    .build();
            assertThat( expectedAccount ).isEqualTo( registeredAccount );
        }

        @Test
        void shouldNewlyRegisteredAccountHaveBasePrivilegesSetProperly() {

            // GIVEN
            timeService.setSystemZoneId( TEST_ZONE_ID );
            timeService.setCurrentSystemDateTime( TEST_CURRENT_TIME );
            final var hashedPass = "encodedPassword";
            final var registrationDO = prepareDummyRegistrationDO();
            when( encoder.encode( registrationDO.getPassword() ) ).thenReturn( hashedPass );

            // WHEN
            var registeredAccount = sut.registerNewAccount( registrationDO );

            // THEN
            assertThat( registeredAccount.isAccountNonExpired() ).isTrue();
            assertThat( registeredAccount.isAccountNonLocked() ).isTrue();
            assertThat( registeredAccount.isCredentialsNonExpired() ).isTrue();
            assertThat( registeredAccount.isEnabled() ).isFalse();
        }

        @Test
        void shouldNewlyRegisteredAccountHaveBaseAuthoritiesSetProperly() {

            // GIVEN
            timeService.setSystemZoneId( TEST_ZONE_ID );
            timeService.setCurrentSystemDateTime( TEST_CURRENT_TIME );
            final var hashedPass = "encodedPassword";
            final var registrationDO = prepareDummyRegistrationDO();
            when( encoder.encode( registrationDO.getPassword() ) ).thenReturn( hashedPass );

            // WHEN
            var registeredAccount = sut.registerNewAccount( registrationDO );

            // THEN
            final var expectedAuthorities = List.of( AUTH_USER_ROLE.getName() );
            assertThat( registeredAccount.getAuthorities() ).containsAll( expectedAuthorities );
        }

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenRegistrationDOIsNull() {
                assertThatThrownBy( () -> sut.registerNewAccount( null ) ).isInstanceOf( NullPointerException.class );
            }
        }

    }

    // ##############################################
    // ################ Registration ################
    // ##############################################

    @Nested
    class ConfirmationLinkGeneration {

        @Test
        void shouldReturnValidConfirmationLink_whenDataValid() {

            // GIVEN
            final var EXPECTED_CLIENT_URL = "http://localhost:8080";
            var accountDO = AccountDO
                    .builder()
                    .emailAddress( "test@email.com" )
                    .build();
            validationRepository.setEmailExists( true );
            when( propertiesProvider.getPropertyValue( GLOBAL_CLIENT_URL ) ).thenReturn( EXPECTED_CLIENT_URL );

            // WHEN
            var result = sut.createNewAccountConfirmationLink( accountDO );

            // THEN
            final var EXPECTED_URL_PART = "http://localhost:8080/?aToken=";
            assertThat( result ).contains( EXPECTED_URL_PART );
        }

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenNullGivenAsParameter() {

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.createNewAccountConfirmationLink( null ) ).isInstanceOf(
                        NullPointerException.class );
            }
        }
    }

    // ##############################################
    // ######## Confirmation Link Generation ########
    // ##############################################

    @Nested
    class AccountConfirmation {

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenTokenIsNull() {

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.confirmRegistration( null ) ).isInstanceOf( NullPointerException.class );
            }

            @Test
            void shouldThrowNullPointerException_whenTokenIsEmpty() {

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.confirmRegistration( StringUtils.EMPTY ) ).isInstanceOf(
                        NullPointerException.class );
            }
        }
    }

    // ##############################################
    // ############ Account Confirmation ############
    // ##############################################

    private void prepareTimeService() {
        timeService = new InMemoryTimeService();
    }

    private void preparePropertiesProvider() {
        propertiesProvider = mock( EnvironmentPropertiesProvider.class );
    }

    private void prepareTokenRepository() {
        verificationTokenRepository = new InMemoryVerificationTokenRepository();
    }

    private void prepareTokenResolver() {
        tokenResolver = new VerificationTokenResolver( verificationTokenRepository );
    }

    private void prepareEventPublisher() {
        eventPublisher = mock( ApplicationEventPublisher.class );
    }

    private void prepareRepository() {
        accountRepository = new InMemoryAccountRepository();
    }

    private void prepareValidator() {
        validationRepository = new InMemoryAuthenticationValidationRepository();
    }

    private void prepareEncoder() {
        encoder = mock( PasswordEncoder.class );
    }


}