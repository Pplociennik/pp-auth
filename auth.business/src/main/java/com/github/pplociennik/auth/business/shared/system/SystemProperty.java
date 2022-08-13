package com.github.pplociennik.auth.business.shared.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum holding a name of the system properties.
 *
 * @author Created by: Pplociennik at 20.07.2022 23:20
 */
@AllArgsConstructor
@Getter
public enum SystemProperty {

    // -- Custom global properties.

    GLOBAL_CLIENT_URL( "spring.global.client.url" ),

    // -- Mailing properties.

    MAILING_SENDER_ADDRESS( "spring.mail.username" ),

    // -- Time Zone properties.

    SYSTEM_JPA_TIME_ZONE( "spring.jpa.properties.hibernate.jdbc.time_zone" );

    private final String name;
}
