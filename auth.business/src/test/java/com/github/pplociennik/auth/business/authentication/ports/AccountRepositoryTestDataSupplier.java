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

package com.github.pplociennik.auth.business.authentication.ports;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;

import java.util.Set;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;

/**
 * A test data supplier for {@link AccountRepositoryTest}.
 *
 * @author Created by: Pplociennik at 12.04.2022 22:44
 */
class AccountRepositoryTestDataSupplier {

    static Account prepareSimpleAccountDataOne() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "example_email_#1" );
        account.setId( 1L );
        account.setPassword( "example_pass_1" );
        account.setUsername( "example_username_1" );
        account.setAuthorities( getDummyAuthorities( account ) );

        return account;
    }

    private static Set< Authority > getDummyAuthorities( Account aAccount ) {
        var authority = Authority.builder()
                .name( AUTH_USER_ROLE.getName() )
                .authoritiesOwner( aAccount )
                .build();

        return Set.of( authority );
    }

    static Account prepareSimpleAccountDataTwo() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "example_email_#2" );
        account.setId( 2L );
        account.setPassword( "example_pass_2" );
        account.setUsername( "example_username_2" );
        account.setAuthorities( getDummyAuthorities( account ) );

        return account;
    }
}
