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

package com.github.pplociennik.auth.core.configuration;

import static java.lang.String.format;

/**
 * A class holding constants being used in the security.
 *
 * @author Created by: Pplociennik at 16.09.2021 19:24
 */
class AuthSecurityConstants {

    public static final String ROOT_URI = "/";
    public static final String AUTH_ADMIN_URI = ROOT_URI + "admin/**";
    public static final String AUTH_USER_URI = ROOT_URI + "user/**";
    private static final String AUTH_URI_PREFIX = "/auth/";
    public static final String AUTH_LOGIN_URI = AUTH_URI_PREFIX + "login/**";
    public static final String AUTH_REGISTRATION_URI = AUTH_URI_PREFIX + "register/**";
    public static final String AUTH_ACCOUNT_CONFIRMATION_URI = AUTH_URI_PREFIX + "confirmAccount/**";
    public static final String AUTH_LOGOUT_URI = AUTH_URI_PREFIX + "logout/**";

    /**
     * The class cannot be instantiable.
     */
    private AuthSecurityConstants() {
        throw new AssertionError( format( "The class \"%s\" is not instantiable!", this ) );
    }
}
