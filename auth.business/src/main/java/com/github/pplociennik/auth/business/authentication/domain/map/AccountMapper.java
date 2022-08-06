/*
 * MIT License
 *
 * Copyright (c) 2021 Przemysław Płóciennik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.pplociennik.auth.business.authentication.domain.map;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountSecurityCoreDO;
import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import com.github.pplociennik.auth.common.auth.dto.AccountDto;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * A mapper for {@link Account} class.
 *
 * @author Created by: Pplociennik at 26.10.2021 20:02
 */
public class AccountMapper {

    public static AccountSecurityCoreDO mapToSecurityCoreDO( @NonNull Account aAccount ) {
        requireNonNull( aAccount );
        return new AccountSecurityCoreDO(
                aAccount.isAccountNonExpired(), aAccount.isCredentialsNonExpired(),
                aAccount.isAccountNonLocked(), aAccount.getUsername(),
                aAccount.getPassword(), aAccount.isEnabled(), getAuthorities( aAccount.getAuthorities() ) );
    }

    public static Account mapToEntity( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );

        return Account.builder()
                .emailAddress( aAccountDO.getEmailAddress() )
                .password( aAccountDO.getPassword() )
                .accountNonExpired( aAccountDO.isAccountNonExpired() )
                .accountNonLocked( aAccountDO.isAccountNonLocked() )
                .username( aAccountDO.getUsername() )
                .credentialsNonExpired( aAccountDO.isCredentialsNonExpired() )
                .authorities( createAuthorities( aAccountDO.getAuthorities() ) )
                .enabled( aAccountDO.isEnabled() )
                .build();
    }

    private static Set< Authority > createAuthorities( Set< AuthorityDO > aAuthorities ) {
        return aAuthorities.stream()
                .map( authority -> Authority.builder()
                        .name( authority.getAuthorityName() )
                        .build() )
                .collect( toUnmodifiableSet() );
    }

    public static AccountDO mapToDomain( @NonNull Account aAccount ) {
        requireNonNull( aAccount );

        var authorities = aAccount.getAuthorities().stream()
                .map( AuthorityMapper::mapToDomain )
                .collect( toUnmodifiableSet() );

        return AccountDO.builder()
                .accountNonExpired( aAccount.isAccountNonExpired() )
                .accountNonLocked( aAccount.isAccountNonLocked() )
                .password( aAccount.getPassword() )
                .enabled( aAccount.isEnabled() )
                .emailAddress( aAccount.getEmailAddress() )
                .username( aAccount.getUsername() )
                .credentialsNonExpired( aAccount.isCredentialsNonExpired() )
                .id( aAccount.getId() )
                .authorities( authorities )
                .build();
    }

    public static AccountDO mapToDomain( @NonNull AccountDto aDto ) {
        requireNonNull( aDto );

        var accountDO = AccountDO.builder()
                .accountNonExpired( aDto.isAccountNonExpired() )
                .accountNonLocked( aDto.isAccountNonLocked() )
                .emailAddress( aDto.getEmailAddress() )
                .enabled( aDto.isEnabled() )
                .credentialsNonExpired( aDto.isCredentialsNonExpired() )
                .password( aDto.getPassword() )
                .username( aDto.getUsername() )
                .build();

        accountDO.setAuthorities( AuthorityMapper.mapToDomain( aDto.getAuthorities(), accountDO ) );
        return accountDO;
    }

    public static AccountDto mapToDto( @NonNull AccountDO aAccountDO ) {
        requireNonNull( aAccountDO );

        return AccountDto.builder()
                .emailAddress( aAccountDO.getEmailAddress() )
                .password( aAccountDO.getPassword() )
                .accountNonExpired( aAccountDO.isAccountNonExpired() )
                .accountNonLocked( aAccountDO.isAccountNonLocked() )
                .authorities( getAuthoritiesNames( aAccountDO ) )
                .username( aAccountDO.getUsername() )
                .credentialsNonExpired( aAccountDO.isCredentialsNonExpired() )
                .enabled( aAccountDO.isEnabled() )
                .build();
    }

    private static Set< String > getAuthoritiesNames( AccountDO aAccountDO ) {
        return aAccountDO.getAuthorities().stream()
                .map( authority -> authority.getAuthorityName() )
                .collect( toUnmodifiableSet() );
    }

    private static List< AuthorityDetails > getAuthorities( Collection< Authority > aAuthorities ) {
        requireNonNull( aAuthorities );
        return aAuthorities
                .stream()
                .map( authority -> new AuthorityDetails( authority.getName() ) )
                .collect( toUnmodifiableList() );
    }

}
