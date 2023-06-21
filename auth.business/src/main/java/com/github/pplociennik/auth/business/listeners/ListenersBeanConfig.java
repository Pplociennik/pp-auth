package com.github.pplociennik.auth.business.listeners;

import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.mailing.EmailFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A Spring's configuration class for listeners' beans definition.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:38
 */
@Configuration
class ListenersBeanConfig {

    private final AuthenticationFacade authenticationFacade;
    private final EmailFacade emailFacade;

    ListenersBeanConfig( AuthenticationFacade aAuthenticationFacade, @NonNull EmailFacade aEmailFacade ) {
        authenticationFacade = requireNonNull( aAuthenticationFacade );
        emailFacade = requireNonNull( aEmailFacade );
    }

    @Bean
    OnRegistrationCompleteListener registrationListener() {
        return new OnRegistrationCompleteListener( authenticationFacade, emailFacade );
    }

    @Bean
    OnAccountConfirmationCompleteListener confirmationListener() {
        return new OnAccountConfirmationCompleteListener( emailFacade );
    }
}
