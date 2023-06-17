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

import auth.dto.AuthenticatedUserDto;
import auth.dto.LoginDto;
import auth.dto.RegistrationDto;
import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.authentication.domain.map.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.github.pplociennik.auth.api.controller.ApiMappingsConstants.*;
import static com.github.pplociennik.auth.business.authentication.domain.map.RegistrationMapper.mapToDO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * A controller class providing Auth endpoints.
 *
 * @author Created by: Pplociennik at 26.10.2021 18:11
 */
@RestController(value = AUTH_CONTROLLER_MAPPING_VALUE)
class AuthController {

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    AuthController( AuthenticationFacade aAuthenticationFacade ) {
        authenticationFacade = aAuthenticationFacade;
    }

    @PostMapping(path = AUTH_CONTROLLER_FULL_REGISTRATION_MAPPING_VALUE, consumes = APPLICATION_JSON_VALUE)
    HttpStatus registerNewUserAccount( @RequestBody RegistrationDto aRegistrationDto ) {
        var registrationDO = mapToDO( aRegistrationDto );
        authenticationFacade.registerNewAccount( registrationDO );
        return HttpStatus.ACCEPTED;
    }

    @PostMapping(
            path = AUTH_CONTROLLER_LOGIN_MAPPING_VALUE, consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity< AuthenticatedUserDto > login( @RequestBody LoginDto aLoginDto ) {
        var loginDO = LoginMapper.mapToDomain( aLoginDto );
        var authenticatedUser = authenticationFacade.authenticateAccount( loginDO );
        return new ResponseEntity<>( authenticatedUser, HttpStatus.ACCEPTED );
    }

    @PostMapping(path = AUTH_CONTROLLER_ACCOUNT_CONFIRMATION_MAPPING_VALUE)
    HttpStatus confirmRegistration( @RequestParam String aToken ) {
        authenticationFacade.confirmRegistration( aToken );
        return HttpStatus.ACCEPTED;
    }
}
