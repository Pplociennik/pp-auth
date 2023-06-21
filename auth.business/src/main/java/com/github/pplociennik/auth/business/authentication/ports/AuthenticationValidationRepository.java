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
import org.springframework.lang.NonNull;

/**
 * A Data Access Object for authentication validation process.
 *
 * @author Created by: Pplociennik at 25.01.2022 23:17
 */
public interface AuthenticationValidationRepository {

    /**
     * Returns true if the specified username is already present in the database.
     *
     * @param aUsername
     *         a username;
     *
     * @return true if the specified username is already present in the database.
     */
    boolean checkIfUsernameExists( @NonNull String aUsername );

    /**
     * Returns true if the specified email address is already present in the database.
     *
     * @param aEmail
     *         an email address
     *
     * @return true if the specified username is already present in the database.
     */
    boolean checkIfEmailExists( @NonNull String aEmail );

    /**
     * Returns true if the specified token is active. False otherwise.
     *
     * @param aToken
     *         a token to be checked
     *
     * @return true if the specified token is active, false otherwise.
     *
     * @throws NullPointerException
     *         if the parameter is null.
     */
    boolean checkIfTokenActive( @NonNull String aToken );

    /**
     * Returns true if the specified token exists in the database. False otherwise.
     *
     * @param aToken
     *         a token to be checked
     *
     * @return true if the specified token exists in the database. False otherwise.
     *
     * @throws NullPointerException
     *         if the parameter is null.
     */
    boolean checkIfTokenExists( @NonNull String aToken );

    /**
     * Returns an account by email address.
     *
     * @param aEmail
     *         an email address.
     *
     * @return an account linked to the specified email address.
     */
    AccountDO findByEmail( @NonNull String aEmail );

    /**
     * Returns an account by username.
     *
     * @param aUsername
     *         a username.
     *
     * @return an account linked to the specified username.
     */
    AccountDO findByUsername( @NonNull String aUsername );
}
