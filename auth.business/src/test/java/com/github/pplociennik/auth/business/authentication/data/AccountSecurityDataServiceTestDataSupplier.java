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

package com.github.pplociennik.auth.business.authentication.data;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountSecurityCoreDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static java.lang.Boolean.TRUE;

/**
 * A test data supplier for AccountSecurityDataServiceTest.
 *
 * @author Created by: Pplociennik at 22.04.2022 16:14
 */
public class AccountSecurityDataServiceTestDataSupplier {

    public static UserDetails prepareTestUser_1() {

        var accountNonExpired = TRUE;
        var accountNonLocked = TRUE;
        var accountEnabled = TRUE;
        var credentialsNonExpired = TRUE;
        var password = "test_password_1";
        var username = "TestUsername_1";

        List authorities = prepareTestAuthorities();

        var account = new AccountSecurityCoreDO( username, password, accountEnabled, accountNonExpired,
                                                 credentialsNonExpired, accountNonLocked, authorities );

        return account;

    }

    public static UserDetails prepareTestUser_2() {

        var accountNonExpired = TRUE;
        var accountNonLocked = TRUE;
        var accountEnabled = TRUE;
        var credentialsNonExpired = TRUE;
        var password = "test_password_2";
        var username = "TestUsername_2";

        List authorities = prepareTestAuthorities();

        var account = new AccountSecurityCoreDO( username, password, accountEnabled, accountNonExpired,
                                                 credentialsNonExpired, accountNonLocked, authorities );

        return account;

    }

    public static UserDetails prepareTestUser_3() {

        var accountNonExpired = TRUE;
        var accountNonLocked = TRUE;
        var accountEnabled = TRUE;
        var credentialsNonExpired = TRUE;
        var password = "test_password_3";
        var username = "TestUsername_3";

        List authorities = prepareTestAuthorities();

        var account = new AccountSecurityCoreDO( username, password, accountEnabled, accountNonExpired,
                                                 credentialsNonExpired, accountNonLocked, authorities );

        return account;

    }

    public static Account prepareTestAccount_1() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "example_email_#1" );
        account.setId( 1L );
        account.setPassword( "test_password_1" );
        account.setUsername( "TestUsername_1" );

        account.setAuthorities( prepareTestAccountAuthorities( account ) );

        return account;
    }

    public static Account prepareTestAccount_2() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "example_email_#2" );
        account.setId( 1L );
        account.setPassword( "test_password_2" );
        account.setUsername( "TestUsername_2" );

        account.setAuthorities( prepareTestAccountAuthorities( account ) );

        return account;
    }

    public static Account prepareTestAccount_3() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "example_email_#3" );
        account.setId( 1L );
        account.setPassword( "test_password_3" );
        account.setUsername( "TestUsername_3" );

        account.setAuthorities( prepareTestAccountAuthorities( account ) );

        return account;
    }


    private static List< AuthorityDetails > prepareTestAuthorities() {
        var authority = new AuthorityDetails( AUTH_USER_ROLE.getName() );
        return List.of( authority );
    }

    private static Set< Authority > prepareTestAccountAuthorities( Account aOwner ) {
        var authority = Authority.builder().authoritiesOwner( aOwner ).name( AUTH_USER_ROLE.getName() ).build();

        return Set.of( authority );
    }
}
