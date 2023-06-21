package com.github.pplociennik.auth.business.authentication.data;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;

/**
 * A data supplier for AuthenticationValidationRepositoryTest.
 *
 * @author Created by: Pplociennik at 20.07.2022 22:47
 */
public class AuthenticationValidationRepositoryTestDataSupplier {

    public static AccountDO prepareAccount_1() {

        var account = new AccountDO();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test1@testMail.com" );
        account.setUsername( "test_username_1" );

        return account;
    }

    public static AccountDO prepareAccount_2() {

        var account = new AccountDO();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test2@testMail.com" );
        account.setUsername( "test_username_2" );

        return account;
    }

    public static AccountDO prepareAccount_3() {

        var account = new AccountDO();
        account.setAccountNonExpired( true );
        account.setAccountNonLocked( true );
        account.setEnabled( true );
        account.setCredentialsNonExpired( true );
        account.setEmailAddress( "test3@testMail.com" );
        account.setUsername( "test_username_3" );

        return account;
    }
}
