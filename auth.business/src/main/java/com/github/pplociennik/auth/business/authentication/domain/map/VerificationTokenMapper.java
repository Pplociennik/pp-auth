package com.github.pplociennik.auth.business.authentication.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.requireNonNull;

/**
 * A mapper for {@link VerificationToken} class.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:16
 */
public class VerificationTokenMapper {

    public static VerificationTokenDO mapToDomain( @NonNull VerificationToken aVerificationToken ) {
        requireNonNull( aVerificationToken );

        return VerificationTokenDO.builder()
                .token( aVerificationToken.getToken() )
                .id( aVerificationToken.getId() )
                .type( aVerificationToken.getType() )
                .expirationDate( getInstant( aVerificationToken.getExpirationDate() ) )
                .owner( AccountMapper.mapToDomain( aVerificationToken.getOwner() ) )
                .build();
    }

    public static VerificationToken mapToEntity( @NonNull VerificationTokenDO aVerificationToken, Account aOwner ) {
        requireNonNull( aVerificationToken );

        return VerificationToken.builder()
                .token( aVerificationToken.getToken() )
                .expirationDate( getZonedExpirationDate( aVerificationToken.getExpirationDate() ) )
                .owner( aOwner )
                .type( aVerificationToken.getType() )
                .build();
    }

    private static ZonedDateTime getZonedExpirationDate( Instant aExpirationDate ) {
        var zoneId = ZoneId.systemDefault();
        return aExpirationDate.atZone( zoneId );
    }

    private static Instant getInstant( ZonedDateTime aExpirationDate ) {
        return aExpirationDate.toInstant();
    }
}
