package com.github.pplociennik.auth.business.listeners;

import auth.dto.ConfirmationLinkRequestDto;
import com.github.pplociennik.auth.business.authentication.AuthenticationFacade;
import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.business.mailing.domain.map.MailingMapper;
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    OnRegistrationCompleteListener( AuthenticationFacade aAuthenticationFacade, @NonNull EmailFacade aEmailFacade ) {
        authenticationFacade = requireNonNull( aAuthenticationFacade );
        emailFacade = requireNonNull( aEmailFacade );
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void onApplicationEvent( @NonNull OnRegistrationCompleteEvent aEvent ) {

        requireNonNull( aEvent );

        var locale = aEvent.getLocale();
        var source = aEvent.getSource();

        var confirmationLinkData = getSourceOfTheProperType( source, ConfirmationLinkRequestDto.class );

        var recipientAddress = confirmationLinkData.getEmailAddress();
        var confirmationLink = authenticationFacade.createNewAccountConfirmationLink( recipientAddress );

        var emailData = new EmailConfirmationDataDto( recipientAddress, confirmationLink, locale );
        emailFacade.sendEmailConfirmationRequest( MailingMapper.mapToDO( emailData ) );

    }
}
