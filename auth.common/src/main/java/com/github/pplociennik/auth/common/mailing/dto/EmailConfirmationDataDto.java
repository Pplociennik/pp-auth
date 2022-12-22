package com.github.pplociennik.auth.common.mailing.dto;

import org.springframework.lang.NonNull;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A data transfer object containing information necessary for creating and sending emails with address confirmation
 * request.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:15
 */
public final class EmailConfirmationDataDto extends EmailDataDto implements AddressableDataDto {

    private final String confirmationLink;

    public EmailConfirmationDataDto(
            @NonNull String aRecipientAddress, @NonNull String aConfirmationLink, Locale aLocale ) {
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
