package com.github.pplociennik.auth.business.testingUtils;

import com.github.pplociennik.util.exc.ValidationException;
import org.assertj.core.api.ThrowableAssert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An util containing methods for suppressed messages of the {@link ValidationException} assertions.
 *
 * @author Created by: Pplociennik at 29.07.2022 22:50
 */
public class ValidationExceptionMessageAssertions {

    /**
     * Asserts that the method being called will throw the {@link ValidationException} with a proper suppressed message.
     *
     * @param aCallable
     *         a method being called.
     * @param aExpectedMessage
     *         an expected suppressed message.
     */
    public static void assertSuppressedValidationMessagesContain( ThrowableAssert.ThrowingCallable aCallable, String aExpectedMessage ) {
        List< String > messages = new LinkedList<>();
        try {
            aCallable.call();
        } catch ( ValidationException e ) {
            messages = getMessages( e.getSuppressed() );
        } catch ( Throwable aE ) {
            throw new RuntimeException( aE );
        }
        assertThat( messages ).contains( aExpectedMessage );
    }

    private static List< String > getMessages( Throwable[] aSuppressed ) {
        return Arrays.stream( aSuppressed )
                .map( Throwable::getMessage )
                .map( ValidationExceptionMessageAssertions::mapMessagesToTable )
                .flatMap( Arrays::stream )
                .collect( Collectors.toUnmodifiableList() );
    }

    private static String[] mapMessagesToTable( String aMessagesAsString ) {
        var lastIndex = aMessagesAsString.length() - 1;
        var cutString = aMessagesAsString.substring( 1, lastIndex );

        return cutString.split( ";" );
    }
}
