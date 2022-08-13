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

package com.github.pplociennik.auth.business.authentication.testimpl;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.inside.AuthenticationValidationRepository;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * In memory implementation of {@link AuthenticationValidationRepository} for unit tests.
 *
 * @author Created by: Pplociennik at 22.04.2022 17:16
 */
public class InMemoryAuthenticationValidationRepository implements AuthenticationValidationRepository {

    private List< AccountDO > database;
    private boolean usernameExists;
    private boolean emailExists;
    private boolean isTokenActive;
    private boolean tokenExists;

    public InMemoryAuthenticationValidationRepository( List< AccountDO > aDatabase ) {
        database = aDatabase;
    }

    public InMemoryAuthenticationValidationRepository() {
        database = new LinkedList<>();
    }

    public void add( AccountDO aAccountDO ) {
        database.add( aAccountDO );
    }

    public void setDatabase( List< AccountDO > aDatabase ) {
        database = aDatabase;
    }

    public void setUsernameExists( boolean aUsernameExists ) {
        usernameExists = aUsernameExists;
    }

    public void setEmailExists( boolean aEmailExists ) {
        emailExists = aEmailExists;
    }

    public void setTokenActive( boolean aTokenActive ) {
        isTokenActive = aTokenActive;
    }

    public void setTokenExists( boolean aTokenExists ) {
        tokenExists = aTokenExists;
    }

    @Override
    public boolean checkIfUsernameExists( @NonNull String aUsername ) {
        requireNonNull( aUsername );

        return usernameExists;
    }

    @Override
    public boolean checkIfEmailExists( @NonNull String aEmail ) {
        requireNonNull( aEmail );

        return emailExists;
    }

    @Override
    public boolean checkIfTokenActive( String aToken ) {
        return isTokenActive;
    }

    @Override
    public boolean checkIfTokenExists( String aToken ) {
        return tokenExists;
    }
}
