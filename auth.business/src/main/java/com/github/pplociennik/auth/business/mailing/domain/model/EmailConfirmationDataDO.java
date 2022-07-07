package com.github.pplociennik.auth.business.mailing.domain.model;

import org.springframework.lang.NonNull;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A domain object holding all the information necessary for email confirmation request message creation and sending.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:31
 */
public final class EmailConfirmationDataDO extends EmailDataDO implements AddressableDataDO {

    private final String confirmationLink;

    public EmailConfirmationDataDO( @NonNull String aRecipientAddress, @NonNull String aConfirmationLink, @NonNull Locale aLocale ) {
        super( aRecipientAddress, aLocale );
        confirmationLink = requireNonNull( aConfirmationLink );
    }

    @Override
    public String getRecipientAddress() {
        return recipientAddress;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }


}
