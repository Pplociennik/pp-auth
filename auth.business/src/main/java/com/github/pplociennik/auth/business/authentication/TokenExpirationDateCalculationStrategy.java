package com.github.pplociennik.auth.business.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * A strategy for calculating an expiration date for the verification token.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:47
 */
@Getter
@AllArgsConstructor
enum TokenExpirationDateCalculationStrategy {

    /**
     * Account confirmation request token.
     */
    ACCOUNT_CONFIRMATION_REQUEST( 15, ChronoUnit.MINUTES ) {
        @Override
        ZonedDateTime calculate( ZonedDateTime aStartDateTime ) {
            return aStartDateTime.plus( getAmount(), getUnit() );
        }
    };

    private final long amount;
    private final TemporalUnit unit;

    abstract ZonedDateTime calculate( @NonNull ZonedDateTime aStartDateTime );
}
