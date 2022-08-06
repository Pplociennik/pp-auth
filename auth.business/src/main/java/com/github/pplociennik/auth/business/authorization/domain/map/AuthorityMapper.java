package com.github.pplociennik.auth.business.authorization.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.db.entity.authorization.Authority;

import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * A mapper for authorities.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:28
 */
public class AuthorityMapper {

    public static AuthorityDO mapToDomain( Authority aAuthority ) {
        return AuthorityDO.builder()
                .authorityName( aAuthority.getName() )
                .owner( AccountMapper.mapToDomain( aAuthority.getAuthoritiesOwner() ) )
                .build();
    }

    public static AuthorityDO mapToDomain( String aAuthorityName, AccountDO aOwner ) {
        return AuthorityDO.builder()
                .authorityName( aAuthorityName )
                .owner( aOwner )
                .build();
    }

    public static Set< AuthorityDO > mapToDomain( Set< String > aAuthoritiesNames, AccountDO aOwner ) {
        return aAuthoritiesNames.stream()
                .map( name -> mapToDomain( name, aOwner ) )
                .collect( toUnmodifiableSet() );
    }
}
