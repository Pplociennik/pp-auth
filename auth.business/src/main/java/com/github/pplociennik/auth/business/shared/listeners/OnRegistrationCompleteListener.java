package com.github.pplociennik.auth.business.shared.listeners;

import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.auth.common.auth.dto.AccountDto;
import com.github.pplociennik.auth.common.auth.dto.mailing.EmailConfirmationDataDto;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToDomain;
import static java.util.Objects.requireNonNull;

/**
 * A listener for {@link OnRegistrationCompleteEvent}. Resolves sending an email with account confirmation request.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:33
 */
class OnRegistrationCompleteListener implements ApplicationListener< OnRegistrationCompleteEvent > {

    private final AuthenticationFacade authenticationFacade;
    private final EmailFacade emailFacade;

    OnRegistrationCompleteListener( @NonNull AuthenticationFacade aAuthenticationFacade, @NonNull EmailFacade aEmailFacade ) {
        authenticationFacade = requireNonNull( aAuthenticationFacade );
        emailFacade = requireNonNull( aEmailFacade );
    }

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event ) {

        requireNonNull( event );

        var accountDto = ( ( AccountDto ) event.getSource() );
        var locale = event.getLocale();

        var recipientAddress = accountDto.getEmailAddress();
        var confirmationLink = authenticationFacade.createNewAccountConfirmationLink( mapToDomain( accountDto ) );

        var emailData = new EmailConfirmationDataDto( recipientAddress, confirmationLink, locale );
        emailFacade.sendEmailConfirmationRequest( emailData );

    }
}
