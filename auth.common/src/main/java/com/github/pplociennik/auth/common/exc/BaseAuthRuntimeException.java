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

package com.github.pplociennik.auth.common.exc;

import com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey;
import com.github.pplociennik.auth.common.utility.LanguageUtil;

import java.io.Serializable;

/**
 * Base exception.
 *
 * @author Created by: Pplociennik at 22.12.2021 19:06
 */
public class BaseAuthRuntimeException extends RuntimeException {

    //TODO
    public BaseAuthRuntimeException( final String message ) {
        super( message );
    }

    //TODO
    public BaseAuthRuntimeException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    //TODO
    public BaseAuthRuntimeException( final Throwable cause ) {
        super( cause );
    }

    public BaseAuthRuntimeException( final AuthResExcMsgTranslationKey aKey, Serializable... aArgs ) {
        super( LanguageUtil.getLocalizedMessage( aKey, aArgs ) );
    }
}
