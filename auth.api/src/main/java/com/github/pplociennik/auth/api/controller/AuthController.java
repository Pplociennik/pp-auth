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

import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.common.dto.auth.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.github.pplociennik.auth.api.mapping.ApiMappingsConstants.AUTH_CONTROLLER_FULL_REGISTRATION_MAPPING_VALUE;
import static com.github.pplociennik.auth.api.mapping.ApiMappingsConstants.AUTH_CONTROLLER_MAPPING_VALUE;
import static com.github.pplociennik.auth.business.authentication.domain.map.RegistrationMapper.mapToDO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * A controller class for Auth endpoints sharing.
 *
 * @author Created by: Pplociennik at 26.10.2021 18:11
 */
@RestController( value = AUTH_CONTROLLER_MAPPING_VALUE )
class AuthController {

    private AuthenticationFacade authenticationFacade;

    @Autowired
    AuthController( final AuthenticationFacade aAuthenticationFacade ) {
        authenticationFacade = aAuthenticationFacade;
    }

    @PostMapping( path = AUTH_CONTROLLER_FULL_REGISTRATION_MAPPING_VALUE, consumes = APPLICATION_JSON_VALUE )
    void registerNewUserAccount( @RequestBody RegistrationDto aRegistrationDto ) {
        var registrationDO = mapToDO( aRegistrationDto );
        authenticationFacade.registerNewAccount( registrationDO );

    }
}
