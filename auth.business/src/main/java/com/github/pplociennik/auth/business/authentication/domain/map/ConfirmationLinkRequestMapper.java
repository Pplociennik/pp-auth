package com.github.pplociennik.auth.business.authentication.domain.map;

import auth.dto.ConfirmationLinkRequestDto;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.ConfirmationLinkRequestDO;

/**
 * A mapper for conversion data being used during the confirmation link generation.
 *
 * @author Created by: Pplociennik at 21.06.2023 21:14
 */
public class ConfirmationLinkRequestMapper {

    public static ConfirmationLinkRequestDO mapToDomain( ConfirmationLinkRequestDto aConfirmationLinkRequestDto ) {
        return new ConfirmationLinkRequestDO( aConfirmationLinkRequestDto.getEmailAddress() );
    }

    public static ConfirmationLinkRequestDto mapToConfirmationLinkRequestDto( AccountDO aAccountDO ) {
        var emailAddress = aAccountDO.getEmailAddress();

        return new ConfirmationLinkRequestDto( emailAddress );
    }
}
