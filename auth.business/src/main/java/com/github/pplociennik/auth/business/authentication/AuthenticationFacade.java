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

import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * A facade sharing functionalities for authentication process.
 *
 * @author Created by: Pplociennik at 26.10.2021 19:18
 */
@Service
public class AuthenticationFacade {

    private AuthService authService;
    private AuthenticationValidator validationService;

    @Autowired
    public AuthenticationFacade( @NonNull AuthService aAuthService, @NonNull AuthenticationValidator aValidationService ) {
        authService = aAuthService;
        validationService = aValidationService;
    }

    public void registerNewAccount( @NonNull RegistrationDO aRegistrationDO ) {
        validationService.validateRegistration( aRegistrationDO );
        authService.registerNewAccount( aRegistrationDO );
    }
}
