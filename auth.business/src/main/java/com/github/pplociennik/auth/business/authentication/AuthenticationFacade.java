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
import com.github.pplociennik.auth.common.auth.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToDto;

/**
 * A facade sharing functionalities for authentication process.
 *
 * @author Created by: Pplociennik at 26.10.2021 19:18
 */
public class AuthenticationFacade {

    private final AuthService authService;
    private final AuthenticationValidator validationService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuthenticationFacade( @NonNull AuthService aAuthService, @NonNull AuthenticationValidator aValidationService, @NonNull ApplicationEventPublisher aEventPublisher ) {
        authService = aAuthService;
        validationService = aValidationService;
        eventPublisher = aEventPublisher;
    }

    /**
     * Creates a new user account.
     *
     * @param aRegistrationDO
     *         a registration data necessary for a new account creation. The data is being validated before the process starts.
     */
    public AccountDto registerNewAccount( @NonNull RegistrationDO aRegistrationDO ) {
        validationService.validateRegistration( aRegistrationDO );
        return mapToDto( authService.registerNewAccount( aRegistrationDO ) );
    }

    public String createNewAccountConfirmationLink( @NonNull AccountDO aAccount ) {
        validationService.validateConfirmationLinkGeneration( aAccount );
        return authService.generateConfirmationLink( aAccount );
    }

    public void confirmRegistration( @NonNull String aToken ) {
        validationService.validateRegistrationConfirmation( aToken );
        authService.confirmRegistration( aToken );
    }
}
