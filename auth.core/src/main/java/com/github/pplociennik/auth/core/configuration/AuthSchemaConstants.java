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
 * A class holding constants being names of the schema elements.
 *
 * @author Created by: Pplociennik at 16.09.2021 20:19
 */
class AuthSchemaConstants {

    public static final String AUTH_USER_TABLE_SCHEMA_NAME = "AUTH_ACCOUNTS";
    public static final String AUTH_USER_TABLE_USERNAME_COLUMN_NAME = "USERNAME";
    public static final String AUTH_USER_TABLE_PASSWORD_COLUMN_NAME = "PASSWORD";
    public static final String AUTH_USER_TABLE_ENABLED_COLUMN_NAME = "ENABLED";
    public static final String AUTH_AUTHORITY_TABLE_SCHEMA_NAME = "AUTH_AUTHORITIES";
    public static final String AUTH_AUTHORITY_TABLE_USERNAME_COLUMN_NAME = "USERNAME";
    public static final String AUTH_AUTHORITY_TABLE_AUTHORITY_COLUMN_NAME = "AUTHORITY";

    /**
     * The class cannot be instantiable.
     */
    private AuthSchemaConstants() {
        throw new RuntimeException( format( "The class \"%s\" is not instantiable!", this ) );
    }


}
