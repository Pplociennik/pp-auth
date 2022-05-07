package com.github.pplociennik.auth.business.authorization.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * A mapper for authorities.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:28
 */
public class AuthorityMapper {

    public static AuthorityDO mapToDomain( @NonNull Authority aAuthority ) {
        requireNonNull( aAuthority );
        return AuthorityDO.builder()
                .authorityName( aAuthority.getName() )
                .owner( AccountMapper.mapToDomain( aAuthority.getAuthoritiesOwner() ) )
                .build();
    }

    public static Authority mapToEntity( @NonNull AuthorityDO aAuthorityDO, @NonNull Account aOwner ) {
        requireNonNull( aAuthorityDO );
        return Authority.builder()
                .name( aAuthorityDO.getAuthorityName() )
                .authoritiesOwner( aOwner )
                .build();
    }
}
