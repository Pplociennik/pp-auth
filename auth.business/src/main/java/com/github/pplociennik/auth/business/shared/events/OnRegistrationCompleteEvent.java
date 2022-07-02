package com.github.pplociennik.auth.business.shared.events;

import com.github.pplociennik.auth.common.auth.dto.AccountDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * An event being published on the new account's registration completion.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:13
 */
@Setter
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final AccountDto account;

    public OnRegistrationCompleteEvent( @NonNull Object source, @NonNull String aAppUrl, @NonNull Locale aLocale, @NonNull AccountDto aAccount ) {
        super( source );

        appUrl = requireNonNull( aAppUrl );
        locale = requireNonNull( aLocale );
        account = requireNonNull( aAccount );
    }
}
