package com.github.pplociennik.auth.business.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;

/**
 * Provides access to the global properties.
 *
 * @author Created by: Pplociennik at 01.07.2022 16:01
 */
@PropertySource( "classpath:application.properties" )
class SystemPropertiesProvider {

    private static final String PREFIX = "spring.global";
    private static final String FRONTEND_URL_ADDRESS = PREFIX + ".frontend.url";

    private final Environment environment;

    @Autowired
    SystemPropertiesProvider( @NonNull Environment aEnvironment ) {
        environment = requireNonNull( aEnvironment );
    }

    String getFrontendUrl() {
        return getPropertyValue( FRONTEND_URL_ADDRESS );
    }

    private String getPropertyValue( @NonNull String aPropertyKey ) {
        requireNonEmpty( aPropertyKey );
        return environment.getProperty( aPropertyKey );
    }
}
