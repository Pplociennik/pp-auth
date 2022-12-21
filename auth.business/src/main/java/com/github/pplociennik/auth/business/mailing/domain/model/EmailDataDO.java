package com.github.pplociennik.auth.business.mailing.domain.model;

import org.springframework.lang.NonNull;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A domain object containing info necessary for creating and sending email messages. Contains elementary information
 * being used in all messages' creation and needs to be extended.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:27
 */
abstract class EmailDataDO implements AddressableDataDO {

    protected final String recipientAddress;
    protected final Locale locale;

    protected EmailDataDO( @NonNull String aRecipientAddress, Locale aLocale ) {
        recipientAddress = requireNonNull( aRecipientAddress );
        locale = requireNonNull( aLocale );
    }
}
