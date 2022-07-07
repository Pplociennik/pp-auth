package com.github.pplociennik.auth.common.auth.dto.mailing;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Basic data dto for transferring info necessary for creating and sending email messages.
 * Contains elementary information being used in all messages' creation and needs to be extended.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:18
 */
abstract class EmailDataDto implements Serializable {

    protected final String recipientAddress;
    protected final Locale locale;

    protected EmailDataDto( @NonNull String aRecipientAddress, @NonNull Locale aLocale ) {
        recipientAddress = requireNonNull( aRecipientAddress );
        locale = requireNonNull( aLocale );
    }
}
