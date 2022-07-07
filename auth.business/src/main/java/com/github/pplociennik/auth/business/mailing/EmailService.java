package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;

import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.EMAIL_CONFIRMATION_MESSAGE;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.EMAIL_ACCOUNT_CONFIRMATION_SUBJECT;
import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static com.github.pplociennik.util.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * A service sharing methods for project specific mailing functionalities.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:19
 */
class EmailService {

    private final MailingPropertiesProvider propertiesProvider;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    EmailService( @NonNull MailingPropertiesProvider aPropertiesProvider, @NonNull TemplateEngine aTemplateEngine, @NonNull JavaMailSender aMailSender ) {
        propertiesProvider = requireNonNull( aPropertiesProvider );
        templateEngine = requireNonNull( aTemplateEngine );
        mailSender = requireNonNull( aMailSender );
    }

    /**
     * Prepares and sends an email message being an account confirmation request.
     *
     * @param aDataDO
     *         a domain object containing the information necessary for preparing and sending message.
     */
    void prepareAndSendEmailConfirmationRequest( @NonNull EmailConfirmationDataDO aDataDO ) {
        requireNonNull( aDataDO );

        var senderAddress = propertiesProvider.getSenderAddress();
        var recipientAddress = aDataDO.getRecipientAddress();

        var contentData = getContentData( EMAIL_CONFIRMATION_MESSAGE, aDataDO );
        var content = templateEngine.process( contentData.getTemplateFile(), contentData.getContext() );

        var message = prepareMessage( senderAddress, recipientAddress, content, getLocalizedMessage( EMAIL_ACCOUNT_CONFIRMATION_SUBJECT ), true );

        send( message );
    }

    private MimeMessagePreparator prepareMessage( @NonNull String aSenderAddress, @NonNull String aRecipientAddress, @NonNull String aContent, @NonNull String aSubject, boolean aHtml ) {
        requireNonEmpty( aSenderAddress );
        requireNonEmpty( aRecipientAddress );
        requireNonEmpty( aContent );
        requireNonEmpty( aSubject );

        return mimeMessage -> {
            var messageHelper = new MimeMessageHelper( mimeMessage );

            messageHelper.setFrom( aSenderAddress );
            messageHelper.setTo( aRecipientAddress );
            messageHelper.setSubject( aSubject );
            messageHelper.setText( aContent, aHtml );
        };
    }

    private EmailContentData getContentData(
            @NonNull EmailContentDataCreationStrategy
                    aEmailConfirmationMessage, @NonNull EmailConfirmationDataDO aSendingDO ) {
        requireNonNull( aEmailConfirmationMessage );
        requireNonNull( aSendingDO );

        return aEmailConfirmationMessage.prepare( aSendingDO );
    }

    private void send( @NonNull MimeMessagePreparator aMessagePreparator ) {
        requireNonNull( aMessagePreparator );
        mailSender.send( aMessagePreparator );
    }
}
