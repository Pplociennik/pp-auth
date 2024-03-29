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

import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import com.github.pplociennik.auth.db.repository.authentication.AuthorityDao;
import com.github.pplociennik.auth.db.repository.authentication.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A Spring's configuration class for authentication beans definition. Infrastructure package part for incoming ports.
 *
 * @author Created by: Pplociennik at 13.10.2021 17:42
 */
@Configuration
class AuthAuthenticationInfrastructureOutputBeans {

    private final AccountDao accountDao;
    private final VerificationTokenDao verificationTokenDao;
    private final AuthorityDao authorityDao;

    @Autowired
    AuthAuthenticationInfrastructureOutputBeans(
            @NonNull AccountDao aAccountDao, @NonNull VerificationTokenDao aVerificationTokenDao,
            AuthorityDao aAuthorityDao ) {
        accountDao = requireNonNull( aAccountDao );
        verificationTokenDao = requireNonNull( aVerificationTokenDao );
        authorityDao = aAuthorityDao;
    }

    @Bean
    AccountRepository accountRepository() {
        return new AccountRepositoryImpl( accountDao, authorityDao );
    }

    @Bean
    VerificationTokenRepository verificationTokenRepository() {
        return new VerificationTokenRepositoryImpl( verificationTokenDao, accountDao );
    }

    @Bean
    AuthenticationValidationRepository authenticationValidationRepository() {
        return new AuthenticationValidationRepositoryImpl( accountRepository(), verificationTokenRepository() );
    }

}
