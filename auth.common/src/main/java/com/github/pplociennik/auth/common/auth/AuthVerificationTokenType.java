package com.github.pplociennik.auth.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.TemporalUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * An enum defining a type of the verification token and holding an amount and unit of the specified token's expiration time.
 *
 * @author Created by: Pplociennik at 01.07.2022 13:10
 */
@Getter
@AllArgsConstructor
public enum AuthVerificationTokenType {

    /**
     * Account confirmation request token.
     */
    EMAIL_CONFIRMATION_TOKEN( 15L, MINUTES );

    private final long amount;
    private final TemporalUnit unit;

}
