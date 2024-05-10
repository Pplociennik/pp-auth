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

package com.github.pplociennik.auth.api.controller;

import auth.dto.AuthenticatedJWTDto;
import auth.dto.LoginDto;
import auth.dto.PasswordChangeRequestDto;
import auth.dto.RegistrationDto;
import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.authentication.domain.map.LoginMapper;
import com.github.pplociennik.commons.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.pplociennik.auth.api.controller.ApiMappingsConstants.*;
import static com.github.pplociennik.auth.business.authentication.domain.map.RegistrationMapper.mapToDO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * A controller class providing Auth endpoints.
 *
 * @author Created by: Pplociennik at 26.10.2021 18:11
 */
@RestController( value = AUTH_CONTROLLER_URL )
class AuthController {

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    AuthController( AuthenticationFacade aAuthenticationFacade ) {
        authenticationFacade = aAuthenticationFacade;
    }

    /**
     * Registers a new account in the system.
     *
     * @param aRegistrationDto
     *         data necessary for new account's creation.
     * @return {@link HttpStatus}
     */
    @PostMapping( path = AUTH_CONTROLLER_FULL_REGISTRATION_URL, consumes = APPLICATION_JSON_VALUE )
    HttpStatus registerNewUserAccount( @RequestBody RegistrationDto aRegistrationDto ) {
        var registrationDO = mapToDO( aRegistrationDto );
        authenticationFacade.registerNewAccount( registrationDO );
        return HttpStatus.ACCEPTED;
    }

    /**
     * Authenticates user in the system by login/email address and password.
     *
     * @param aLoginDto
     *         data necessary for user's authentication.
     * @return authentication data of the authenticated user
     */
    @PostMapping(
            path = AUTH_CONTROLLER_LOGIN_URL, consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity< AuthenticatedJWTDto > login( @RequestBody LoginDto aLoginDto ) {
        var loginDO = LoginMapper.mapToDomain( aLoginDto );
        var authenticatedUser = authenticationFacade.authenticateAccount( loginDO );
        return new ResponseEntity<>( authenticatedUser, HttpStatus.ACCEPTED );
    }

    /**
     * Confirms registration and enables the registered account in the system
     *
     * @param aToken
     *         a token being used for account confirmation. Being send automatically via an email message.
     * @return {@link HttpStatus}
     */
    @PostMapping( path = AUTH_CONTROLLER_ACCOUNT_CONFIRMATION_URL )
    HttpStatus confirmRegistration( @RequestParam String aToken ) {
        authenticationFacade.confirmRegistration( aToken );
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping(
            name = AUTH_CONTROLLER_PASSWORD_CHANGE_URL,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity< ResponseDto > changeAccountPassword( @RequestBody PasswordChangeRequestDto aRequestDto ) {
        return null;
    }
}
