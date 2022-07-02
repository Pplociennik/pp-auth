package com.github.pplociennik.auth.business.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;

/**
 * A service providing properties necessary for sending email messages e.g. address.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:28
 */
@PropertySource( "classpath:application.properties" )
class MailingPropertiesProvider {

    private static final String PREFIX = "spring.mail";
    private static final String SENDER_ADDRESS = PREFIX + ".username";

    private final Environment environment;

    @Autowired
    MailingPropertiesProvider( @NonNull Environment aEnvironment ) {
        environment = requireNonNull( aEnvironment );
    }

    String getSenderAddress() {
        return getPropertyValue( SENDER_ADDRESS );
    }

    private String getPropertyValue( @NonNull String aPropertyKey ) {
        requireNonEmpty( aPropertyKey );
        return environment.getProperty( aPropertyKey );
    }


}
