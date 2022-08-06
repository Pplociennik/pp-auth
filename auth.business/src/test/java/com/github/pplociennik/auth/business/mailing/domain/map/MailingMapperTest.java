package com.github.pplociennik.auth.business.mailing.domain.map;

import com.github.pplociennik.auth.business.mailing.domain.model.EmailConfirmationDataDO;
import com.github.pplociennik.auth.business.mailing.domain.model.WelcomeEmailDataDO;
import com.github.pplociennik.auth.common.mailing.dto.EmailConfirmationDataDto;
import com.github.pplociennik.auth.common.mailing.dto.WelcomeEmailDataDto;
import org.junit.jupiter.api.Test;

import static com.github.pplociennik.auth.business.mailing.domain.map.MailingMapper.mapToDO;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MailingMapper}.
 *
 * @author Created by: Pplociennik at 06.08.2022 23:19
 */
class MailingMapperTest {

    @Test
    void shouldReturnValidEmailConfirmationDataDO_whenValidDtoGiven() {

        // GIVEN
        var recipientAddress = "testRecipientAddress@testEmail.com";
        var confirmationLink = "http://test-site-address.com/confirmationToken";
        var locale = ENGLISH;
        var dto = new EmailConfirmationDataDto( recipientAddress, confirmationLink, locale );

        // WHEN
        var resultDO = mapToDO( dto );

        // THEN
        var expectedDO = new EmailConfirmationDataDO( recipientAddress, confirmationLink, locale );
        assertThat( resultDO ).usingRecursiveComparison().isEqualTo( expectedDO );
    }

    @Test
    void shouldReturnValidWelcomeEmailDataDO_whenValidDtoGiven() {

        // GIVEN
        var recipientAddress = "testRecipientAddress@testEmail.com";
        var username = "TEST_USERNAME";
        var locale = ENGLISH;
        var dto = new WelcomeEmailDataDto( recipientAddress, locale, username );

        // WHEN
        var resultDO = mapToDO( dto );

        // THEN
        var expectedDO = new WelcomeEmailDataDO( recipientAddress, locale, username );
        assertThat( resultDO ).usingRecursiveComparison().isEqualTo( expectedDO );
    }

}