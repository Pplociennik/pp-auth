package com.github.pplociennik.auth.common.mailing.dto;

import com.github.pplociennik.util.utility.CustomObjects;
import org.springframework.lang.NonNull;

import java.util.Locale;

/**
 * A data transfer object containing information necessary for creating and sending welcome emails.
 *
 * @author Created by: Pplociennik at 06.08.2022 22:13
 */
public final class WelcomeEmailDataDto extends EmailDataDto {

    private final String username;

    public WelcomeEmailDataDto( @NonNull String aRecipientAddress, @NonNull Locale aLocale, @NonNull String aUsername ) {
        super( aRecipientAddress, aLocale );
        username = CustomObjects.requireNonEmpty( aUsername );
    }

    @Override
    public String getRecipientAddress() {
        return recipientAddress;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public String getUsername() {
        return username;
    }
}
