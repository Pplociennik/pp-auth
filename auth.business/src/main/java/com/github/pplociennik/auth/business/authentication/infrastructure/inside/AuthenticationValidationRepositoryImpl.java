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

package com.github.pplociennik.auth.business.authentication.infrastructure.inside;

import com.github.pplociennik.auth.business.authentication.ports.inside.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.inside.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.ports.inside.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 *
 * @author Created by: Pplociennik at 25.01.2022 23:20
 */
class AuthenticationValidationRepositoryImpl implements AuthenticationValidationRepository {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    AuthenticationValidationRepositoryImpl( @NonNull AccountRepository aAccountRepository, VerificationTokenRepository aTokenRepository ) {
        accountRepository = aAccountRepository;
        tokenRepository = aTokenRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfUsernameExists( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return accountRepository.existsAccountByUsername( aUsername );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfEmailExists( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        return accountRepository.existsAccountByEmailAddress( aEmail );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfTokenActive( @NonNull String aToken ) {
        requireNonNull( aToken );

        var verificationToken = tokenRepository.findByToken( aToken );
        return verificationToken.isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfTokenExists( @NonNull String aToken ) {
        requireNonNull( aToken );

        var verificationToken = tokenRepository.findByToken( aToken );
        return verificationToken != null;
    }
}
