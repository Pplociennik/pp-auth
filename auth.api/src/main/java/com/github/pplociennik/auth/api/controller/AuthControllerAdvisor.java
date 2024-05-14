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

package com.github.pplociennik.auth.api.controller;

import com.github.pplociennik.auth.api.shared.ResponseEntityExceptionParams;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import com.github.pplociennik.commons.exc.ValidationException;
import com.github.pplociennik.commons.utility.LanguageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.UNEXPECTED_EXCEPTION;
import static com.github.pplociennik.commons.utility.CustomCollectors.toSingleton;
import static com.github.pplociennik.commons.utility.CustomObjects.arrayOf;

/**
 * An exception handler for controllers.
 *
 * @author Created by: Pplociennik at 30.01.2022 09:56
 */
@ControllerAdvice( assignableTypes = { AuthController.class } )
class AuthControllerAdvisor extends ResponseEntityExceptionHandler {

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
    ResponseEntity< ResponseEntityExceptionParams > handleAccountConfirmationException(
            AccountConfirmationException aException ) {

        var message = aException.getMessage();

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
    @ExceptionHandler( BadCredentialsException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleBadCredentialsException(
            BadCredentialsException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.UNAUTHORIZED );
    }

    /**
     * Handles the {@link CredentialsExpiredException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( CredentialsExpiredException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleCredentialsExpiredException(
            CredentialsExpiredException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.UNAUTHORIZED );
    }

    /**
     * Handles the {@link AccountExpiredException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( AccountExpiredException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleAccountExpiredException(
            AccountExpiredException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.UNAUTHORIZED );
    }

    /**
     * Handles the {@link DisabledException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( DisabledException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleDisabledException(
            DisabledException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.UNAUTHORIZED );
    }

    /**
     * Handles the {@link LockedException}.
     *
     * @param aException
     *         a caught exception
     * @return a response with BAD_REQUEST status
     */
    @ExceptionHandler( LockedException.class )
    ResponseEntity< ResponseEntityExceptionParams > handleLockedException(
            LockedException aException ) {

        var message = aException.getMessage();

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.UNAUTHORIZED );
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
        var message = LanguageUtil.getLocalizedMessage( UNEXPECTED_EXCEPTION );

        var params = new ResponseEntityExceptionParams();
        params.setTimestamp( LocalDateTime.now() );
        params.setMessage( message );

        aException.printStackTrace();

        return new ResponseEntity<>( params, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private Throwable getSuppressedException( Exception aException, Class aClass ) {
        var allSuppressed = aException.getSuppressed();
        return Arrays
                .stream( allSuppressed )
                .filter( aClass::isInstance )
                .collect( toSingleton() );
    }
}
