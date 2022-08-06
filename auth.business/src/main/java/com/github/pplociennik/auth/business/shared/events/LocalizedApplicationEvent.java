package com.github.pplociennik.auth.business.shared.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Base application event class containing a locale.
 *
 * @author Created by: Pplociennik at 06.08.2022 22:58
 */
class LocalizedApplicationEvent extends ApplicationEvent {

    protected final Locale locale;

    protected LocalizedApplicationEvent( @NonNull Object aSource, @NonNull Locale aLocale ) {
        super( aSource );
        locale = requireNonNull( aLocale );
    }
}
