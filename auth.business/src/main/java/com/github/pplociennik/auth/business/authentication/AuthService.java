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

package com.github.pplociennik.auth.business.authentication;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.VerificationTokenRepository;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.function.Predicate;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToEntity;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.ACCOUNT_CONFIRMATION_TOKEN_EXPIRED;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.ACCOUNT_CONFIRMATION_USER_NOT_EXISTS;
import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static java.time.Instant.now;
import static java.util.Objects.requireNonNull;

/**
 * A service sharing methods for basic authentication process.
 *
 * @author Created by: Pplociennik at 26.10.2021 20:18
 */
class AuthService {

    private final PasswordEncoder encoder;
    private final AccountRepository accountRepository;
    private final VerificationUrlResolver tokenResolver;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public AuthService( @NonNull PasswordEncoder aEncoder, @NonNull AccountRepository aAccountRepository, @NonNull VerificationUrlResolver aUrlResolver, @NonNull VerificationTokenRepository aTokenRepository ) {
        encoder = aEncoder;
        accountRepository = aAccountRepository;
        tokenResolver = aUrlResolver;
        tokenRepository = aTokenRepository;
    }

    /**
     * Creates a new {@link Account} entry in the database containing a hashed password.
     *
     * @param aRegistrationDO
     *         a registration domain object.
     */
    AccountDO registerNewAccount( @NonNull RegistrationDO aRegistrationDO ) {

        requireNonNull( aRegistrationDO );

        String hashedPassword = encoder.encode( aRegistrationDO.getPassword() );
        var newAccount = mapToEntity( aRegistrationDO, hashedPassword );
        addNewAccountBasePrivilidges( newAccount );

        return accountRepository.save( newAccount );
    }

    /**
     * Returns a link containing token for registered account confirmation.
     *
     * @param aAccount
     *         an account for which the token is being generated.
     * @return a confirmation link needed to be sent via email message.
     */
    String generateConfirmationLink( @NonNull AccountDO aAccount ) {
        requireNonNull( aAccount );
        return tokenResolver.resolveAccountConfirmationLink( aAccount );
    }

    /**
     * Confirms registered account.
     *
     * @param aToken
     *         a token for the account's registration confirmation.
     */
    void confirmRegistration( @NonNull String aToken ) {
        requireNonEmpty( aToken );

        var verificationToken = tokenRepository.findByToken( aToken );
        var tokenOwner = verificationToken.getOwner();

        var accountByUsername = accountRepository.findAccountByUsername( tokenOwner.getUsername() );
        var accountToBeConfirmed = Optional.of( accountByUsername )
                .orElseThrow( () -> new AccountConfirmationException( ACCOUNT_CONFIRMATION_USER_NOT_EXISTS ) );

        throwIf( verificationToken, this::isTokenExpired );
        accountRepository.enableAccount( accountToBeConfirmed );
    }

    private boolean throwIf( @NonNull VerificationTokenDO aVerificationToken, @NonNull Predicate< VerificationTokenDO > aPredicate ) {
        requireNonNull( aVerificationToken );
        requireNonNull( aPredicate );

        var result = aPredicate.test( aVerificationToken );
        return Optional.of( result ).orElseThrow( () -> new AccountConfirmationException( ACCOUNT_CONFIRMATION_TOKEN_EXPIRED ) );
    }

    private boolean isTokenExpired( @NonNull VerificationTokenDO aVerificationTokenDO ) {
        requireNonNull( aVerificationTokenDO );
        return aVerificationTokenDO.getExpirationDate().isBefore( now() );
    }

    private Account addNewAccountBasePrivilidges( Account aNewAccount ) {

        aNewAccount.setEnabled( false );
        aNewAccount.setAccountNonLocked( true );
        aNewAccount.setAccountNonExpired( true );
        aNewAccount.setCredentialsNonExpired( true );

        return aNewAccount;
    }
}
