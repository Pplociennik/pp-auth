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

package com.github.pplociennik.auth.db.repository.authentication;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A DAO object for Account data transfer.
 *
 * @author Created by: Pplociennik at 17.09.2021 19:48
 */
@Repository
public interface AccountDao extends JpaRepository< Account, Long >, CrudRepository< Account, Long > {

    /**
     * Returns an {@link Account} linked to the given email address.
     *
     * @param aEmailAddress
     *         an email address
     * @return an {@link Account} object
     */
    Optional< Account > getAccountByEmailAddress( String aEmailAddress );

    /**
     * Returns an {@link Account} linked to the given unique identifier.
     *
     * @param aUniqueIdentifier
     *         an unique identifier
     * @return an {@link Account} object
     */
    Optional< Account > getAccountByUniqueObjectIdentifier( @NonNull String aUniqueIdentifier );

    /**
     * Returns an {@link Account} linked to the given username.
     *
     * @param aUsername
     *         a Username.
     * @return an {@link Account} object.
     */
    Optional< Account > findAccountByUsername( String aUsername );

    /**
     * Returns a {@link Account} linked to the specified email address;
     *
     * @param aEmailAddress
     *         an email address.
     * @return an {@link Account} object.
     */
    Optional< Account > findAccountByEmailAddress( @NonNull String aEmailAddress );

    /**
     * Returns a {@link Account} with the specified identifier.
     *
     * @param aId
     *         an id.
     * @return an {@link Account} object.
     */
    Optional< Account > findAccountById( @NonNull long aId );

    /**
     * Checks if there is already a username in the database with the specified username.
     *
     * @param aUsername
     *         a username
     * @return true if the username is already present in the database.
     */
    boolean existsAccountByUsername( String aUsername );

    /**
     * Checks if there is already an email in the database with the specified email.
     *
     * @param aEmail
     *         an email address
     * @return true if there is already an email in the database with the specified email.
     */
    boolean existsAccountByEmailAddress( String aEmail );

}
