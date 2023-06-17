package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.mailing.domain.model.AddressableDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.WelcomeEmailDataDO;
import com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey;
import com.github.pplociennik.auth.common.lang.AuthResUnitTranslationKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Optional;

import static com.github.pplociennik.auth.common.lang.AuthResEmailMsgTranslationKey.*;
import static com.github.pplociennik.auth.common.lang.AuthResUnitTranslationKey.MINUTES;
import static com.github.pplociennik.commons.utility.CustomObjects.arrayOf;
import static com.github.pplociennik.commons.utility.LanguageUtil.getLocalizedMessage;
import static java.util.Objects.requireNonNull;

/**
 * A strategy for creating context data for email messages. Context data consists of the message's context and template
 * file.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:00
 */
@AllArgsConstructor
@Getter
enum EmailContentDataCreationStrategy {

    EMAIL_CONFIRMATION_MESSAGE( "confirmationRequestEmailTemplate" ) {
        @Override
        EmailContentData prepare( @NonNull AddressableDataDO aDataDO ) {
            requireNonNull( aDataDO );

            var emailData = getProperTypeOfDataDO( aDataDO, EmailConfirmationDataDO.class );
            var locale = emailData.getLocale();

            var context = new Context();

            context.setVariable( "message", getLocalizedMessage( EMAIL_ACCOUNT_CONFIRMATION_MESSAGE, locale ) );
            context.setVariable( "confirmationLink", emailData.getConfirmationLink() );
            context.setVariable( "disclaimer",
                    getLocalizedDisclaimer( EMAIL_ACCOUNT_CONFIRMATION_DISCLAIMER, locale, 15L,
                            MINUTES ) );

            return EmailContentData.of( context, getTemplateFile(), locale );
        }

    },

    WELCOME_EMAIL_MESSAGE( "welcomeEmailTemplate" ) {
        @Override
        EmailContentData prepare( AddressableDataDO aDataDO ) {
            requireNonNull( aDataDO );

            var emailData = getProperTypeOfDataDO( aDataDO, WelcomeEmailDataDO.class );
            var locale = emailData.getLocale();

            var context = new Context();

            context.setVariable( "welcome", getLocalizedMessage( WELCOME_EMAIL_WELCOME, locale,
                    arrayOf( emailData.getUsername() ) ) );
            context.setVariable( "welcome_text", getLocalizedMessage( WELCOME_EMAIL_WELCOME_TEXT, locale ) );
            context.setVariable( "regards", getLocalizedMessage( WELCOME_EMAIL_REGARDS, locale ) );

            return EmailContentData.of( context, getTemplateFile(), locale );
        }
    };

    private final String templateFile;

    // ### Private helper methods.
    private static < T > T getProperTypeOfDataDO( @NonNull AddressableDataDO aDataDO, @NonNull Class< T > aType ) {
        requireNonNull( aDataDO );
        requireNonNull( aType );

        var checkedObject = Optional
                .of( aDataDO )
                .stream()
                .filter( aType::isInstance )
                .findAny()
                .orElseThrow( () -> new IllegalArgumentException( "Wrong type!" ) );
        return aType.cast( checkedObject );
    }

    private static String getLocalizedDisclaimer(
            @NonNull AuthResEmailMsgTranslationKey aBaseMessage, Locale aLocale, long aAmount,
            @NonNull AuthResUnitTranslationKey aUnit ) {
        requireNonNull( aBaseMessage );
        requireNonNull( aUnit );

        var localizedUnit = getLocalizedMessage( aUnit, aLocale );
        var parametersForFullLocalization = new Object[]{ aAmount, localizedUnit };
        return getLocalizedMessage( aBaseMessage, aLocale, parametersForFullLocalization );
    }

    abstract EmailContentData prepare( @NonNull AddressableDataDO aDataDO );
}
