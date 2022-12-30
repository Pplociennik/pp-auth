package com.github.pplociennik.auth.business.shared.events;

import com.github.pplociennik.commons.events.BaseLocalizedApplicationEvent;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Locale;

/**
 * An event being published on the new account's registration completion.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:13
 */
@Getter
public class OnRegistrationCompleteEvent extends BaseLocalizedApplicationEvent {
    public OnRegistrationCompleteEvent( @NonNull Object source, @NonNull Locale aLocale ) {
        super( source, aLocale );
    }
}
