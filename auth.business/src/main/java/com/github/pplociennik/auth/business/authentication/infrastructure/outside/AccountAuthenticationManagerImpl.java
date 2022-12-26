package com.github.pplociennik.auth.business.authentication.infrastructure.outside;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.inside.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.inside.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.github.pplociennik.auth.common.lang.AuthResExcMsgTranslationKey.*;
import static com.github.pplociennik.commons.utility.LanguageUtil.getLocalizedMessage;

/**
 * Custom authentication provider.
 *
 * @author Created by: Pplociennik at 14.08.2022 22:45
 */
class AccountAuthenticationManagerImpl implements AuthenticationManager {

    private final AccountRepository accountRepository;
    private final AuthenticationValidationRepository validationRepository;
    private final TimeService timeService;
    private final PasswordEncoder passwordEncoder;

    AccountAuthenticationManagerImpl(
            AccountRepository aAccountRepository, AuthenticationValidationRepository aValidationRepository,
            TimeService aTimeService, PasswordEncoder aPasswordEncoder ) {
        accountRepository = aAccountRepository;
        validationRepository = aValidationRepository;
        timeService = aTimeService;
        passwordEncoder = aPasswordEncoder;
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

        var account = accountRepository.findAccountByUsername( name );
        var isDataValid = executeValidation( account, name, password );

        return isDataValid
               ? authenticateUser( account )
               : null;
    }

    private Authentication authenticateUser( AccountDO aAccountDO ) {
        updateLastLoginDate( aAccountDO );
        var accountAuthorites = AuthorityMapper.mapToAuthorityDetails( aAccountDO.getAuthorities() );
        return new UsernamePasswordAuthenticationToken( aAccountDO.getUsername(), aAccountDO.getPassword(),
                                                        accountAuthorites );
    }

    private void updateLastLoginDate( AccountDO aAccountDO ) {
        var currentDateTime = timeService.getCurrentSystemDateTime();
        aAccountDO.setLastLoginDate( currentDateTime );
        accountRepository.update( aAccountDO );
    }

    private boolean executeValidation( AccountDO aAccount, String aName, String aPassword ) {

        validateName( aName );

        validatePassword( aAccount, aPassword );
        validateIfEnabled( aAccount );
        validateIfNonLocked( aAccount );

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

    private void validatePassword( AccountDO aAccount, String aPassword ) {
        if ( ! passwordEncoder.matches( aPassword, aAccount.getPassword() ) ) {
            throw new BadCredentialsException( getLocalizedMessage( AUTHENTICATION_INCORRECT_PASSWORD ) );
        }
    }

    private void validateName( String aName ) {
        if ( ! checkIfNameExistsInDatabase( aName ) ) {
            throw new BadCredentialsException( getLocalizedMessage( AUTHENTICATION_USERNAME_NOT_FOUND ) );
        }
    }

    private boolean checkIfNameExistsInDatabase( String aName ) {
        return validationRepository.checkIfUsernameExists( aName );
    }
}
