package com.github.pplociennik.auth.business.shared.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A service for accessing system properties.
 *
 * @author Created by: Pplociennik at 20.07.2022 23:17
 */
@PropertySource( "classpath:application.properties" )
public class EnvironmentPropertiesProvider {

    private final Environment environment;

    @Autowired
    public EnvironmentPropertiesProvider( @NonNull Environment aEnvironment ) {
        environment = requireNonNull( aEnvironment );
    }

    /**
     * Returns a value of the property specified by a key.
     *
     * @param aPropertyKey
     *         a key of the system property
     * @return a value of the property
     * @throws NullPointerException
     *         when parameter is null
     */
    public String getPropertyValue( @NonNull SystemProperty aPropertyKey ) {
        requireNonNull( aPropertyKey );
        return environment.getProperty( aPropertyKey.getName() );
    }
}
