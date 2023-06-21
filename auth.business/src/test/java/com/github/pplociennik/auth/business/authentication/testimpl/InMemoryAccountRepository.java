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
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;

/**
 * In memory implementation of {@link AccountRepository} for unit tests.
 *
 * @author Created by: Pplociennik at 12.04.2022 22:27
 */
public class InMemoryAccountRepository implements AccountRepository {

    private static final Pattern EMAIL_PATTERN = compile( "[a-zA-Z0-9!#$%&'*+-\\/=?^_`{|}~]+@[a-z0-9-]{2,}\\.[a-z]{2,}",
            Pattern.CASE_INSENSITIVE );

    private static final Set< String > BASE_USER_AUTHORITIES = Set.of( AUTH_USER_ROLE.getName() );

    private List< AccountDO > database;
    private boolean existsAccountByUsername;
    private boolean existsAccountByEmail;

    public InMemoryAccountRepository( List< AccountDO > aDatabase ) {
        database = aDatabase;
    }

    public InMemoryAccountRepository() {
        database = new LinkedList<>();
    }

    public void setDatabase( List< AccountDO > aDatabase ) {
        database = aDatabase;
    }

    public void setExistsAccountByUsername( boolean aExistsAccountByUsername ) {
        existsAccountByUsername = aExistsAccountByUsername;
    }

    public void setExistsAccountByEmail( boolean aExistsAccountByEmail ) {
        existsAccountByEmail = aExistsAccountByEmail;
    }

    @Override
    public AccountDO persist( @NonNull AccountDO aAccount ) {
        requireNonNull( aAccount );
        database.add( aAccount );
        return aAccount;
    }

    @Override
    public AccountDO persistWithBaseUserAuthorities( AccountDO aAccount ) {
        requireNonNull( aAccount );
        createBaseTestAuthoritiesForAccount( aAccount );
        database.add( aAccount );
        return aAccount;
    }

    @Override
    public AccountDO update( AccountDO aAccount ) {
        return aAccount;
    }

    @Override
    public AccountDO findAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return database
                .stream()
                .filter( acc -> acc
                        .getUsername()
                        .equals( aUsername ) )
                .findAny()
                .orElse( null );

    }

    @Override
    public AccountDO findAccountByEmailAddress( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        return database
                .stream()
                .filter( account -> account
                        .getEmailAddress()
                        .equals( aEmail ) )
                .findAny()
                .orElse( null );
    }

    @Override
    public AccountDO findAccountByUsernameOrEmail( @NonNull String aUsernameOrEmail ) {
        requireNonNull( aUsernameOrEmail );
        return database
                .stream()
                .filter( account -> matchesUsernameOrEmail( account, aUsernameOrEmail ) )
                .findAny()
                .orElse( null );
    }

    @Override
    public boolean existsAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return existsAccountByUsername;
    }

    @Override
    public boolean existsAccountByEmailAddress( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        return existsAccountByEmail;
    }

    @Override
    public AccountDO enableAccount( @NonNull AccountDO aAccountToBeConfirmed ) {
        requireNonNull( aAccountToBeConfirmed );
        return null;
        // TODO: add tests and fix this!!!
    }

    private void createBaseTestAuthoritiesForAccount( AccountDO aAccount ) {
        var authorities = BASE_USER_AUTHORITIES
                .stream()
                .map( authorityName -> {
                    var authority = new AuthorityDO();
                    authority.setAuthorityName( authorityName );
                    authority.setOwner( aAccount );

                    return authority;
                } )
                .collect( Collectors.toSet() );
        aAccount.setAuthorities( authorities );
    }

    private boolean matchesUsernameOrEmail( AccountDO aAccount, String aUsernameOrEmail ) {
        var toBeCompared = checkIfMatches( aUsernameOrEmail, EMAIL_PATTERN )
                ? aAccount.getEmailAddress()
                : aAccount.getUsername();

        return toBeCompared.equals( aUsernameOrEmail );
    }

    private boolean checkIfMatches( String aText, Pattern aPattern ) {
        var matcher = aPattern.matcher( aText );
        return matcher.matches();
    }
}
