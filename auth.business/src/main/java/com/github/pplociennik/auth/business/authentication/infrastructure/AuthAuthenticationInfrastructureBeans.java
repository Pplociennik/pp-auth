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

package com.github.pplociennik.auth.business.authentication.infrastructure;

import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.AccountSecurityDataService;
import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.db.repository.authentication.SpringAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A Spring's configuration class for authentication beans definition. Infrastructure package part.
 *
 * @author Created by: Pplociennik at 13.10.2021 17:42
 */
@Configuration
public class AuthAuthenticationInfrastructureBeans {

    private final SpringAccountRepository springAccountRepository;

    @Autowired
    public AuthAuthenticationInfrastructureBeans( SpringAccountRepository aSpringAccountRepository ) {
        springAccountRepository = aSpringAccountRepository;
    }

    @Bean
    AccountRepository accountRepository() {
        return new AccountRepositoryImpl( springAccountRepository );
    }

    @Bean
    AccountSecurityDataService accountSecurityDataService() {
        return new AccountSecurityDataServiceImpl( accountRepository() );
    }

    @Bean
    AuthenticationValidationRepository authenticationValidationRepository() {
        return new AuthenticationValidationRepositoryImpl( accountRepository() );
    }

}
