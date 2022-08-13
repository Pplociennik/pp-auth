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

package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.authentication.ports.outside.AccountSecurityDataService;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToSecurityCoreDO;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.AUTHENTICATION_USERNAME_NOT_FOUND;
import static com.github.pplociennik.util.utility.CustomObjects.arrayOf;
import static com.github.pplociennik.util.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * In memory implementation of {@link AccountSecurityDataService} for unit tests.
 *
 * @author Created by: Pplociennik at 23.04.2022 13:26
 */
public class InMemoryAccountSecurityDataService implements AccountSecurityDataService {

    private AccountDao accountRepository;

    public InMemoryAccountSecurityDataService( @NonNull AccountDao aAccountRepository ) {
        accountRepository = aAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );

        var account = accountRepository.findAccountByUsername( aUsername );
        var toReturn = account.orElseThrow(
                () -> new UsernameNotFoundException( getLocalizedMessage( AUTHENTICATION_USERNAME_NOT_FOUND, arrayOf( aUsername ) ) ) );

        return mapToSecurityCoreDO( toReturn );
    }
}
