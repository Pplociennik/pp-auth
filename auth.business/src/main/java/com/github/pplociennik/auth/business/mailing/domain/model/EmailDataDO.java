package com.github.pplociennik.auth.business.mailing.domain.model;

import org.springframework.lang.NonNull;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * A domain object containing info necessary for creating and sending email messages.
 * Contains elementary information being used in all messages' creation and needs to be extended.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:27
 */
abstract class EmailDataDO implements Serializable {

    protected final String recipientAddress;

    protected EmailDataDO( @NonNull String aRecipientAddress ) {
        recipientAddress = requireNonNull( aRecipientAddress );
    }
}
