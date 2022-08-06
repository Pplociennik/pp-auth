package com.github.pplociennik.auth.business.listeners;

import org.springframework.lang.NonNull;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * AAn utility class for application's listeners.
 *
 * @author Created by: Pplociennik at 06.08.2022 23:09
 */
class ListenerParametersUtil {

    static < T > T getSourceOfTheProperType( @NonNull Object aSource, @NonNull Class< T > aType ) {
        requireNonNull( aSource );
        requireNonNull( aType );

        var object = Optional.of( aSource )
                .filter( aType::isInstance )
                .stream()
                .findAny().orElseThrow( () -> new IllegalArgumentException( "Wrong type!" ) );
        return aType.cast( object );
    }
}
