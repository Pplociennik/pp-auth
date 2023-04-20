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

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.authentication.domain.model.LoginDO;
import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.shared.events.SystemEventsPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.github.pplociennik.auth.business.authentication.AuthenticationPublishableEventsSupplier.ON_ACCOUNT_CONFIRMATION_FINISHED;
import static com.github.pplociennik.auth.business.authentication.AuthenticationPublishableEventsSupplier.ON_REGISTRATION_FINISHED;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToConfirmationLinkGenerationDto;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToDto;
import static com.github.pplociennik.commons.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;

/**
 * A facade sharing functionalities for authentication process.
 *
 * @author Created by: Pplociennik at 26.10.2021 19:18
 */
public class AuthenticationFacade {

    private final AuthService authService;
    private final AuthenticationValidator validationService;
    private final SystemEventsPublisher systemEventsPublisher;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationFacade(
            @NonNull AuthService aAuthService, @NonNull AuthenticationValidator aValidationService,
            SystemEventsPublisher aSystemEventsPublisher, AuthenticationManager aAuthenticationManager,
            AccountRepository aAccountRepository ) {
        authService = aAuthService;
        validationService = aValidationService;
        systemEventsPublisher = aSystemEventsPublisher;
        authenticationManager = aAuthenticationManager;
        accountRepository = aAccountRepository;
    }

    /**
     * Creates a new user account.
     *
     * @param aRegistrationDO
     *         a registration data necessary for a new account creation. The data is being validated before the process
     *         starts.
     */
    public AccountDto registerNewAccount( @NonNull RegistrationDO aRegistrationDO ) {
        requireNonNull( aRegistrationDO );
        validationService.validateRegistration( aRegistrationDO );
        var registeredAccount = authService.registerNewAccount( aRegistrationDO );
        var confirmationLinkData = mapToConfirmationLinkGenerationDto( registeredAccount );
        systemEventsPublisher.publishEvent( ON_REGISTRATION_FINISHED, confirmationLinkData );
        return mapToDto( registeredAccount );
    }

    /**
     * Returns a confirmation link for the specified account.
     *
     * @param aUniqueAccountIdentifier
     *         the account for which the confirmation link is going to be generated.
     * @return the confirmation link as a {@link String}.
     */
    public String createNewAccountConfirmationLink( @NonNull String aUniqueAccountIdentifier ) {
        requireNonNull( aUniqueAccountIdentifier );
        validationService.validateConfirmationLinkGeneration( aUniqueAccountIdentifier );
        return authService.generateConfirmationLink( aUniqueAccountIdentifier );
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
        var enabledAccount = mapToDto( authService.confirmRegistration( aToken ) );
        systemEventsPublisher.publishEvent( ON_ACCOUNT_CONFIRMATION_FINISHED, enabledAccount );
    }

    /**
     * Validates login data and authenticates user.
     *
     * @param aLoginDO
     *         a login data.
     */
    public void authenticateAccount( @NonNull LoginDO aLoginDO ) {
        requireNonNull( aLoginDO );
        validationService.validateLoginData( aLoginDO );
        var account = accountRepository.findAccountByUsernameOrEmail( aLoginDO.getUsernameOrEmail() );
        validationService.preValidateLogin( aLoginDO );
        var token = new UsernamePasswordAuthenticationToken( account.getUsername(), account.getPassword() );
        var authenticationObject = authenticationManager.authenticate( token );
        SecurityContextHolder
                .getContext()
                .setAuthentication( authenticationObject );
    }
}
