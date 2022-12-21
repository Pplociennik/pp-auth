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

import auth.dto.RegistrationDto;

/**
 * A data supplier for RegistrationMapperTest.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:51
 */
public class RegistrationMapperTestDataSupplier {

    private static final String TEST_USERNAME = "TestUsername";
    private static final String TEST_PASSWORD = "TestPassword1!";
    private static final String TEST_EMAIL = "testEamail@gmail.com";

    public static RegistrationDto prepareSimpleRegistrationDto() {

        return RegistrationDto
                .builder()
                .email( TEST_EMAIL )
                .username( TEST_USERNAME )
                .password( TEST_PASSWORD )
                .repeatedPassword( TEST_PASSWORD )
                .build();
    }
}
