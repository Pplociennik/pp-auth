package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.WelcomeEmailDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;

import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.EMAIL_CONFIRMATION_MESSAGE;
import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.WELCOME_EMAIL_MESSAGE;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.EMAIL_ACCOUNT_CONFIRMATION_SUBJECT;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.WELCOME_EMAIL_SUBJECT;
import static java.util.Objects.requireNonNull;

/**
 * A facade providing functionalities for sending email messages.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:16
 */
public class EmailFacade {

    private final EmailService emailService;

    @Autowired
    public EmailFacade( @NonNull EmailService aEmailService ) {
        emailService = requireNonNull( aEmailService );
    }

    /**
     * Prepares and sends an email message being an account confirmation request.
     *
     * @param aDataDO
     *         a domain object containing the information necessary for preparing and sending message.
     */
    public void sendEmailConfirmationRequest( @NonNull EmailConfirmationDataDO aDataDO ) {
        emailService.send( aDataDO, EMAIL_CONFIRMATION_MESSAGE, EMAIL_ACCOUNT_CONFIRMATION_SUBJECT, true );
    }

    /**
     * Prepares and sends an email message being a welcome email sent after account confirmation.
     *
     * @param aDataDO
     *         a domain object containing the information necessary for preparing and sending message.
     */
    @Async
    public void sendWelcomeEmail( @NonNull WelcomeEmailDataDO aDataDO ) {
        emailService.send( aDataDO, WELCOME_EMAIL_MESSAGE, WELCOME_EMAIL_SUBJECT, true );
    }
}
