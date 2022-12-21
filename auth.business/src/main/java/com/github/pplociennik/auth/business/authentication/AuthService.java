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
import com.github.pplociennik.auth.business.authentication.ports.inside.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.inside.VerificationTokenRepository;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.business.shared.system.EnvironmentPropertiesProvider;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import com.github.pplociennik.auth.common.exc.AccountConfirmationException;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.business.shared.system.SystemProperty.GLOBAL_CLIENT_URL;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.ACCOUNT_CONFIRMATION_TOKEN_EXPIRED;
import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.ACCOUNT_CONFIRMATION_USER_NOT_EXISTS;
import static com.github.pplociennik.commons.utility.CustomObjects.requireNonEmpty;
import static com.github.pplociennik.commons.utility.identifier.UniqueIdentifierGenerator.generateIdentifier;
import static java.time.Instant.now;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * A service sharing methods for basic authentication process.
 *
 * @author Created by: Pplociennik at 26.10.2021 20:18
 */
class AuthService {

    private static final Set< String > BASE_USER_AUTHORITIES = Set.of( AUTH_USER_ROLE.getName() );
    private final PasswordEncoder encoder;
    private final AccountRepository accountRepository;
    private final VerificationTokenResolver tokenResolver;
    private final VerificationTokenRepository tokenRepository;
    private final EnvironmentPropertiesProvider propertiesProvider;
    private final TimeService timeService;

    @Autowired
    public AuthService(
            @NonNull PasswordEncoder aEncoder, @NonNull AccountRepository aAccountRepository,
            @NonNull VerificationTokenResolver aUrlResolver, @NonNull VerificationTokenRepository aTokenRepository,
            @NonNull EnvironmentPropertiesProvider aPropertiesProvider, TimeService aTimeService ) {
        encoder = aEncoder;
        accountRepository = aAccountRepository;
        tokenResolver = aUrlResolver;
        tokenRepository = aTokenRepository;
        propertiesProvider = aPropertiesProvider;
        timeService = aTimeService;
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
        var newAccount = createNewAccount( aRegistrationDO, hashedPassword );
        addNewAccountBasePrivilidges( newAccount );

        return accountRepository.persist( newAccount );
    }

    /**
     * Returns a link containing token for registered account confirmation.
     *
     * @param aAccountDO
     *         an account for which the token is being generated.
     * @return a confirmation link needed to be sent via email message.
     */
    String generateConfirmationLink( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );

        final var parameter = "aToken";

        var verificationToken = tokenResolver.resolveUniqueToken( aAccountDO, EMAIL_CONFIRMATION_TOKEN );
        var url = propertiesProvider.getPropertyValue( GLOBAL_CLIENT_URL );

        return url + "/?" + parameter + "=" + verificationToken.getToken();
    }

    /**
     * Confirms registered account.
     *
     * @param aToken
     *         a token for the account's registration confirmation.
     */
    AccountDO confirmRegistration( @NonNull String aToken ) {
        requireNonEmpty( aToken );

        var verificationToken = tokenRepository.findByToken( aToken );
        var tokenOwner = verificationToken.getOwner();

        var accountByUsername = accountRepository.findAccountByUsername( tokenOwner.getUsername() );
        var accountToBeConfirmed = Optional
                .ofNullable( accountByUsername )
                .orElseThrow( () -> new AccountConfirmationException( ACCOUNT_CONFIRMATION_USER_NOT_EXISTS ) );

        throwIf( verificationToken, this::isTokenExpired );
        var enabledAccount = accountRepository.enableAccount( accountToBeConfirmed );
        deactivateToken( verificationToken );

        return enabledAccount;
    }

    private AccountDO createNewAccount( @NonNull RegistrationDO aRegistrationDO, @NonNull String aHashedPassword ) {
        requireNonNull( aRegistrationDO );
        requireNonNull( aHashedPassword );

        var identifier = generateIdentifier( Account.class, aRegistrationDO.getUsername() );

        return AccountDO
                .builder()
                .uniqueObjectIdentifier( identifier )
                .username( aRegistrationDO.getUsername() )
                .password( aHashedPassword )
                .emailAddress( aRegistrationDO.getEmail() )
                .creationDate( timeService.getCurrentSystemDateTime() )
                .authorities( createBaseUserAuthorities() )
                .build();

    }

    private void deactivateToken( VerificationTokenDO aVerificationToken ) {
        aVerificationToken.setActive( false );
        tokenRepository.update( aVerificationToken );
    }

    private boolean throwIf(
            @NonNull VerificationTokenDO aVerificationToken, @NonNull Predicate< VerificationTokenDO > aPredicate ) {
        requireNonNull( aVerificationToken );
        requireNonNull( aPredicate );

        var result = aPredicate.test( aVerificationToken );
        if ( result ) {
            throw new AccountConfirmationException( ACCOUNT_CONFIRMATION_TOKEN_EXPIRED );
        }

        return result;
    }

    private boolean isTokenExpired( @NonNull VerificationTokenDO aVerificationTokenDO ) {
        requireNonNull( aVerificationTokenDO );
        return aVerificationTokenDO
                .getExpirationDate()
                .isBefore( now() );
    }

    private AccountDO addNewAccountBasePrivilidges( AccountDO aNewAccount ) {

        aNewAccount.setEnabled( false );
        aNewAccount.setAccountNonLocked( true );
        aNewAccount.setAccountNonExpired( true );
        aNewAccount.setCredentialsNonExpired( true );

        return aNewAccount;
    }

    private Set< AuthorityDO > createBaseUserAuthorities() {
        return BASE_USER_AUTHORITIES
                .stream()
                .map( this::createNewOrphanAuthority )
                .collect( toUnmodifiableSet() );
    }

    private AuthorityDO createNewOrphanAuthority( String aAuthorityName ) {
        var identifier = generateIdentifier( Authority.class, aAuthorityName );

        var authorityDO = new AuthorityDO();
        authorityDO.setAuthorityName( aAuthorityName );
        authorityDO.setUniqueObjectIdentifier( identifier );

        return authorityDO;
    }
}
