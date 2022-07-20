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

import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;

import java.util.List;
import java.util.Set;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;

/**
 * A data supplier for AccountMapperTest.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:21
 */
public class AccountMapperTestDataSupplier {

    private static final String TEST_USERNAME = "TestUsername";
    private static final String TEST_EMAIL = "testEmail@gmail.com";
    private static final String TEST_PASSWORD = "TestPassword1!";
    private static final String TEST_HASHED_PASSWORD = "VeryLongTestHashedPasswordForConversion";

    public static Account prepareSimpleAccountData() {

        var account = new Account();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( TEST_EMAIL );
        account.setId( 1L );
        account.setPassword( TEST_HASHED_PASSWORD );
        account.setUsername( TEST_USERNAME );

        account.setAuthorities( prepareTestAccountAuthorities( account ) );

        return account;
    }

    public static RegistrationDO prepareSimpleRegistrationDO() {

        return new RegistrationDO( TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, TEST_PASSWORD );
    }

    private static List< AuthorityDetails > prepareTestAuthorities() {
        var authority = new AuthorityDetails( AUTH_USER_ROLE.getName() );
        return List.of( authority );
    }

    private static Set< Authority > prepareTestAccountAuthorities( Account aOwner ) {
        var authority = Authority.builder()
                .authoritiesOwner( aOwner )
                .name( AUTH_USER_ROLE.getName() )
                .build();

        return Set.of( authority );
    }
}
