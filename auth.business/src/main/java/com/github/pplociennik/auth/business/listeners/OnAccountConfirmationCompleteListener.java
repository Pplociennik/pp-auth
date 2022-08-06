package com.github.pplociennik.auth.business.listeners;

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.business.shared.events.OnAccountConfirmationCompleteEvent;
import com.github.pplociennik.auth.common.mailing.dto.WelcomeEmailDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.auth.business.listeners.ListenerParametersUtil.getSourceOfTheProperType;
import static java.util.Objects.requireNonNull;

/**
 * A listener for {@link OnAccountConfirmationCompleteEvent}. Resolves sending the welcome email.
 *
 * @author Created by: Pplociennik at 06.08.2022 22:36
 */
class OnAccountConfirmationCompleteListener implements ApplicationListener< OnAccountConfirmationCompleteEvent > {

    private final EmailFacade emailFacade;

    @Autowired
    public OnAccountConfirmationCompleteListener( EmailFacade aEmailFacade ) {
        emailFacade = aEmailFacade;
    }

    @Override
    public void onApplicationEvent( @NonNull OnAccountConfirmationCompleteEvent event ) {

        requireNonNull( event );

        var locale = event.getLocale();
        var source = event.getSource();
        var accountDto = getSourceOfTheProperType( source, AccountDto.class );

        var recipientAddress = accountDto.getEmailAddress();
        var username = accountDto.getUsername();

        var welcomeEmailDataDto = new WelcomeEmailDataDto( recipientAddress, locale, username );

        emailFacade.sendWelcomeEmail( welcomeEmailDataDto );
    }


}
