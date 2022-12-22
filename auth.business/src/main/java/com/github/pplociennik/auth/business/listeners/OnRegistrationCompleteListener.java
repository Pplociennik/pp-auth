package com.github.pplociennik.auth.business.listeners;

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.listeners.ListenerParametersUtil.getSourceOfTheProperType;
import static java.util.Objects.requireNonNull;

/**
 * A listener for {@link OnRegistrationCompleteEvent}. Resolves sending an email with account confirmation request.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:33
 */
class OnRegistrationCompleteListener implements ApplicationListener< OnRegistrationCompleteEvent > {

    private final AuthenticationFacade authenticationFacade;
    private final EmailFacade emailFacade;

    @Autowired
    OnRegistrationCompleteListener(
            @NonNull AuthenticationFacade aAuthenticationFacade, @NonNull EmailFacade aEmailFacade ) {
        authenticationFacade = requireNonNull( aAuthenticationFacade );
        emailFacade = requireNonNull( aEmailFacade );
    }

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event ) {

        requireNonNull( event );

        var locale = event.getLocale();
        var source = event.getSource();

        var accountDto = getSourceOfTheProperType( source, AccountDto.class );

        var recipientAddress = accountDto.getEmailAddress();
        var confirmationLink = authenticationFacade.createNewAccountConfirmationLink(
                AccountMapper.mapToDomain( accountDto ) );

        var emailData = new EmailConfirmationDataDto( recipientAddress, confirmationLink, locale );
        emailFacade.sendEmailConfirmationRequest( emailData );

    }
}
