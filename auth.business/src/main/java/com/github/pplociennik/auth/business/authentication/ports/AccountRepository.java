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

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.springframework.lang.NonNull;

/**
 * A Data Access Object for authentication data.
 *
 * @author Created by: Pplociennik at 28.11.2021 15:26
 */
public interface AccountRepository {

    /**
     * Persists an account object to the database.
     *
     * @param aAccount
     *         a user account.
     */
    AccountDO save( @NonNull AccountDO aAccount );

    /**
     * Returns an account with the specified username if it exists in the database.
     *
     * @param aUsername
     *         a username.
     * @return a user account.
     */
    AccountDO findAccountByUsername( @NonNull String aUsername );

    /**
     * Returns true if an account with the specified username exists in the database.
     *
     * @param aUsername
     *         a username.
     * @return TRUE is the account exists and FALSE if it does not.
     */
    boolean existsAccountByUsername( @NonNull String aUsername );

    /**
     * Returns true if an account with the specified email address exists in the database.
     *
     * @param aEmail
     *         an email address.
     * @return TRUE if the account exists and FALSE if it does noe.
     */
    boolean existsAccountByEmailAddress( @NonNull String aEmail );

    /**
     * Enables the specified account. Sets 'enabled' flag to true.
     *
     * @param aAccountToBeConfirmed
     *         an account to be updated.
     */
    AccountDO enableAccount( @NonNull AccountDO aAccountToBeConfirmed );
}
