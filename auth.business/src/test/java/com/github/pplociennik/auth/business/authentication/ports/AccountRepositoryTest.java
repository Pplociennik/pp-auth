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

import com.github.pplociennik.auth.business.authentication.testimpl.InMemoryAccountRepository;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.pplociennik.auth.business.authentication.ports.AccountRepositoryTestDataSupplier.prepareSimpleAccountDataOne;
import static com.github.pplociennik.auth.business.authentication.ports.AccountRepositoryTestDataSupplier.prepareSimpleAccountDataTwo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link AccountRepository}.
 *
 * @author Created by: Pplociennik at 12.04.2022 22:07
 */
class AccountRepositoryTest {

    private final AccountRepository accountRepository;
    private Account TEST_ACCOUNT_1;
    private Account TEST_ACCOUNT_2;

    AccountRepositoryTest() {
        accountRepository = new InMemoryAccountRepository();
    }

    @BeforeEach
    void setUp() {
        TEST_ACCOUNT_1 = prepareSimpleAccountDataOne();
        TEST_ACCOUNT_2 = prepareSimpleAccountDataTwo();
        accountRepository.save( TEST_ACCOUNT_1 );
    }

    @Test
    void shouldReturnTrue_whenThereExistsAccountWithUsername() {

        var username = TEST_ACCOUNT_1.getUsername();
        boolean result = accountRepository.existsAccountByUsername( username );

        assertThat( result ).isTrue();
    }

    @Test
    void shouldReturnTrue_whenThereExistsAccountWithEmailAddress() {

        var emailAddress = TEST_ACCOUNT_1.getEmailAddress();
        boolean result = accountRepository.existsAccountByEmailAddress( emailAddress );

        assertThat( result ).isTrue();
    }

    @Test
    void shouldReturnNonNullAccount_whenThereExistsAccountWithUsername() {

        var username = TEST_ACCOUNT_1.getUsername();
        var account = accountRepository.findAccountByUsername( username );

        assertThat( account ).isNotNull();
        assertThat( account ).isEqualTo( TEST_ACCOUNT_1 );
    }

    @Test
    void shouldSaveNewAccountToDatabase() {
        accountRepository.save( TEST_ACCOUNT_2 );
    }

}