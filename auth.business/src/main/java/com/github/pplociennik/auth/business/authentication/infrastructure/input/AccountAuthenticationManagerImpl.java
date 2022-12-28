package com.github.pplociennik.auth.business.authentication.infrastructure.input;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.regex.Pattern;

import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.*;
import static com.github.pplociennik.commons.utility.LanguageUtil.getLocalizedMessage;
import static java.util.regex.Pattern.compile;

/**
 * Custom authentication provider.
 *
 * @author Created by: Pplociennik at 14.08.2022 22:45
 */
class AccountAuthenticationManagerImpl implements AuthenticationManager {

    private static final Pattern EMAIL_PATTERN = compile( "[a-zA-Z0-9!#$%&'*+-\\/=?^_`{|}~]+@[a-z0-9-]{2,}\\.[a-z]{2,}",
                                                          Pattern.CASE_INSENSITIVE );

    private final AccountRepository accountRepository;
    private final TimeService timeService;

    @Autowired
    AccountAuthenticationManagerImpl(
            AccountRepository aAccountRepository, TimeService aTimeService ) {
        accountRepository = aAccountRepository;
        timeService = aTimeService;
    }

    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {

        if ( authentication == null ) {
            return authentication;
        }

        var name = authentication.getName();
        var password = authentication
                .getCredentials()
                .toString();

        var account = checkIfMatches( name, EMAIL_PATTERN )
                      ? accountRepository.findAccountByEmailAddress( name )
                      : accountRepository.findAccountByUsername( name );
        var isDataValid = executeValidation( account, name, password );

        return isDataValid
               ? authenticateUser( account )
               : null;
    }

    private Authentication authenticateUser( AccountDO aAccountDO ) {
        updateLastLoginDate( aAccountDO );
        var accountAuthorities = AuthorityMapper.mapToAuthorityDetails( aAccountDO.getAuthorities() );
        return new UsernamePasswordAuthenticationToken( aAccountDO.getUsername(), aAccountDO.getPassword(),
                                                        accountAuthorities );
    }

    private void updateLastLoginDate( AccountDO aAccountDO ) {
        var currentDateTime = timeService.getCurrentSystemDateTime();
        aAccountDO.setLastLoginDate( currentDateTime );
        accountRepository.update( aAccountDO );
    }

    private boolean executeValidation( AccountDO aAccount, String aName, String aPassword ) {

        validatePassword( aAccount, aPassword );
        validateIfEnabled( aAccount );
        validateIfNonLocked( aAccount );
        validateIfAccountNonExpired( aAccount );
        validateIfCredentialsNonExpired( aAccount );

        return true;
    }

    private void validateIfNonLocked( AccountDO aAccount ) {
        if ( ! aAccount.isAccountNonLocked() ) {
            throw new LockedException( getLocalizedMessage( AUTHENTICATION_ACCOUNT_LOCKED ) );
        }
    }

    private void validateIfEnabled( AccountDO aAccount ) {
        if ( ! aAccount.isEnabled() ) {
            throw new DisabledException( getLocalizedMessage( AUTHENTICATION_ACCOUNT_NOT_CONFIRMED ) );
        }
    }

    private void validateIfAccountNonExpired( AccountDO aAccount ) {
        if ( ! aAccount.isAccountNonExpired() ) {
            throw new AccountExpiredException( getLocalizedMessage( AUTHENTICATION_ACCOUNT_EXPIRED ) );
        }
    }

    private void validateIfCredentialsNonExpired( AccountDO aAccount ) {
        if ( ! aAccount.isCredentialsNonExpired() ) {
            throw new CredentialsExpiredException( getLocalizedMessage( AUTHENTICATION_CREDENTIALS_EXPIRED ) );
        }
    }

    private void validatePassword( AccountDO aAccount, String aPassword ) {
        if ( ! aPassword.equals( aAccount.getPassword() ) ) {
            throw new BadCredentialsException( getLocalizedMessage( AUTHENTICATION_INCORRECT_PASSWORD ) );
        }
    }

    private boolean checkIfMatches( String aText, Pattern aPattern ) {
        var matcher = aPattern.matcher( aText );
        return matcher.matches();
    }
}
