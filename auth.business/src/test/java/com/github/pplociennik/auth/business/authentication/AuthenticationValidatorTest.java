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
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAuthenticationValidationRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.github.pplociennik.auth.business.testingUtils.ValidationExceptionMessageAssertions.assertSuppressedValidationMessagesContain;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.*;
import static com.github.pplociennik.commons.utility.LanguageUtil.getLocalizedMessage;
import static com.github.pplociennik.commons.utility.LanguageUtil.setLocale;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AuthenticationValidator};
 *
 * @author Created by: Pplociennik at 23.04.2022 11:50
 */
class AuthenticationValidatorTest {

    private static final String TEST_VALID_USERNAME = "ValidUserName";
    private static final String TEST_VALID_PASSWORD = "TestPass1!";
    private static final String TEST_NOT_EQUAL_PASSWORD = "TestPassDifferent1!";
    private static final String TEST_VALID_EMAIL = "testEmail@gmail.com";
    private static final String TEST_UNIQUE_ACCOUNT_OBJECT_ID = "Account#000000#000000#ValidUserName";
    private static final String TEST_VALID_EMAIL_SPEC_CHARS = "test#ema!il$^%@testdomain.com";
    private static final String TEST_VALID_EMAIL_NUMBERS = "testemail9548@testdomain.com";
    private static final String TEST_VALID_EMAIL_PERMITTED_SPEC_CHAR_DOMAIN = "testemail@test-domain.com";

    private static final String TEST_INVALID_USERNAME = "a";
    private static final String TEST_INVALID_PASSWORD = "i";
    private static final String TEST_INVALID_EMAIL = "invalidEmail";
    private static final String TEST_INVALID_EMAIL_LOCAL_SPACE = "test email@mail.com";
    private static final String TEST_INVALID_EMAIL_DOMAIN_SPACE = "testemail@test domain.com";
    private static final String TEST_INVALID_EMAIL_NOT_PERMITTED_SPEC_CHAR_DOMAIN = "testmail@test#domain.com";
    private static final String TEST_INVALID_EMAIL_TOO_LESS_CHARS_DOMAIN = "testemail@s.s";

    private static final String TEST_OCCUPIED_USERNAME = "InvalidUserName";
    private static final String TEST_OCCUPIED_EMAIL = "occupied@email.com";
    private List< AccountDO > TEST_DATABASE = new LinkedList<>();

    private InMemoryAuthenticationValidationRepository validationRepository = new InMemoryAuthenticationValidationRepository(
            TEST_DATABASE );
    private AuthenticationValidator sut = new AuthenticationValidator( validationRepository );

    @BeforeEach
    void prepare() {
        setLocale( ENGLISH );
    }

    // ##############################################
    // ################ Registration ################
    // ##############################################

    @Nested
    class Registration {

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenUsernameOccupied() {

            // GIVEN
            validationRepository.setUsernameExists( true );
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_OCCUPIED_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_USERNAME_ALREADY_IN_USE, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenEmailOccupied() {

            // GIVEN
            validationRepository.setEmailExists( true );
            var registrationDO = new RegistrationDO( TEST_OCCUPIED_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_EMAIL_ALREADY_IN_USE, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenUsernameInvalid() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_INVALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_USERNAME_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenPasswordInvalid() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_INVALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_PASSWORD_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenPasswordsNotEqual() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_NOT_EQUAL_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_PASSWORDS_NOT_EQUAL, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenSecondPasswordIsNull() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, null );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_PASSWORDS_NOT_EQUAL, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenUsernameIsEmpty() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, StringUtils.EMPTY, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_USERNAME_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenEmailIsEmpty() {

            // GIVEN
            var registrationDO = new RegistrationDO( StringUtils.EMPTY, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_EMAIL_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenPasswordIsEmpty() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, StringUtils.EMPTY,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_PASSWORD_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Test
        void shouldThrowValidationExceptionWithProperSuppressedExceptionMessage_whenSecondPasswordIsEmpty() {

            // GIVEN
            var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     StringUtils.EMPTY );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_PASSWORDS_NOT_EQUAL, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @ParameterizedTest
        @ValueSource( strings = { TEST_VALID_EMAIL, TEST_VALID_EMAIL_NUMBERS, TEST_VALID_EMAIL_SPEC_CHARS, TEST_VALID_EMAIL_PERMITTED_SPEC_CHAR_DOMAIN } )
        void shouldNotFail_whenThereIsCorrectEmailAddressAndTheRestOfDataValid( String aEmailAddress ) {

            // GIVEN
            var registrationDO = new RegistrationDO( aEmailAddress, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            sut.validateRegistration( registrationDO );
        }

        @ParameterizedTest
        @ValueSource( strings = { TEST_INVALID_EMAIL, TEST_INVALID_EMAIL_DOMAIN_SPACE, TEST_INVALID_EMAIL_LOCAL_SPACE, TEST_INVALID_EMAIL_TOO_LESS_CHARS_DOMAIN, TEST_INVALID_EMAIL_NOT_PERMITTED_SPEC_CHAR_DOMAIN } )
        void shouldThrowValidationException_whenEmailInvalid( String aEmailAddress ) {

            // GIVEN
            var registrationDO = new RegistrationDO( aEmailAddress, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                     TEST_VALID_PASSWORD );

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_EMAIL_NOT_MATCHING_PATTERN, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistration( registrationDO ), message );
        }

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenRegistrationDOIsNull() {

                // GIVEN
                RegistrationDO registrationDO = null;

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateRegistration( registrationDO ) ).isInstanceOf(
                        NullPointerException.class );
            }

            @Test
            void shouldThrowNullPointerException_whenUsernameIsNull() {

                // GIVEN
                var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, null, TEST_VALID_PASSWORD,
                                                         TEST_VALID_PASSWORD );

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateRegistration( registrationDO ) ).isInstanceOf(
                        NullPointerException.class );
            }

            @Test
            void shouldThrowNullPointerException_whenEmailIsNull() {

                // GIVEN
                var registrationDO = new RegistrationDO( null, TEST_VALID_USERNAME, TEST_VALID_PASSWORD,
                                                         TEST_VALID_PASSWORD );

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateRegistration( registrationDO ) ).isInstanceOf(
                        NullPointerException.class );
            }

