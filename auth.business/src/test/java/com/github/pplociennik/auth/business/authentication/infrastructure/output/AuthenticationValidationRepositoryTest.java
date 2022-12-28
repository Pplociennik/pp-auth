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

package com.github.pplociennik.auth.business.authentication.infrastructure.output;

import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAccountRepository;
import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryVerificationTokenRepository;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AuthenticationValidationRepository}.
 *
 * @author Created by: Pplociennik at 22.04.2022 17:15
 */
class AuthenticationValidationRepositoryTest {
    private InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();
    private InMemoryVerificationTokenRepository tokenRepository = new InMemoryVerificationTokenRepository();
    private final AuthenticationValidationRepository sut = new AuthenticationValidationRepositoryImpl(
            accountRepository, tokenRepository );

    @Test
    void shouldThrowNullPointerException_whenUsernameNull() {
        assertThatThrownBy( () -> sut.checkIfUsernameExists( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenEmailNull() {
        assertThatThrownBy( () -> sut.checkIfEmailExists( null ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldReturnFalse_whenUsernameEmpty() {
        assertThat( sut.checkIfUsernameExists( EMPTY ) ).isFalse();
    }

    @Test
    void shouldReturnFalse_whenEmailEmpty() {
        assertThat( sut.checkIfEmailExists( EMPTY ) ).isFalse();
    }

}