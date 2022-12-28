package com.github.pplociennik.auth.business.shared;

import com.github.pplociennik.auth.business.shared.system.EnvironmentPropertiesProvider;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import com.github.pplociennik.auth.business.shared.system.time.SystemTimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Definitions of the shared beans in business module.
 *
 * @author Created by: Pplociennik at 28.12.2022 00:01
 */
@Configuration
class AuthSharedBeansConfig {

    private final Environment environment;

    @Autowired
    AuthSharedBeansConfig( Environment aEnvironment ) {
        environment = aEnvironment;
    }

    @Bean
    EnvironmentPropertiesProvider environmentPropertiesProvider() {
        return new EnvironmentPropertiesProvider( environment );
    }

    @Bean
    TimeService timeService() {
        return new SystemTimeServiceImpl( environmentPropertiesProvider() );
    }
}
