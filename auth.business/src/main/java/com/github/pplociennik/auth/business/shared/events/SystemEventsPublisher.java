package com.github.pplociennik.auth.business.shared.events;

import com.github.pplociennik.commons.events.PublishableEventsSupplier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;

import java.util.Locale;

import static com.github.pplociennik.commons.utility.CustomObjects.validateNonNull;
import static java.util.Objects.requireNonNull;

/**
 * A service for events publishing.
 *
 * @author Created by: Pplociennik at 20.04.2023 18:37
 */
public class SystemEventsPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SystemEventsPublisher( ApplicationEventPublisher aEventPublisher ) {
        eventPublisher = aEventPublisher;
    }

    /**
     * Publishes the specified event.
     *
     * @param aApplicationEvent
     *         the event to be published.
     */
    public void publishEvent( @NonNull ApplicationEvent aApplicationEvent ) {
        requireNonNull( aApplicationEvent );
        eventPublisher.publishEvent( aApplicationEvent );
    }

    /**
     * Publishes the event basing on the provided supplier and source object.
     *
     * @param aPublishableEventsSupplier
     *         a supplier creating the proper event.
     * @param aSourceObject
     *         a source object being a base for the event's creation.
     */
    public void publishEvent(
            @NonNull PublishableEventsSupplier aPublishableEventsSupplier, @NonNull Object aSourceObject ) {
        validateNonNull( aPublishableEventsSupplier, aSourceObject );
        var event = aPublishableEventsSupplier.getEvent( aSourceObject );
        eventPublisher.publishEvent( event );
    }

    /**
     * Publishes the event basing on the provided supplier, source object and locale specified.
     *
     * @param aPublishableEventsSupplier
     *         a supplier creating the proper event.
     * @param aSourceObject
     *         a source object being a base for the event's creation.
     * @param aLocale
     *         a locale.
     */
    public void publishEvent(
            @NonNull PublishableEventsSupplier aPublishableEventsSupplier, @NonNull Object aSourceObject,
            @NonNull Locale aLocale ) {
        validateNonNull( aPublishableEventsSupplier, aSourceObject, aLocale );
        var event = aPublishableEventsSupplier.getEvent( aSourceObject, aLocale );
        eventPublisher.publishEvent( event );
    }
}
