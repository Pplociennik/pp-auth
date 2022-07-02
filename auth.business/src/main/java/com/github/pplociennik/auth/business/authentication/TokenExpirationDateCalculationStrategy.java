package com.github.pplociennik.auth.business.authentication;

import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;

/**
 * A strategy for calculating an expiration date for the verification token.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:47
 */
enum TokenExpirationDateCalculationStrategy {

    /**
     * Account confirmation request token.
     */
    ACCOUNT_CONFIRMATION_REQUEST {
        @Override
        ZonedDateTime calculate( ZonedDateTime aStartDateTime ) {
            return aStartDateTime.plusMinutes( 15L );
        }
    };

    abstract ZonedDateTime calculate( @NonNull ZonedDateTime aStartDateTime );
}
