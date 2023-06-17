/*
 * <!--
 *   ~ MIT License
 *   ~
 *   ~ Copyright (c) 2023 Przemysław Płóciennik
 *   ~
 *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   ~ of this software and associated documentation files (the "Software"), to deal
 *   ~ in the Software without restriction, including without limitation the rights
 *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   ~ copies of the Software, and to permit persons to whom the Software is
 *   ~ furnished to do so, subject to the following conditions:
 *   ~
 *   ~ The above copyright notice and this permission notice shall be included in all
 *   ~ copies or substantial portions of the Software.
 *   ~
 *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   ~ SOFTWARE.
 *   -->
 */

package com.github.pplociennik.auth.business.shared.system;

import com.github.pplociennik.commons.system.SystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * A service for accessing system properties.
 *
 * @author Created by: Pplociennik at 20.07.2022 23:17
 */
@PropertySource("classpath:application.properties")
public class SystemPropertiesProvider {

    private final Environment environment;

    @Autowired
    public SystemPropertiesProvider( @NonNull Environment aEnvironment ) {
        environment = requireNonNull( aEnvironment );
    }

    /**
     * Returns a value of the property specified by a key.
     *
     * @param aProperty
     *         a key of the system property
     *
     * @return a value of the property
     *
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
