package com.github.pplociennik.auth.business.authentication.infrastructure.outside;

import com.github.pplociennik.auth.business.authentication.ports.inside.AccountRepository;
import com.github.pplociennik.auth.business.authentication.ports.inside.AuthenticationValidationRepository;
import com.github.pplociennik.auth.business.authentication.ports.outside.AccountSecurityDataService;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * A Spring's configuration class for authentication beans definition. Infrastructure package part for Outgoing
 * adapters.
 *
 * @author Created by: Pplociennik at 14.08.2022 22:29
 */
@Configuration
class AuthAuthenticationInfrastructureBeansOut {

    private final AccountDao accountDao;
    private final AccountRepository accountRepository;
    private final AuthenticationValidationRepository validationRepository;
    private final TimeService timeService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthAuthenticationInfrastructureBeansOut(
            AccountDao aAccountDao, AccountRepository aAccountRepository,
            AuthenticationValidationRepository aValidationRepository, TimeService aTimeService,
            PasswordEncoder aPasswordEncoder ) {
        accountDao = aAccountDao;
        accountRepository = aAccountRepository;
        validationRepository = aValidationRepository;
        timeService = aTimeService;
        passwordEncoder = aPasswordEncoder;
    }

    @Bean
    AccountSecurityDataService accountSecurityDataService() {
        return new AccountSecurityDataServiceImpl( accountDao );
    }

    @Bean
    UsernamePasswordAuthenticationFilter jsonAuthenticationFilter() {
        var jsonAuthenticationFailter = new JsonAuthenticationFilterImpl();
        jsonAuthenticationFailter.setAuthenticationSuccessHandler( restAuthenticationSuccessHandler() );
        jsonAuthenticationFailter.setAuthenticationFailureHandler( restAuthenticationFailureHandler() );
        jsonAuthenticationFailter.setAuthenticationManager( accountAuthenticationManager() );

        return jsonAuthenticationFailter;
    }

    @Bean
    AuthenticationManager accountAuthenticationManager() {
        return new AccountAuthenticationManagerImpl( accountRepository, validationRepository, timeService,
                                                     passwordEncoder );
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler restAuthenticationFailureHandler() {
        return new RestAuthenticationFailureHandlerImpl();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler restAuthenticationSuccessHandler() {
        return new RestAuthenticationSuccessHandlerImpl();
    }

}
