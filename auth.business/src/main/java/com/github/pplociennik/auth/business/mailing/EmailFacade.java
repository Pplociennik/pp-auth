package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;
import com.github.pplociennik.auth.common.mailing.dto.WelcomeEmailDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;

import static com.github.pplociennik.auth.business.mailing.domain.map.MailingMapper.mapToDO;
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

    @Async
    public void sendEmailConfirmationRequest( @NonNull EmailConfirmationDataDto aDto ) {
        var emailDataDO = mapToDO( aDto );
        emailService.prepareAndSendEmailConfirmationRequest( emailDataDO );
    }

    @Async
    public void sendWelcomeEmail( @NonNull WelcomeEmailDataDto aDto ) {
        var emailDataDO = mapToDO( aDto );
        emailService.prepareAndSendWelcomeEmail( emailDataDO );
    }
}
