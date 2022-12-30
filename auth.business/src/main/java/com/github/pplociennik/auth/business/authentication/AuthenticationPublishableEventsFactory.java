package com.github.pplociennik.auth.business.authentication;

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.shared.events.OnAccountConfirmationCompleteEvent;
import com.github.pplociennik.auth.business.shared.events.OnRegistrationCompleteEvent;
import com.github.pplociennik.commons.events.PublishableEvent;
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
enum AuthenticationPublishableEventsFactory {

    ON_REGISTRATION_FINISHED( AccountDto.class ) {
        @Override
        PublishableEvent getEvent( @NonNull Object aSource ) {
            requireNonNull( aSource );
            validateType( aSource, getSourceType() );
            return new OnRegistrationCompleteEvent( aSource, LanguageUtil.getLocale() );
        }

        @Override
        PublishableEvent getEvent( @NonNull Object aSource, @NonNull Locale aLocale ) {
            validateNonNull( aSource, aLocale );
            validateType( aSource, getSourceType() );
            return new OnRegistrationCompleteEvent( aSource, aLocale );
        }
    },

    ON_ACCOUNT_CONFIRMATION_FINISHED( AccountDto.class ) {
        @Override
        PublishableEvent getEvent( @NonNull Object aSource ) {
            requireNonNull( aSource );
            validateType( aSource, getSourceType() );
            return new OnAccountConfirmationCompleteEvent( aSource, LanguageUtil.getLocale() );
        }

        @Override
        PublishableEvent getEvent( @NonNull Object aSource, @NonNull Locale aLocale ) {
            validateNonNull( aSource, aLocale );
            validateType( aSource, getSourceType() );
            return new OnAccountConfirmationCompleteEvent( aSource, aLocale );
        }
    };

    private final Class< ? > sourceType;

    AuthenticationPublishableEventsFactory( Class< ? > aSourceType ) {
        sourceType = aSourceType;
    }

    Class< ? > getSourceType() {
        return sourceType;
    }

    abstract PublishableEvent getEvent( @NonNull Object aSource );

    abstract PublishableEvent getEvent( @NonNull Object aSource, @NonNull Locale aLocale );
}
