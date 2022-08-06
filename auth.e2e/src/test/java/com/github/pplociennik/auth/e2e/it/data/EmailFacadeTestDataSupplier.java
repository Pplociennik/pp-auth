package com.github.pplociennik.auth.e2e.it.data;

import com.github.pplociennik.auth.business.mailing.EmailFacade;
import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;

import java.util.Locale;

/**
 * A data supplier for {@link EmailFacade} tests.
 *
 * @author Created by: Pplociennik at 02.07.2022 17:05
 */
public class EmailFacadeTestDataSupplier {

    public static final String TEST_RECIPIENT_ADDRESS = "test@recipient.com";
    public static final String TEST_CONFIRMATION_LINK = "http://localhost/aToken=dummyToken";

    public static EmailConfirmationDataDto getDummyEmailConfirmationSendingDto() {
        return new EmailConfirmationDataDto( TEST_RECIPIENT_ADDRESS, TEST_CONFIRMATION_LINK, Locale.ENGLISH );
    }
}
