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

package com.github.pplociennik.auth.business.authentication.infrastructure.output;

import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import com.github.pplociennik.auth.db.repository.authentication.AuthorityDao;
import com.github.pplociennik.commons.utility.CustomCollectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToDomain;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToEntity;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.business.shared.system.ObjectsSpecifierDefinition.accountTypeSpecifier;
import static com.github.pplociennik.auth.business.shared.system.ObjectsSpecifierDefinition.authorityTypeSpecifier;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.ACCOUNT_CONFIRMATION_USER_NOT_EXISTS;
import static com.github.pplociennik.commons.utility.CustomCollectors.toSingleton;
import static com.github.pplociennik.commons.utility.identifier.UniqueIdentifierGenerator.generateIdentifier;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;

/**
 * {@inheritDoc}
 *
 * @author Created by: Pplociennik at 28.11.2021 15:30
 */
class AccountRepositoryImpl implements AccountRepository {

    private static final Pattern EMAIL_PATTERN = compile( "[a-zA-Z0-9!#$%&'*+-\\/=?^_`{|}~]+@[a-z0-9-]{2,}\\.[a-z]{2,}",
                                                          Pattern.CASE_INSENSITIVE );

    private static final Set< String > BASE_USER_AUTHORITIES = Set.of( AUTH_USER_ROLE.getName() );

    private final AccountDao accountDao;
    private final AuthorityDao authorityDao;

    @Autowired
    public AccountRepositoryImpl( @NonNull AccountDao aAccountDao, AuthorityDao aAuthorityDao ) {
        accountDao = aAccountDao;
        authorityDao = aAuthorityDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDO persist( @NonNull AccountDO aAccount ) {
        requireNonNull( aAccount );

        var account = mapToEntity( aAccount );
        var identifier = generateIdentifier( account, accountTypeSpecifier() );
        account.setUniqueObjectIdentifier( identifier );
        return mapToDomain( accountDao.saveAndFlush( account ) );
    }

    @Override
    public AccountDO persistWithBaseUserAuthorities( AccountDO aAccount ) {
        requireNonNull( aAccount );

        var account = mapToEntity( aAccount );
        var identifier = generateIdentifier( account, accountTypeSpecifier() );
        account.setUniqueObjectIdentifier( identifier );
        var authorities = createBaseAuthoritiesForAccount( account );
        account.setAuthorities( authorities );
        return mapToDomain( accountDao.saveAndFlush( account ) );
    }

    @Override
    public AccountDO update( @NonNull AccountDO aAccount ) {
        requireNonNull( aAccount );

        var toBeUpdated = accountDao.getAccountByUniqueObjectIdentifier( aAccount.getUniqueObjectIdentifier() );
        return toBeUpdated
                .map( forUpdate -> updateAccount( forUpdate, aAccount ) )
                .stream()
                .collect( toSingleton() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDO findAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        var account = accountDao.findAccountByUsername( aUsername );
        return account
                .map( AccountMapper::mapToDomain )
                .orElse( null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDO findAccountByEmailAddress( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        var account = accountDao.findAccountByEmailAddress( aEmail );
        return account
                .map( AccountMapper::mapToDomain )
                .orElse( null );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDO findAccountByUsernameOrEmail( @NonNull String aUsernameOrEmail ) {
        requireNonNull( aUsernameOrEmail );
        return checkIfMatches( aUsernameOrEmail, EMAIL_PATTERN )
               ? findAccountByEmailAddress( aUsernameOrEmail )
               : findAccountByUsername( aUsernameOrEmail );
    }

    @Override
    public AccountDO findAccountByUniqueIdentifier( @NonNull String aUniqueIdentifier ) {
        requireNonNull( aUniqueIdentifier );
        var account = accountDao.findAccountByUniqueObjectIdentifier( aUniqueIdentifier );
        return account
                .map( AccountMapper::mapToDomain )
                .stream()
                .collect( toSingleton() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsAccountByUsername( @NonNull String aUsername ) {
        requireNonNull( aUsername );
        return accountDao.existsAccountByUsername( aUsername );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsAccountByEmailAddress( @NonNull String aEmail ) {
        requireNonNull( aEmail );
        return accountDao.existsAccountByEmailAddress( aEmail );
    }

    /**
     * Updates the specified account.
     *
     * @param aAccount
     *         an account to be updated.
     */
    @Override
    public AccountDO enableAccount( @NonNull AccountDO aAccount ) {
        requireNonNull( aAccount );

        var account = accountDao.findAccountByEmailAddress( aAccount.getEmailAddress() );
        var toUpdate = account.orElseThrow(
                () -> new AccountConfirmationException( ACCOUNT_CONFIRMATION_USER_NOT_EXISTS ) );
        toUpdate.setEnabled( true );

        var enabledAccount = accountDao.save( toUpdate );
        return mapToDomain( enabledAccount );
    }

    private Set<Authority> createBaseAuthoritiesForAccount(Account aAccount ) {
        return BASE_USER_AUTHORITIES
                .stream()
                .map( authorityName -> {
                    var authority = new Authority();
                    authority.setName( authorityName );
                    authority.setAuthoritiesOwner( aAccount );

                    var identifier = generateIdentifier( authority, authorityTypeSpecifier() );
                    authority.setUniqueObjectIdentifier( identifier );
                    return authority;
                } )
                .collect( Collectors.toSet() );
    }

    private AccountDO updateAccount( Account aToBeUpdated, AccountDO aAccount ) {

        requireNonNull( aToBeUpdated );

        aToBeUpdated.setLastLoginDate( aAccount.getLastLoginDate() );
        accountDao.saveAndFlush( aToBeUpdated );

        return mapToDomain( aToBeUpdated );
    }

    private boolean checkIfMatches( String aText, Pattern aPattern ) {
        var matcher = aPattern.matcher( aText );
        return matcher.matches();
    }
}
