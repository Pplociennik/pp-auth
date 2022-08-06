package com.github.pplociennik.auth.business.shared.events;

import lombok.Getter;

import java.util.Locale;

/**
 * An event being published on the account's confirmation completion.
 *
 * @author Created by: Pplociennik at 06.08.2022 22:33
 */
@Getter
public class OnAccountConfirmationCompleteEvent extends LocalizedApplicationEvent {

    public OnAccountConfirmationCompleteEvent( Object source, Locale aLocale ) {
        super( source, aLocale );
    }

    public Locale getLocale() {
        return locale;
    }

}
