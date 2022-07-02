package com.github.pplociennik.auth.business.mailing.domain.model;

import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A domain object holding all the information necessary for email confirmation request message creation and sending.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:31
 */
public final class EmailConfirmationDataDO extends EmailDataDO implements AddressableDataDO {

    private final String confirmationLink;

    public EmailConfirmationDataDO( @NonNull String aRecipientAddress, @NonNull String aConfirmationLink ) {
        super( aRecipientAddress );
        confirmationLink = requireNonNull( aConfirmationLink );
    }

    @Override
    public String getRecipientAddress() {
        return recipientAddress;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }
}
