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

package com.github.pplociennik.auth.common.lang;

import com.github.pplociennik.util.lang.TranslationKey;

/**
 * An enum holding keys for identifying exceptions' translation messages.
 *
 * @author Created by: Pplociennik at 22.12.2021 20:07
 */
public enum AuthResExcMsgTranslationKey implements TranslationKey {

    /**
     * Authentication process: Username does not match pattern.
     */
    AUTHENTICATION_USERNAME_NOT_MATCHING_PATTERN,

    /**
     * Authentication process: Password does not match pattern.
     */
    AUTHENTICATION_PASSWORD_NOT_MATCHING_PATTERN,

    /**
     * Authentication process: Email does not match pattern.
     */
    AUTHENTICATION_EMAIL_NOT_MATCHING_PATTERN,

    /**
     * Authentication process: No registration data.
     */
    AUTHENTICATION_NO_REGISTRATION_DATA,

    /**
     * Authentication process: Passwords from input are not equal.
     */
    AUTHENTICATION_PASSWORDS_NOT_EQUAL,

    /**
     * Authentication process: Username already in use.
     */
    AUTHENTICATION_USERNAME_ALREADY_IN_USE,

    /**
     * Authentication process: Email already ein use.
     */
    AUTHENTICATION_EMAIL_ALREADY_IN_USE,

    /**
     * Generating confirmation link: User with such an email does not exist in the database.
     */
    AUTHENTICATION_USER_DOES_NOT_EXIST,

    /**
     * Authentication process: Username not found in database.
     */
    AUTHENTICATION_USERNAME_NOT_FOUND,

    /**
     * Authentication process: Incorrect password.
     */
    AUTHENTICATION_INCORRECT_PASSWORD,

    /**
     * Authentication process: Account not confirmed!
     */
    AUTHENTICATION_ACCOUNT_NOT_CONFIRMED,

    /**
     * Authentication process: Account has been blocked. Please, contact the administrators.
     */
    AUTHENTICATION_ACCOUNT_LOCKED,

    /**
     * Account confirmation: Account does not exist!
     */
    ACCOUNT_CONFIRMATION_USER_NOT_EXISTS,

    /**
     * Account confirmation: Token expired!
     */
    ACCOUNT_CONFIRMATION_TOKEN_EXPIRED,

    /**
     * Account confirmation: Token is not active!
     */
    ACCOUNT_CONFIRMATION_TOKEN_NOT_ACTIVE,

    /**
     * Account confirmation: Token not found!
     */
    ACCOUNT_CONFIRMATION_TOKEN_NOT_FOUND,

    /**
     * Reading properties: Process failed.
     */
    READING_PROPERTIES_FAILED,

    /**
     * All: No data has been provided.
     */
    NO_DATA_PROVIDED,

    /**
     * Unexpected exception being thrown.
     */
    UNEXPECTED_EXCEPTION;

    private static final String EXCEPTIONS_TRANSLATIONS_BASENAME_PROPERTY = "lang/AuthResExcMsg";

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getTranslationsSourcePropertyName() {
        return EXCEPTIONS_TRANSLATIONS_BASENAME_PROPERTY;
    }
}
