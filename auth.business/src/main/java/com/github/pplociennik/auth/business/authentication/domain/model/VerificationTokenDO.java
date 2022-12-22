package com.github.pplociennik.auth.business.authentication.domain.model;

import auth.AuthVerificationTokenType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;

/**
 * A Domain Object being a representation of the verification token.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:05
 */
@Data
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class VerificationTokenDO {

    private long id;
    private String uniqueObjectIdentifier;
    private AuthVerificationTokenType type;
    private AccountDO owner;
    private String token;
    private Instant expirationDate;
    private ZoneId zoneId;
    private boolean isActive;

    public VerificationTokenDO(
            long aId, String aUniqueObjectIdentifier, AuthVerificationTokenType aType, AccountDO aOwner, String aToken,
            Instant aExpirationDate, boolean aIsActive ) {
        this( aId, aUniqueObjectIdentifier, aType, aOwner, aToken, aExpirationDate, ZoneId.systemDefault(), aIsActive );
    }

    public VerificationTokenDO(
            long aId, String aUniqueObjectIdentifier, AuthVerificationTokenType aType, AccountDO aOwner, String aToken,
            Instant aExpirationDate, ZoneId aZoneId, boolean aIsActive ) {
        id = aId;
        uniqueObjectIdentifier = aUniqueObjectIdentifier;
        type = aType;
        owner = aOwner;
        token = aToken;
        expirationDate = aExpirationDate;
        zoneId = aZoneId;
        isActive = aIsActive;
    }
}
