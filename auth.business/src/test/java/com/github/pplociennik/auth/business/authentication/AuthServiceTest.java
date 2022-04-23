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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private AccountRepository accountRepository;
    private PasswordEncoder encoder;

    private AuthService underTest;

    @BeforeEach
    void prepareMocks() {
        accountRepository = mock( AccountRepository.class );
        encoder = mock( PasswordEncoder.class );

        when( encoder.encode( TEST_VALID_PASSWORD ) ).thenReturn( TEST_ENCODED_PASSWORD );

        underTest = new AuthService( encoder, accountRepository );
    }

    @Test
    void shouldRegisterNewAccount_whenDataValid() {

        var registrationDO = new RegistrationDO( TEST_VALID_EMAIL, TEST_VALID_USERNAME, TEST_VALID_PASSWORD, TEST_VALID_PASSWORD );

        underTest.registerNewAccount( registrationDO );
    }

    @Test
    void shouldThrowNullPointerException_whenRegistrationDOIsNull() {
        assertThatThrownBy( () -> underTest.registerNewAccount( null ) ).isInstanceOf( NullPointerException.class );
    }
}