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

package com.github.pplociennik.auth.business.authentication.infrastructure.outside;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.inside.AccountRepository;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handler for successful rest authentication.
 *
 * @author Created by: Pplociennik at 29.01.2022 22:53
 */
class RestAuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

    private final AccountRepository accountRepository;
    private final TimeService timeService;

    @Autowired
    RestAuthenticationSuccessHandlerImpl( AccountRepository aAccountRepository, TimeService aTimeService ) {
        accountRepository = aAccountRepository;
        timeService = aTimeService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication ) {

        var authenticatedUser = getAuthenticatedUser( authentication );
        updateLastLoginDate( authenticatedUser );
        clearAuthenticationAttributes( request );
    }

    private void updateLastLoginDate( AccountDO aAuthenticatedUser ) {
        var currentZonedDateTime = timeService.getCurrentSystemDateTime();
        aAuthenticatedUser.setLastLoginDate( currentZonedDateTime );

        accountRepository.update( aAuthenticatedUser );
    }

    private AccountDO getAuthenticatedUser( Authentication aAuthentication ) {
        var username = aAuthentication.getName();
        return accountRepository.findAccountByUsername( username );
    }
}
