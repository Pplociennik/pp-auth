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

package com.github.pplociennik.auth.api.exc;

import com.github.pplociennik.auth.api.shared.ResponseEntityExceptionParams;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import com.github.pplociennik.util.exc.ValidationException;
import com.github.pplociennik.util.utility.LanguageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.UNEXPECTED_EXCEPTION;
import static com.github.pplociennik.util.utility.CustomCollectors.toSingleton;
import static com.github.pplociennik.util.utility.CustomObjects.arrayOf;

/**
 * An exception handler for controllers.
 *
 * @author Created by: Pplociennik at 30.01.2022 09:56
 */
@ControllerAdvice
class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * Handles the {@link ValidationException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( ValidationException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleValidationException( ValidationException aException ) {

        var suppressedException = getSuppressedException( aException, ValidationException.class );
        var message = suppressedException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.BAD_REQUEST );
    }

    /**
     * Handles the {@link AccountConfirmationException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( AccountConfirmationException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleAccountConfirmationException( AccountConfirmationException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.BAD_REQUEST );
    }

    /**
     * Handles the unexpected exception.
     *
     * @param aException
     *         a caught exception
     * @return a response with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler( Exception.class )
    ResponseEntity< ResponseEntityExceptionParams > handleUnexpectedException( Exception aException ) {
        var message = LanguageUtil.getLocalizedMessage( UNEXPECTED_EXCEPTION, arrayOf( aException.getMessage() ) );

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private Throwable getSuppressedException( Exception aException, Class aClass ) {
        var allSuppressed = aException.getSuppressed();
        return Arrays.stream( allSuppressed )
                .filter( aClass::isInstance )
                .collect( toSingleton() );
    }
}
