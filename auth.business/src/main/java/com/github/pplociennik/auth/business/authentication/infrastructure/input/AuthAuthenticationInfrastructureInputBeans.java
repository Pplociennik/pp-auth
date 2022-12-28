package com.github.pplociennik.auth.business.authentication.infrastructure.input;

import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Beans' definition of the input ports implementations.
 *
 * @author Created by: Pplociennik at 27.12.2022 23:31
 */
@Configuration
class AuthAuthenticationInfrastructureInputBeans {

    private final AccountRepository accountRepository;
    private final TimeService timeService;

    @Autowired
    AuthAuthenticationInfrastructureInputBeans(
            AccountRepository aAccountRepository, TimeService aTimeService ) {
        accountRepository = aAccountRepository;
        timeService = aTimeService;
    }

    @Bean
    AuthenticationManager accountAuthenticationManager() {
        return new AccountAuthenticationManagerImpl( accountRepository, timeService );
    }
}
