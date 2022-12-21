package com.github.pplociennik.auth.business.shared.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

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
     * @param aProperty
     *         a key of the system property
     * @return a value of the property
     * @throws NullPointerException
     *         when parameter is null
     */
    public String getPropertyValue( @NonNull SystemProperty aProperty ) {
        requireNonNull( aProperty );

        var propertyName = aProperty.getName();
        var possibleValues = aProperty.getPossibleValues();

        var propertyValue = environment.getProperty( propertyName );
        return isNotEmpty( possibleValues )
               ? validatePropertyValue( possibleValues, propertyValue )
               : propertyValue;
    }

    private String validatePropertyValue( Set< String > aPossibleValues, String aPropertyValue ) {

        if ( aPossibleValues.contains( aPropertyValue ) ) {
            return aPropertyValue;
        }

        throw new IllegalArgumentException(
                "Incorrect value of the property: " + aPropertyValue + ".\n Correct values are: " + aPossibleValues );
    }
}
