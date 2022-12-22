package com.github.pplociennik.auth.business.authentication.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.model.VerificationTokenDO;
import com.github.pplociennik.auth.db.entity.authentication.VerificationToken;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static auth.AuthVerificationTokenType.EMAIL_CONFIRMATION_TOKEN;
import static com.github.pplociennik.auth.business.authentication.data.AccountMapperTestDataSupplier.prepareSimpleAccountData;
import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToDomain;
import static com.github.pplociennik.auth.business.authentication.domain.map.VerificationTokenMapper.mapToEntity;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link VerificationTokenMapper}.
 *
 * @author Created by: Pplociennik at 07.08.2022 00:55
 */
class VerificationTokenMapperTest {

    private static final ZoneId TEST_ZONE_ID = ZoneId.of( "Europe/Warsaw" );
    private static final ZonedDateTime TEST_ZONED_DATE_TIME = Instant
            .now()
            .plus( 15, MINUTES )
            .atZone( TEST_ZONE_ID );

    @Test
    void shouldReturnValidVerificationTokenDO_whenVerificationTokenValid() {

        // GIVEN
        var token = "dummyToken";
        var account = prepareSimpleAccountData();
        var type = EMAIL_CONFIRMATION_TOKEN;
        var expirationDate = TEST_ZONED_DATE_TIME;
        var active = true;
        var verificationToken = new VerificationToken();
        verificationToken.setToken( token );
        verificationToken.setOwner( account );
        verificationToken.setType( type );
        verificationToken.setExpirationDate( expirationDate );
        verificationToken.setActive( active );

        // WHEN
        var resultDO = mapToDomain( verificationToken );

        // THEN
        var accountDO = AccountMapper.mapToDomain( account );
        var expectedDO = VerificationTokenDO
                .builder()
                .token( token )
                .owner( accountDO )
                .type( type )
                .expirationDate( expirationDate.toInstant() )
                .zoneId( TEST_ZONE_ID )
                .isActive( true )
                .build();
        assertThat( resultDO )
                .usingRecursiveComparison()
                .isEqualTo( expectedDO );
    }

    @Test
    void shouldReturnValidVerificationToken_whenDOAndOwnerValid() {

        // GIVEN
        var token = "dummyToken";
        var account = prepareSimpleAccountData();
        var type = EMAIL_CONFIRMATION_TOKEN;
        var expirationDate = TEST_ZONED_DATE_TIME;
        var active = true;
        var accountDO = AccountMapper.mapToDomain( account );
        var tokenDO = VerificationTokenDO
                .builder()
                .token( token )
                .owner( accountDO )
                .type( type )
                .expirationDate( expirationDate.toInstant() )
                .zoneId( TEST_ZONE_ID )
                .isActive( true )
                .build();

        // WHEN
        var result = mapToEntity( tokenDO, account );

        // THEN
        var expectedToken = new VerificationToken();
        expectedToken.setToken( token );
        expectedToken.setOwner( account );
        expectedToken.setType( type );
        expectedToken.setExpirationDate( expirationDate );
        expectedToken.setActive( active );
        assertThat( result )
                .usingRecursiveComparison()
                .isEqualTo( expectedToken );
    }
}