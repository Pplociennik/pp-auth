package com.github.pplociennik.auth.business.mailing.domain.model;

import org.springframework.lang.NonNull;

import java.util.Locale;

import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;

/**
 * A domain object holding all the information necessary for welcome email message creation and sending.
 *
 * @author Created by: Pplociennik at 06.08.2022 22:06
 */
public final class WelcomeEmailDataDO extends EmailDataDO implements AddressableDataDO {

    private final String username;

    public WelcomeEmailDataDO( @NonNull String aRecipientAddress, @NonNull Locale aLocale, @NonNull String aUsername ) {
        super( aRecipientAddress, aLocale );
        username = requireNonEmpty( aUsername );
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
