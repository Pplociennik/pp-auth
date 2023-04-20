package com.github.pplociennik.auth.business.authentication;

import auth.dto.AccountDto;
import auth.dto.ConfirmationLinkGenerationDto;
import com.github.pplociennik.auth.business.shared.events.OnAccountConfirmationCompleteEvent;
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.commons.events.PublishableEvent;
import com.github.pplociennik.commons.events.PublishableEventsSupplier;
import com.github.pplociennik.commons.utility.LanguageUtil;
import org.springframework.lang.NonNull;

import java.util.Locale;

import static com.github.pplociennik.commons.utility.CustomObjects.validateNonNull;
import static com.github.pplociennik.commons.utility.CustomObjects.validateType;
import static java.util.Objects.requireNonNull;

/**
 * A factory for creating events being published during the authentication processes.
 *
 * @author Created by: Pplociennik at 30.12.2022 02:52
 */
enum AuthenticationPublishableEventsSupplier implements PublishableEventsSupplier {

    ON_REGISTRATION_FINISHED( ConfirmationLinkGenerationDto.class ) {
        @Override
        public PublishableEvent getEvent( @NonNull Object aSource ) {
            requireNonNull( aSource );
            validateType( aSource, getRequiredSourceType() );
            return new OnRegistrationCompleteEvent( aSource, LanguageUtil.getLocale() );
        }

        @Override
        public PublishableEvent getEvent( @NonNull Object aSource, @NonNull Locale aLocale ) {
            validateNonNull( aSource, aLocale );
            validateType( aSource, getRequiredSourceType() );
            return new OnRegistrationCompleteEvent( aSource, aLocale );
        }

        @Override
        public Class< ? > getRequiredSourceType() {
            return sourceType;
        }
    },

    ON_ACCOUNT_CONFIRMATION_FINISHED( AccountDto.class ) {
        @Override
        public PublishableEvent getEvent( @NonNull Object aSource ) {
            requireNonNull( aSource );
            validateType( aSource, getRequiredSourceType() );
            return new OnAccountConfirmationCompleteEvent( aSource, LanguageUtil.getLocale() );
        }

        @Override
        public PublishableEvent getEvent( @NonNull Object aSource, @NonNull Locale aLocale ) {
            validateNonNull( aSource, aLocale );
            validateType( aSource, getRequiredSourceType() );
            return new OnAccountConfirmationCompleteEvent( aSource, aLocale );
        }

        @Override
        public Class< ? > getRequiredSourceType() {
            return sourceType;
        }
    };

    protected final Class< ? > sourceType;


    AuthenticationPublishableEventsSupplier( Class< ? > aSourceType ) {
        sourceType = aSourceType;
    }
}
