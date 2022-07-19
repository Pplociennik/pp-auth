package com.github.pplociennik.auth.business.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * A strategy for calculating an expiration date for the verification token.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:47
 */
@Getter
@AllArgsConstructor
enum TokenExpirationDateAmount {

    /**
     * Account confirmation request token.
     */
    ACCOUNT_CONFIRMATION_REQUEST( 15, ChronoUnit.MINUTES );

    private final long amount;
    private final TemporalUnit unit;
}
