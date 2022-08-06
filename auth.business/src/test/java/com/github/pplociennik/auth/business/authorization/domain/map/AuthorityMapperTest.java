package com.github.pplociennik.auth.business.authorization.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.pplociennik.auth.business.authentication.data.AccountMapperTestDataSupplier.prepareSimpleAccountData;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link AuthorityMapper}.
 *
 * @author Created by: Pplociennik at 06.08.2022 21:31
 */
class AuthorityMapperTest {

    @Test
    void shouldReturnValidAuthorityDO_whenAuthorityValid() {

        // GIVEN
        var authorityName = AUTH_USER_ROLE.getName();
        var account = prepareSimpleAccountData();
        var authority = Authority.builder()
                .name( authorityName )
                .authoritiesOwner( account )
                .build();

        // WHEN
        var authorityDO = AuthorityMapper.mapToDomain( authority );

        // THEN
        var expectedAuthorityDO = AuthorityDO.builder()
                .authorityName( authorityName )
                .owner( AccountMapper.mapToDomain( account ) )
                .build();
        assertThat( authorityDO ).usingRecursiveComparison().isEqualTo( expectedAuthorityDO );
    }

    @Test
    void shouldReturnValidAuthorityDO_whenAuthorityNameAndOwnerValid() {

        // GIVEN
        var authorityName = AUTH_USER_ROLE.getName();
        var authorityOwner = AccountMapper.mapToDomain( prepareSimpleAccountData() );

        // WHEN
        var authorityDO = AuthorityMapper.mapToDomain( authorityName, authorityOwner );

        // THEN
        var expectedAuthorityDO = AuthorityDO.builder()
                .authorityName( authorityName )
                .owner( authorityOwner )
                .build();
        assertThat( authorityDO ).usingRecursiveComparison().isEqualTo( expectedAuthorityDO );
    }

    @Test
    void shouldReturnValidAuthorityDO_whenAuthoritiesNamesAndOwnerValid() {

        // GIVEN
        var authoritiesNames = Set.of( AUTH_USER_ROLE.getName() );
        var authorityOwner = AccountMapper.mapToDomain( prepareSimpleAccountData() );

        // WHEN
        var authorityDO = AuthorityMapper.mapToDomain( authoritiesNames, authorityOwner );

        // THEN
        var expectedAuthorityDO = AuthorityDO.builder()
                .authorityName( AUTH_USER_ROLE.getName() )
                .owner( authorityOwner )
                .build();
        var expectedDOs = Set.of( expectedAuthorityDO );
        assertThat( authorityDO ).usingRecursiveComparison().isEqualTo( expectedDOs );
    }
}