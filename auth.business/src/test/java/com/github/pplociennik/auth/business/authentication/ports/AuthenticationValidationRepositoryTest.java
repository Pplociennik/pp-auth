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

package com.github.pplociennik.auth.business.authentication.ports;

import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAuthenticationValidationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AuthenticationValidationRepository}.
 *
 * @author Created by: Pplociennik at 22.04.2022 17:15
 */
class AuthenticationValidationRepositoryTest {

    public static final String TEST_USERNAME_1 = "test_username_1";
    public static final String TEST_USERNAME_2 = "test_username_2";
    public static final String TEST_USERNAME_3 = "test_username_3";
    public static final String TEST_EMAIL_1 = "test1@testMail.com";
    public static final String TEST_EMAIL_2 = "test2@testMail.com";
    public static final String TEST_EMAIL_3 = "test3@testMail.com";
    public static final String NOT_EXISTING_TEST_USERNAME = "dokad_noca_tupta_jez?";
    public static final String NOT_EXISTING_TEST_EMAIL = "wrong@email.com";

    private final AuthenticationValidationRepository underTest = new InMemoryAuthenticationValidationRepository();

    @ParameterizedTest
    @ValueSource( strings = { TEST_USERNAME_1, TEST_USERNAME_2, TEST_USERNAME_3 } )
    void shouldReturnTrue_whenUsernameExists( String aUsername ) {
        assertThat( underTest.checkIfUsernameExists( aUsername ) ).isTrue();
    }

    @ParameterizedTest
    @ValueSource( strings = { TEST_EMAIL_1, TEST_EMAIL_2, TEST_EMAIL_3 } )
    void shouldReturnTrue_whenEmailExists( String aEmail ) {
        assertThat( underTest.checkIfEmailExists( aEmail ) ).isTrue();
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameNull() {
        assertThatThrownBy( () -> underTest.checkIfUsernameExists( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenEmailNull() {
        assertThatThrownBy( () -> underTest.checkIfEmailExists( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldReturnFalse_whenUsernameEmpty() {
        assertThat( underTest.checkIfUsernameExists( EMPTY ) ).isFalse();
    }

    @Test
    void shouldReturnFalse_whenEmailEmpty() {
        assertThat( underTest.checkIfEmailExists( EMPTY ) ).isFalse();
    }

    @Test
    void shouldReturnFalse_whenUsernameDoesNotExist() {
        assertThat( underTest.checkIfUsernameExists( NOT_EXISTING_TEST_USERNAME ) ).isFalse();
    }

    @Test
    void shouldReturnFalse_whenEmailDoesNotExist() {
        assertThat( underTest.checkIfEmailExists( NOT_EXISTING_TEST_EMAIL ) ).isFalse();
    }
}