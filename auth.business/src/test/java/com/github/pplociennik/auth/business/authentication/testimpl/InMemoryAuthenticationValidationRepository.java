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

import com.github.pplociennik.auth.business.authentication.ports.AuthenticationValidationRepository;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.springframework.lang.NonNull;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * In memory implementation of {@link AuthenticationValidationRepository} for unit tests.
 *
 * @author Created by: Pplociennik at 22.04.2022 17:16
 */
public class InMemoryAuthenticationValidationRepository implements AuthenticationValidationRepository {

    private List< Account > database;

    public InMemoryAuthenticationValidationRepository() {
        prepareTestDatabase();
    }

    public void prepareTestDatabase() {
        var account_1 = prepareAccount_1();
        var account_2 = prepareAccount_2();
        var account_3 = prepareAccount_3();

        database = List.of( account_1, account_2, account_3 );
    }

    @Override
    public boolean checkIfUsernameExists( @NonNull String aUsername ) {
        requireNonNull( aUsername );

        return database.stream()
                .anyMatch( account -> account.getUsername().equals( aUsername ) );
    }

    @Override
    public boolean checkIfEmailExists( @NonNull String aEmail ) {
        requireNonNull( aEmail );

        return database.stream()
                .anyMatch( account -> account.getEmailAddress().equals( aEmail ) );
    }

    private Account prepareAccount_1() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test1@testMail.com" );
        account.setId( 1L );
        account.setPassword( "test_pass_1" );
        account.setUsername( "test_username_1" );

        return account;
    }

    private Account prepareAccount_2() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test2@testMail.com" );
        account.setId( 1L );
        account.setPassword( "test_pass_1" );
        account.setUsername( "test_username_2" );

        return account;
    }

    private Account prepareAccount_3() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test3@testMail.com" );
        account.setId( 1L );
        account.setPassword( "test_pass_1" );
        account.setUsername( "test_username_3" );

        return account;
    }
}