            @Test
            void shouldThrowNullPointerException_whenPasswordIsNull() {

                // GIVEN
                var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, null,
                                                         TEST_VALID_PASSWORD );

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateRegistration( registrationDO ) ).isInstanceOf(
                        NullPointerException.class );
            }
        }

    }

    // ##############################################
    // ######## Confirmation Link Generation ########
    // ##############################################

    @Nested
    class ConfirmationLinkGeneration {

        @Test
        void shouldThrowValidationExceptionWithAProperMessage_whenAccountDoesNotExist() {

            // GIVEN
            validationRepository.setEmailExists( false );
            var accountDO = AccountDO
                    .builder()
                    .emailAddress( TEST_VALID_EMAIL )
                    .uniqueObjectIdentifier( TEST_UNIQUE_ACCOUNT_OBJECT_ID )
                    .build();

            // WHEN
            // THEN
            var message = getLocalizedMessage( AUTHENTICATION_USER_DOES_NOT_EXIST, ENGLISH );
            assertSuppressedValidationMessagesContain(
                    () -> sut.validateConfirmationLinkGeneration( accountDO.getUniqueObjectIdentifier() ), message );
        }

        @Test
        void shouldSucceed_whenAccountExists() {

            // GIVEN
            validationRepository.setAccountExistsByUniqueId( true );
            var accountDO = AccountDO
                    .builder()
                    .emailAddress( TEST_VALID_EMAIL )
                    .uniqueObjectIdentifier( TEST_UNIQUE_ACCOUNT_OBJECT_ID )
                    .build();

            // WHEN
            // THEN
            sut.validateConfirmationLinkGeneration( accountDO.getUniqueObjectIdentifier() );
        }

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenNullGivenAsParameter() {

                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateConfirmationLinkGeneration( null ) ).isInstanceOf(
                        NullPointerException.class );
            }

        }


    }

    // ##############################################
    // ############ Account Confirmation ############
    // ##############################################

    @Nested
    class AccountConfirmation {

        @Test
        void shouldThrowValidationExceptionWithAProperMessage_whenTokenIsNotActive() {

            // GIVEN
            final var givenToken = "SimpleToken";
            validationRepository.setTokenActive( false );

            // WHEN
            // THEN
            var message = getLocalizedMessage( ACCOUNT_CONFIRMATION_TOKEN_NOT_ACTIVE, ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistrationConfirmation( givenToken ),
                                                       message );
        }

        @Test
        void shouldThrowValidationExceptionWithAProperMessage_whenTokenDoesNotExist() {

            // GIVEN
            var token = "simpleDummyToken";
            validationRepository.setTokenExists( false );

            // WHEN
            // THEN
            var message = getLocalizedMessage( ACCOUNT_CONFIRMATION_TOKEN_NOT_FOUND, Locale.ENGLISH );
            assertSuppressedValidationMessagesContain( () -> sut.validateRegistrationConfirmation( token ), message );
        }

        @Test
        void shouldSucceed_whenTokenIsActive() {

            // GIVEN
            final var givenToken = "SimpleToken";
            validationRepository.setTokenExists( true );
            validationRepository.setTokenActive( true );

            // WHEN
            // THEN
            sut.validateRegistrationConfirmation( givenToken );
        }

        @Nested
        class CornerCases {

            @Test
            void shouldThrowNullPointerException_whenTokenNull() {

                // GIVEN
                // WHEN
                // THEN
                assertThatThrownBy( () -> sut.validateRegistrationConfirmation( null ) ).isInstanceOf(
                        NullPointerException.class );
            }

            @Test
            void shouldThrowValidationException_whenTokenEmpty() {

                // GIVEN
                // WHEN
                // THEN
                var message = getLocalizedMessage( NO_DATA_PROVIDED, ENGLISH );
                assertSuppressedValidationMessagesContain(
                        () -> sut.validateRegistrationConfirmation( StringUtils.EMPTY ), message );
            }

        }

    }
}