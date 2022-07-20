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

package com.github.pplociennik.auth.business.authentication.infrastructure;

import com.github.pplociennik.auth.business.authentication.ports.AccountSecurityDataService;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToSecurityCoreDO;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.AUTHENTICATION_USERNAME_NOT_FOUND;
import static com.github.pplociennik.util.utility.CustomObjects.arrayOf;
import static com.github.pplociennik.util.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * A service for {@link Account} data management.
 *
 * @author Created by: Pplociennik at 17.09.2021 21:34
 */
class AccountSecurityDataServiceImpl implements AccountSecurityDataService {

    private final AccountDao AccountDao;

    @Autowired
    public AccountSecurityDataServiceImpl( @NonNull final AccountDao aAccountDao ) {
        AccountDao = aAccountDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername( @NonNull final String aUsername ) {
        requireNonNull( aUsername );

        var account = AccountDao.findAccountByUsername( aUsername );
        var toReturn = account.orElseThrow(
                () -> new UsernameNotFoundException( getLocalizedMessage( AUTHENTICATION_USERNAME_NOT_FOUND, arrayOf( aUsername ) ) ) );

        return mapToSecurityCoreDO( toReturn );
    }
}

