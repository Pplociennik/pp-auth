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

import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.common.exc.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    private static final String TEST_INVALID_USERNAME = "a";
    private static final String TEST_INVALID_PASSWORD = "i";
    private static final String TEST_INVALID_EMAIL = "invalidEmail";

    private static final String TEST_OCCUPIED_USERNAME = "InvalidUserName";
    private static final String TEST_OCCUPIED_EMAIL = "occupied@email.com";

    private AuthenticationValidationRepository validationRepository;
    private AuthenticationValidator underTest;

    @BeforeEach
    void prepareMocks() {
        validationRepository = mock( AuthenticationValidationRepository.class );

        when( validationRepository.checkIfUsernameExists( TEST_OCCUPIED_USERNAME ) ).thenReturn( TRUE );
        when( validationRepository.checkIfEmailExists( TEST_OCCUPIED_EMAIL ) ).thenReturn( TRUE );
        when( validationRepository.checkIfUsernameExists( TEST_VALID_USERNAME ) ).thenReturn( FALSE );
        when( validationRepository.checkIfEmailExists( TEST_VALID_EMAIL ) ).thenReturn( FALSE );

        underTest = new AuthenticationValidator( validationRepository );
    }

    @Test
    void shouldThrowValidationException_whenUsernameOccupied() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_OCCUPIED_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }

    @Test
    void shouldThrowValidationException_whenEmailOccupied() {

        var registrationDO = new RegistrationDO( TEST_OCCUPIED_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }

    @Test
    void shouldThrowValidationException_whenUsernameInvalid() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_INVALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }

    @Test
    void shouldThrowValidationException_whenEmailInvalid() {

        var registrationDO = new RegistrationDO( TEST_INVALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }

    @Test
    void shouldThrowValidationException_whenPasswordInvalid() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_INVALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }

    @Test
    void shouldThrowValidationException_whenPasswordsNotEqual() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_NOT_EQUAL_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }


    @Test
    void shouldThrowNullPointerException_whenRegistrationDOIsNull() {

        RegistrationDO registrationDO = null;

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsNull() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, null, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenEmailIsNull() {

        var registrationDO = new RegistrationDO( null, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenPasswordIsNull() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, null, TEST_VALID_PASSWORD );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowValidationException_whenSecondPasswordIsNull() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, null );

        assertThatThrownBy( () -> underTest.validateRegistration( registrationDO ) ).isInstanceOf( ValidationException.class );
    }
}