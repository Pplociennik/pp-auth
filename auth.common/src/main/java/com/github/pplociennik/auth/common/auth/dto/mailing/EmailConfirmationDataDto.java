package com.github.pplociennik.auth.common.auth.dto.mailing;

import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A data transfer object containing information necessary for creating and sending emails with address confirmation request.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:15
 */
public final class EmailConfirmationDataDto extends EmailDataDto implements AddressableDataDto {

    private final String confirmationLink;

    public EmailConfirmationDataDto( @NonNull String aRecipientAddress, @NonNull String aConfirmationLink ) {
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
