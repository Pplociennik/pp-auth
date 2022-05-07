package com.github.pplociennik.auth.common.auth.dto.mailing;

import org.springframework.lang.NonNull;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * Basic data dto for transferring info necessary for creating and sending email messages.
 * Contains elementary information being used in all messages' creation and needs to be extended.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:18
 */
abstract class EmailDataDto implements Serializable {

    protected final String recipientAddress;

    protected EmailDataDto( @NonNull String aRecipientAddress ) {
        recipientAddress = requireNonNull( aRecipientAddress );
    }
}
