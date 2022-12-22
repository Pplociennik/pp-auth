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
import com.github.pplociennik.auth.business.authentication.ports.inside.AuthenticationValidationRepository;
import com.github.pplociennik.commons.validation.ValidatorIf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.*;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;

/**
 * Validation service for authentication process.
 *
 * @author Created by: Pplociennik at 22.12.2021 23:20
 */
class AuthenticationValidator {

    private static final Pattern USERNAME_PATTERN = compile( "^[a-zA-Z0-9]{6,15}$" );
    private static final Pattern PASSWORD_PATTERN = compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$&+,:;=?@#|'<>.^*()%!-]).{8,128}$" );
    private static final Pattern EMAIL_PATTERN = compile( "[a-zA-Z0-9!#$%&'*+-\\/=?^_`{|}~]+@[a-z0-9-]{2,}\\.[a-z]{2,}",
                                                          Pattern.CASE_INSENSITIVE );
    private final AuthenticationValidationRepository authenticationValidationRepository;

    @Autowired
    AuthenticationValidator( @NonNull AuthenticationValidationRepository aAuthenticationValidationRepository ) {
        authenticationValidationRepository = aAuthenticationValidationRepository;
    }

    void validateRegistration( @NonNull RegistrationDO aRegistrationDO ) {

        ValidatorIf
                .of( aRegistrationDO )
                .validate( Objects::nonNull, AUTHENTICATION_NO_REGISTRATION_DATA )
                .validate( RegistrationDO::getUsername, this::checkIfUsernameCorrect,
                           AUTHENTICATION_USERNAME_NOT_MATCHING_PATTERN )
                .validate( RegistrationDO::getPassword, this::checkIfPasswordCorrect,
                           AUTHENTICATION_PASSWORD_NOT_MATCHING_PATTERN )
                .validate( RegistrationDO::getEmail, this::checkIfEmailCorrect,
                           AUTHENTICATION_EMAIL_NOT_MATCHING_PATTERN )
                .validate( this::checkIfPasswordsEqual, AUTHENTICATION_PASSWORDS_NOT_EQUAL )
                .validate( RegistrationDO::getUsername, this::checkIfUsernameNotExists,
                           AUTHENTICATION_USERNAME_ALREADY_IN_USE )
                .validate( RegistrationDO::getEmail, this::checkIfEmailNotExists, AUTHENTICATION_EMAIL_ALREADY_IN_USE )
                .perform();
    }

    void validateConfirmationLinkGeneration( @NonNull AccountDO aAccountDO ) {

        ValidatorIf
                .of( aAccountDO )
                .validate( Objects::nonNull, NO_DATA_PROVIDED )
                .validate( AccountDO::getEmailAddress, this::checkIfAccountExists, AUTHENTICATION_USER_DOES_NOT_EXIST )
                .perform();
    }

    public void validateRegistrationConfirmation( String aToken ) {

        ValidatorIf
                .of( aToken )
                .validate( StringUtils::isNotBlank, NO_DATA_PROVIDED )
                .validate( this::checkIfTokenExists, ACCOUNT_CONFIRMATION_TOKEN_NOT_FOUND )
                .validate( this::checkIfTokenActive, ACCOUNT_CONFIRMATION_TOKEN_NOT_ACTIVE )
                .perform();
    }

    private boolean checkIfTokenExists( String aToken ) {
        return authenticationValidationRepository.checkIfTokenExists( aToken );
    }

    private boolean checkIfTokenActive( String aToken ) {
        return authenticationValidationRepository.checkIfTokenActive( aToken );
    }

    private boolean checkIfAccountExists( String aEmail ) {
        return authenticationValidationRepository.checkIfEmailExists( aEmail );
    }

    private boolean checkIfEmailNotExists( String aEmail ) {
        requireNonNull( aEmail );
        return ! authenticationValidationRepository.checkIfEmailExists( aEmail );
    }

    private boolean checkIfUsernameNotExists( String aUsername ) {
        requireNonNull( aUsername );
        return ! authenticationValidationRepository.checkIfUsernameExists( aUsername );
    }

    private boolean checkIfUsernameCorrect( String aUsername ) {
        return checkIfMatches( aUsername, USERNAME_PATTERN );
    }

    private boolean checkIfPasswordCorrect( String aPassword ) {
        return checkIfMatches( aPassword, PASSWORD_PATTERN );
    }

    private boolean checkIfEmailCorrect( String aEmail ) {
        return checkIfMatches( aEmail, EMAIL_PATTERN );
    }

    private boolean checkIfPasswordsEqual( RegistrationDO aRegistrationDO ) {
        return aRegistrationDO
                .getPassword()
                .equals( aRegistrationDO.getRepeatedPassword() );
    }

    private boolean checkIfMatches( String aText, Pattern aPattern ) {
        var matcher = aPattern.matcher( aText );
        return matcher.matches();
    }
}
