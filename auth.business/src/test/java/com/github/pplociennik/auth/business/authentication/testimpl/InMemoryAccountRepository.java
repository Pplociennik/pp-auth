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

import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;

import static com.github.pplociennik.util.utility.CustomCollectors.toSingleton;
import static java.util.Objects.requireNonNull;

/**
 * In memory implementation of {@link AccountRepository} for unit tests.
 *
 * @author Created by: Pplociennik at 12.04.2022 22:27
 */
public class InMemoryAccountRepository implements AccountRepository {

    private List< Account > database;

    public InMemoryAccountRepository() {
        database = new LinkedList<>();
    }

    @Override
    public AccountDO save( @NonNull Account aAccount ) {
        requireNonNull( aAccount );
        database.add( aAccount );
        return AccountMapper.mapToDomain( aAccount );
    }

    @Override
    public AccountDO findAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        var account = database.stream()
                .filter( acc -> acc.getUsername().equals( aUsername ) )
                .collect( toSingleton() );

        return AccountMapper.mapToDomain( account );
    }

    @Override
    public boolean existsAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return database.stream()
                .anyMatch( account -> account.getUsername().equals( aUsername ) );
    }

    @Override
    public boolean existsAccountByEmailAddress( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        return database.stream()
                .anyMatch( account -> account.getEmailAddress().equals( aEmail ) );
    }

    @Override
    public void enableAccount( AccountDO aAccountToBeConfirmed ) {
        return;
    }
}
