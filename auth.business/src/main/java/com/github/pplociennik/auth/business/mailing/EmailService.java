package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.AddressableDataDO;
import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;

import static com.github.pplociennik.auth.business.shared.system.SystemProperties.GLOBAL_EMAILS_SENDING;
import static com.github.pplociennik.auth.business.shared.system.SystemProperties.MAILING_SENDER_ADDRESS;
import static com.github.pplociennik.commons.utility.CustomObjects.requireNonEmpty;
import static com.github.pplociennik.commons.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * A service sharing methods for mailing functionalities.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:19
 */
class EmailService {

    private final JavaMailSender mailSender;
    private final SystemPropertiesProvider propertiesProvider;
    private final TemplateEngine templateEngine;


    @Autowired
    EmailService( @NonNull JavaMailSender aMailSender, @NonNull SystemPropertiesProvider aPropertiesProvider, TemplateEngine aTemplateEngine ) {
        mailSender = requireNonNull( aMailSender );
        propertiesProvider = requireNonNull( aPropertiesProvider );
        templateEngine = aTemplateEngine;
    }

    /**
     * Prepares a message's content based on the specified strategy and data provided.
     *
     * @param aContentCreationStrategy
     *         a strategy of preparing message's content.
     * @param aSendingDataDO
     *         data based on which the message is being created.
     *
     * @return the object containing the message's content data such as template, context and locale.
     */
    public EmailContentData getContentData(
            @NonNull EmailContentDataCreationStrategy aContentCreationStrategy,
            @NonNull AddressableDataDO aSendingDataDO ) {
        requireNonNull( aContentCreationStrategy );
        requireNonNull( aSendingDataDO );

        return aContentCreationStrategy.prepare( aSendingDataDO );
    }

    /**
     * Prepares and returns a message to be sent.
     *
     * @param aSenderAddress
     *         an email address of the message's sender.
     * @param aRecipientAddress
     *         an email address of the message's receiver.
     * @param aContent
     *         the message's content.
     * @param aSubject
     *         the message's subject.
     * @param aIsHtml
     *         a flag determining if the message's content is in the HTML format.
     *
     * @return prepared message ready to be sent.
     */
    public MimeMessagePreparator prepareMessage(
            @NonNull String aSenderAddress, @NonNull String aRecipientAddress, @NonNull String aContent,
            @NonNull String aSubject, boolean aIsHtml ) {
        requireNonEmpty( aSenderAddress );
        requireNonEmpty( aRecipientAddress );
        requireNonEmpty( aContent );
        requireNonEmpty( aSubject );

        return mimeMessage -> {
            var messageHelper = new MimeMessageHelper( mimeMessage );

            messageHelper.setFrom( aSenderAddress );
            messageHelper.setTo( aRecipientAddress );
            messageHelper.setSubject( aSubject );
            messageHelper.setText( aContent, aIsHtml );
        };
    }

    /**
     * Prepares a message based on the specified attributes and sends it if possible.
     *
     * @param aDataDO
     *         data necessary for the message's creation.
     * @param aCreationStrategy
     *         the message's creation strategy.
     * @param aSubject
     *         the message's subject.
     * @param aIsHtml
     *         a flag determining if the message's content is in the HTML format.
     */
    @Async
    public void send( AddressableDataDO aDataDO, EmailContentDataCreationStrategy aCreationStrategy, AuthResEmailMsgTranslationKey aSubject, boolean aIsHtml ) {
        requireNonNull( aDataDO );

        var senderAddress = propertiesProvider.getPropertyValue( MAILING_SENDER_ADDRESS );
        var recipientAddress = aDataDO.getRecipientAddress();

        var contentData = getContentData( aCreationStrategy, aDataDO );
        var locale = contentData.getLocale();
        var content = templateEngine.process( contentData.getTemplateFile(), contentData.getContext() );

        var message = prepareMessage( senderAddress, recipientAddress, content, getLocalizedMessage( aSubject, locale ), aIsHtml );

        send( message );
    }

    /**
     * Sends an email message if possible.
     *
     * @param aMessage
     *         the message to be sent.
     */
    @Async
    public void send( @NonNull MimeMessagePreparator aMessage ) {
        requireNonNull( aMessage );

        if ( checkIfCanBeSent() ) {
            mailSender.send( aMessage );
        }
    }

    private Boolean checkIfCanBeSent() {
        var property = propertiesProvider.getPropertyValue( GLOBAL_EMAILS_SENDING );
        return Boolean.parseBoolean( property );
    }

}
