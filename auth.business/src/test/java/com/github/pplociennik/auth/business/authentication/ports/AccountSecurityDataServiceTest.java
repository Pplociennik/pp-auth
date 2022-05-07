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

import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAccountSecurityDataService;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.github.pplociennik.auth.business.authentication.ports.AccountSecurityDataServiceTestDataSupplier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AccountSecurityDataService}.
 *
 * @author Created by: Pplociennik at 22.04.2022 16:06
 */
class AccountSecurityDataServiceTest {

    private static final String TEST_USERNAME_1 = "TestUsername_1";
    private static final String TEST_USERNAME_2 = "TestUsername_2";
    private static final String TEST_USERNAME_3 = "TestUsername_3";
    public static final String TEST_INCORRECT_USERNAME = "IncorrectUsername";
    private static UserDetails TEST_USER_1;
    private static UserDetails TEST_USER_2;
    private static UserDetails TEST_USER_3;
    private static Account TEST_ACCOUNT_1;
    private static Account TEST_ACCOUNT_2;
    private static Account TEST_ACCOUNT_3;

    private AccountDao repository = mock( AccountDao.class );
    private AccountSecurityDataService underTest = new InMemoryAccountSecurityDataService( repository );

    @BeforeEach
    void prepareMocksAndData() {
        TEST_USER_1 = prepareTestUser_1();
        TEST_USER_2 = prepareTestUser_2();
        TEST_USER_3 = prepareTestUser_3();

        TEST_ACCOUNT_1 = prepareTestAccount_1();
        TEST_ACCOUNT_2 = prepareTestAccount_2();
        TEST_ACCOUNT_3 = prepareTestAccount_3();

        when( repository.findAccountByUsername( TEST_USERNAME_1 ) ).thenReturn( TEST_ACCOUNT_1 );
        when( repository.findAccountByUsername( TEST_USERNAME_2 ) ).thenReturn( TEST_ACCOUNT_2 );
        when( repository.findAccountByUsername( TEST_USERNAME_3 ) ).thenReturn( TEST_ACCOUNT_3 );

        when( repository.findAccountByUsername( TEST_INCORRECT_USERNAME ) ).thenThrow( UsernameNotFoundException.class );
        when( repository.findAccountByUsername( null ) ).thenThrow( NullPointerException.class );
    }

    @Test
    void shouldReturnAppropriateUser_whenUsernameCorrect() {
        var user1 = underTest.loadUserByUsername( TEST_USERNAME_1 );
        var user2 = underTest.loadUserByUsername( TEST_USERNAME_2 );
        var user3 = underTest.loadUserByUsername( TEST_USERNAME_3 );

        assertThat( user1 ).usingRecursiveComparison().isEqualTo( TEST_USER_1 );
        assertThat( user2 ).usingRecursiveComparison().isEqualTo( TEST_USER_2 );
        assertThat( user3 ).usingRecursiveComparison().isEqualTo( TEST_USER_3 );
    }

    @Test
    void shouldThrowUsernameNotFoundException_whenThereIsNoSuchAUserInTheDatabase() {
        assertThatThrownBy( () -> underTest.loadUserByUsername( TEST_INCORRECT_USERNAME ) ).isInstanceOf( UsernameNotFoundException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsNull() {
        assertThatThrownBy( () -> underTest.loadUserByUsername( null ) ).isInstanceOf( NullPointerException.class );
    }

}
