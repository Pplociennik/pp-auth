package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.AddressableDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.WelcomeEmailDataDO;
import com.github.pplociennik.auth.business.shared.system.EnvironmentPropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;

import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.EMAIL_CONFIRMATION_MESSAGE;
import static com.github.pplociennik.auth.business.mailing.EmailContentDataCreationStrategy.WELCOME_EMAIL_MESSAGE;
import static com.github.pplociennik.auth.business.shared.system.SystemProperty.GLOBAL_EMAILS_SENDING;
import static com.github.pplociennik.auth.business.shared.system.SystemProperty.MAILING_SENDER_ADDRESS;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.EMAIL_ACCOUNT_CONFIRMATION_SUBJECT;
import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.WELCOME_EMAIL_SUBJECT;
import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static com.github.pplociennik.util.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * A service sharing methods for project specific mailing functionalities.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:19
 */
class EmailService {

    private final EnvironmentPropertiesProvider propertiesProvider;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    EmailService(
            @NonNull EnvironmentPropertiesProvider aPropertiesProvider, @NonNull TemplateEngine aTemplateEngine,
            @NonNull JavaMailSender aMailSender ) {
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

        var senderAddress = propertiesProvider.getPropertyValue( MAILING_SENDER_ADDRESS );
        var recipientAddress = aDataDO.getRecipientAddress();

        var contentData = getContentData( EMAIL_CONFIRMATION_MESSAGE, aDataDO );
        var locale = contentData.getLocale();
        var content = templateEngine.process( contentData.getTemplateFile(), contentData.getContext() );

        var message = prepareMessage( senderAddress, recipientAddress, content,
                                      getLocalizedMessage( EMAIL_ACCOUNT_CONFIRMATION_SUBJECT, locale ), true );

        send( message );
    }

    /**
     * Prepares and sends an email message being a welcome email sent after account confirmation.
     *
     * @param aDataDO
     *         a domain object containing the information necessary for preparing and sending message.
     */
    void prepareAndSendWelcomeEmail( @NonNull WelcomeEmailDataDO aDataDO ) {
        requireNonNull( aDataDO );

        var senderAddress = propertiesProvider.getPropertyValue( MAILING_SENDER_ADDRESS );
        var recipientAddress = aDataDO.getRecipientAddress();

        var contentData = getContentData( WELCOME_EMAIL_MESSAGE, aDataDO );
        var locale = contentData.getLocale();
        var content = templateEngine.process( contentData.getTemplateFile(), contentData.getContext() );

        var message = prepareMessage( senderAddress, recipientAddress, content,
                                      getLocalizedMessage( WELCOME_EMAIL_SUBJECT, locale ), true );

        send( message );
    }

    private MimeMessagePreparator prepareMessage(
            @NonNull String aSenderAddress, @NonNull String aRecipientAddress, @NonNull String aContent,
            @NonNull String aSubject, boolean aHtml ) {
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
            @NonNull EmailContentDataCreationStrategy aEmailConfirmationMessage,
            @NonNull AddressableDataDO aSendingDO ) {
        requireNonNull( aEmailConfirmationMessage );
        requireNonNull( aSendingDO );

        return aEmailConfirmationMessage.prepare( aSendingDO );
    }

    private void send( @NonNull MimeMessagePreparator aMessagePreparator ) {
        requireNonNull( aMessagePreparator );
        
        if ( checkIfCanBeSent() ) {
            mailSender.send( aMessagePreparator );
        }
    }

    private Boolean checkIfCanBeSent() {
        var property = propertiesProvider.getPropertyValue( GLOBAL_EMAILS_SENDING );
        return Boolean.parseBoolean( property );
    }
}
