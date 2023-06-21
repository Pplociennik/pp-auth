package com.github.pplociennik.auth.business.shared;

import com.github.pplociennik.auth.business.shared.events.SystemEventsPublisher;
import com.github.pplociennik.auth.business.shared.system.SessionService;
import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import com.github.pplociennik.auth.business.shared.system.TimeService;
import com.github.pplociennik.auth.business.shared.system.session.SessionServiceImpl;
import com.github.pplociennik.auth.business.shared.system.time.SystemTimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    AuthSharedBeansConfig( Environment aEnvironment, ApplicationEventPublisher aApplicationEventPublisher ) {
        environment = aEnvironment;
        applicationEventPublisher = aApplicationEventPublisher;
    }

    @Bean
    SystemPropertiesProvider systemPropertiesProvider() {
        return new SystemPropertiesProvider( environment );
    }

    @Bean
    TimeService timeService() {
        return new SystemTimeServiceImpl( systemPropertiesProvider() );
    }

    @Bean
    SystemEventsPublisher systemEventsPublisher() {
        return new SystemEventsPublisher( applicationEventPublisher );
    }

    @Bean
    SessionService sessionService() { return new SessionServiceImpl(); }
}
