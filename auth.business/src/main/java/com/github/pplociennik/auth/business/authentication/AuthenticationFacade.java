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
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.auth.common.auth.dto.AccountDto;
import com.github.pplociennik.util.utility.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToDto;
import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;

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
        requireNonNull( aRegistrationDO );
        validationService.validateRegistration( aRegistrationDO );
        var registeredAccount = mapToDto( authService.registerNewAccount( aRegistrationDO ) );
        publishEventOnRegistrationFinished( registeredAccount );
        return registeredAccount;
    }

    /**
     * Returns a confirmation link for the specified account.
     *
     * @param aAccountDO
     *         the account for which the confirmation link is going to be generated.
     * @return the confirmation link as a {@link String}.
     */
    public String createNewAccountConfirmationLink( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );
        validationService.validateConfirmationLinkGeneration( aAccountDO );
        return authService.generateConfirmationLink( aAccountDO );
    }

    /**
     * Confirms (enables) the account if the specified token is valid.
     *
     * @param aToken
     *         the verification token.
     * @throws NullPointerException
     *         when the token is null or empty.
     */
    public void confirmRegistration( @NonNull String aToken ) {
        requireNonEmpty( aToken );
        validationService.validateRegistrationConfirmation( aToken );
        authService.confirmRegistration( aToken );
    }

    private void publishEventOnRegistrationFinished( @NonNull AccountDto aRegisteredAccount ) {
        requireNonNull( aRegisteredAccount );

        var event = new OnRegistrationCompleteEvent( aRegisteredAccount, LanguageUtil.getLocale() );
        eventPublisher.publishEvent( event );
    }
}
