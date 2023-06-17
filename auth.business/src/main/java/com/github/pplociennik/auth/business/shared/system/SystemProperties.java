package com.github.pplociennik.auth.business.shared.system;

import com.github.pplociennik.commons.system.SystemProperty;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An enum holding a name of the system properties.
 *
 * @author Created by: Pplociennik at 20.07.2022 23:20
 */
@Getter
public enum SystemProperties implements SystemProperty {

    // -- Custom global properties.

    GLOBAL_CLIENT_URL( "pp.auth.global.client.url" ),

    GLOBAL_EMAILS_SENDING( "pp.auth.global.emails.sending", "true", "false" ),

    // -- Mailing properties.

    MAILING_SENDER_ADDRESS( "spring.mail.username" ),

    // -- Time Zone properties.

    SYSTEM_JPA_TIME_ZONE( "spring.jpa.properties.hibernate.jdbc.time_zone" );

    private final String name;
    private final Set< String > possibleValues;

    private static Set< String > getAsSet( String[] aPossibleValues ) {
        return Stream
                .of( aPossibleValues )
                .filter( Objects::nonNull )
                .collect( Collectors.toSet() );
    }

    // ### Private helper methods ###

    SystemProperties( String aName, String... aPossibleValues ) {
        name = aName;
        possibleValues = getAsSet( aPossibleValues );
    }
}
