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
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link AuthenticationFacade}.
 *
 * @author Created by: Pplociennik at 23.04.2022 11:04
 */
class AuthenticationFacadeTest {

    private static final String TEST_VALID_USERNAME = "ValidUserName";
    private static final String TEST_VALID_PASSWORD = "TestPass1!";
    private static final String TEST_ENCODED_VALID_PASSWORD = "EncodedPass";
    private static final String TEST_VALID_EMAIL = "testEmail@gmail.com";

    private static final String TEST_OCCUPIED_USERNAME = "InvalidUserName";
    private static final String TEST_OCCUPIED_EMAIL = "occupied@email.com";

    private AuthService authService;
    private AuthenticationValidator validator;
    private PasswordEncoder encoder;
    private AccountRepository accountRepository;
    private AuthenticationValidationRepository validationRepository;
    private AuthenticationFacade underTest;

    @BeforeEach
    void prepareMocks() {
        prepareEncoder();
        prepareValidator();
        prepareRepository();

        authService = new AuthService( encoder, accountRepository );
        validator = new AuthenticationValidator( validationRepository );

        underTest = new AuthenticationFacade( authService, validator );
    }

    private void prepareRepository() {
        accountRepository = mock( AccountRepository.class );
    }

    private void prepareValidator() {
        validationRepository = mock( AuthenticationValidationRepository.class );

        when( validationRepository.checkIfEmailExists( TEST_VALID_EMAIL ) ).thenReturn( FALSE );
        when( validationRepository.checkIfUsernameExists( TEST_VALID_USERNAME ) ).thenReturn( FALSE );

        when( validationRepository.checkIfEmailExists( TEST_OCCUPIED_EMAIL ) ).thenReturn( TRUE );
        when( validationRepository.checkIfUsernameExists( TEST_OCCUPIED_USERNAME ) ).thenReturn( TRUE );
    }

    private void prepareEncoder() {
        encoder = mock( PasswordEncoder.class );

        when( encoder.encode( TEST_VALID_PASSWORD ) ).thenReturn( TEST_ENCODED_VALID_PASSWORD );
    }

    @Test
    void shouldRegisterNewAccount_whenAllDataValid() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        underTest.registerNewAccount( registrationDO );
    }

    @Test
    void shouldThrowNullPointerException_whenRegistrationDOIsNull() {
        assertThatThrownBy( () -> underTest.registerNewAccount( null ) ).isInstanceOf( NullPointerException.class );
    }


}