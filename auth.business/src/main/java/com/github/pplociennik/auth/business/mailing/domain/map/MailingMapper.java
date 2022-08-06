package com.github.pplociennik.auth.business.mailing.domain.map;

import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.WelcomeEmailDataDO;
import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;
import com.github.pplociennik.auth.common.mailing.dto.WelcomeEmailDataDto;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * A mapper for mailing data objects.
 *
 * @author Created by: Pplociennik at 30.06.2022 21:30
 */
public class MailingMapper {

    public static EmailConfirmationDataDO mapToDO( @NonNull EmailConfirmationDataDto aDto ) {
        Objects.requireNonNull( aDto );

        var recipientAddress = aDto.getRecipientAddress();
        var confirmationLink = aDto.getConfirmationLink();
        var locale = aDto.getLocale();

        return new EmailConfirmationDataDO( recipientAddress, confirmationLink, locale );
    }

    public static WelcomeEmailDataDO mapToDO( @NonNull WelcomeEmailDataDto aDto ) {
        Objects.requireNonNull( aDto );

        var recipientAddress = aDto.getRecipientAddress();
        var username = aDto.getUsername();
        var locale = aDto.getLocale();

        return new WelcomeEmailDataDO( recipientAddress, locale, username );
    }
}
