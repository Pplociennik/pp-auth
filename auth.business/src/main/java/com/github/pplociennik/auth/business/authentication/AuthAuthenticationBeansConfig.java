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

import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.business.shared.events.SystemEventsPublisher;
import com.github.pplociennik.auth.business.shared.system.SessionService;
import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A Spring's configuration class for authentication beans definition.
 *
 * @author Created by: Pplociennik at 31.01.2022 00:32
 */
@Configuration
class AuthAuthenticationBeansConfig {

    private final AccountRepository accountRepository;
    private final AuthenticationValidationRepository authenticationValidationRepository;
    private final PasswordEncoder encoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final TimeService timeService;
    private final SystemPropertiesProvider propertiesProvider;
    private final SystemEventsPublisher systemEventsPublisher;
    private final SessionService sessionService;

    @Autowired
    AuthAuthenticationBeansConfig(
            AccountRepository aAccountRepository,
            AuthenticationValidationRepository aAuthenticationValidationRepository, PasswordEncoder aEncoder,
            VerificationTokenRepository aVerificationTokenRepository, AuthenticationManager aAuthenticationManager,
            TimeService aTimeService, SystemPropertiesProvider aPropertiesProvider,
            SystemEventsPublisher aSystemEventsPublisher, SessionService aSessionService ) {
        accountRepository = aAccountRepository;
        authenticationValidationRepository = aAuthenticationValidationRepository;
        encoder = aEncoder;
        verificationTokenRepository = aVerificationTokenRepository;
        authenticationManager = aAuthenticationManager;
        timeService = aTimeService;
        propertiesProvider = aPropertiesProvider;
        systemEventsPublisher = aSystemEventsPublisher;
        sessionService = aSessionService;
    }

    @Bean
    AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacade( authService(), authenticationValidator(), systemEventsPublisher,
                authenticationManager, accountRepository, sessionService );
    }

    @Bean
    AuthenticationValidator authenticationValidator() {
        return new AuthenticationValidator( authenticationValidationRepository );
    }

    @Bean
    AuthService authService() {
        return new AuthService( encoder, accountRepository, verificationTokenResolver(), verificationTokenRepository,
                propertiesProvider, timeService );
    }

    @Bean
    VerificationTokenResolver verificationTokenResolver() {
        return new VerificationTokenResolver( verificationTokenRepository, timeService );
    }
}
