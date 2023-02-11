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

import auth.dto.AccountDto;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.domain.model.AccountSecurityCoreDO;
import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.github.pplociennik.auth.business.shared.system.ObjectsSpecifierDefinition.authorityTypeSpecifier;
import static com.github.pplociennik.commons.utility.identifier.UniqueIdentifierGenerator.generateIdentifier;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

/**
 * A mapper for {@link Account} class.
 *
 * @author Created by: Pplociennik at 26.10.2021 20:02
 */
public class AccountMapper {

    public static AccountSecurityCoreDO mapToSecurityCoreDO(
            Account aAccount ) {
        if ( aAccount == null ) {
            return null;
        }
        return new AccountSecurityCoreDO( aAccount.getUsername(), aAccount.getPassword(), aAccount.isEnabled(),
                                          aAccount.isAccountNonExpired(), aAccount.isCredentialsNonExpired(),
                                          aAccount.isAccountNonLocked(), getAuthorities( aAccount.getAuthorities() ) );
    }

    public static Account mapToEntity( AccountDO aAccountDO ) {

        if ( aAccountDO == null ) {
            return null;
        }
        var authorities = createAuthorities( aAccountDO.getAuthorities() );

        var account = new Account();
        account.setEmailAddress( aAccountDO.getEmailAddress() );
        account.setAccountNonExpired( aAccountDO.isAccountNonExpired() );
        account.setAccountNonLocked( aAccountDO.isAccountNonLocked() );
        account.setPassword( aAccountDO.getPassword() );
        account.setUsername( aAccountDO.getUsername() );
        account.setCredentialsNonExpired( aAccountDO.isCredentialsNonExpired() );
        account.setAuthorities( authorities );
        account.setEnabled( aAccountDO.isEnabled() );

        account.setUniqueObjectIdentifier( aAccountDO.getUniqueObjectIdentifier() );
        account.setCreationDate( aAccountDO.getCreationDate() );
        account.setLastModification( aAccountDO.getLastModificationDate() );

        updateAuthoritiesSetAccount( authorities, account );

        return account;
    }

    public static AccountDO mapToDomain( Account aAccount ) {

        if ( aAccount == null ) {
            return null;
        }
        var authorities = mapAuthoritiesToDomain( aAccount.getAuthorities() );

        var accountDO = AccountDO
                .builder()
                .uniqueObjectIdentifier( aAccount.getUniqueObjectIdentifier() )
                .accountNonExpired( aAccount.isAccountNonExpired() )
                .accountNonLocked( aAccount.isAccountNonLocked() )
                .enabled( aAccount.isEnabled() )
                .emailAddress( aAccount.getEmailAddress() )
                .username( aAccount.getUsername() )
                .password( aAccount.getPassword() )
                .credentialsNonExpired( aAccount.isCredentialsNonExpired() )
                .authorities( authorities )
                .creationDate( aAccount.getCreationDate() )
                .lastModificationDate( aAccount.getLastModification() )
                .lastLoginDate( aAccount.getLastLoginDate() )
                .build();

        authorities.forEach( aAuthorityDO -> aAuthorityDO.setOwner( accountDO ) );

        return accountDO;
    }

    private static Set< AuthorityDO > mapAuthoritiesToDomain(
            Set< Authority > aAuthorities ) {
        return aAuthorities
                .stream()
                .map( aAuthority -> AuthorityDO
                        .builder()
                        .authorityName( aAuthority.getName() )
                        .uniqueObjectIdentifier( aAuthority.getUniqueObjectIdentifier() )
                        .build() )
                .collect( toSet() );
    }

    public static AccountDO mapToDomain( AccountDto aDto ) {

        if ( aDto == null ) {
            return null;
        }
        var accountDO = AccountDO
                .builder()
                .accountNonExpired( aDto.isAccountNonExpired() )
                .accountNonLocked( aDto.isAccountNonLocked() )
                .emailAddress( aDto.getEmailAddress() )
                .enabled( aDto.isEnabled() )
                .credentialsNonExpired( aDto.isCredentialsNonExpired() )
                .username( aDto.getUsername() )
                .build();

        accountDO.setAuthorities( AuthorityMapper.mapToDomain( aDto.getAuthorities(), accountDO ) );
        return accountDO;
    }

    public static AccountDto mapToDto( AccountDO aAccountDO ) {

        if ( aAccountDO == null ) {
            return null;
        }
        return AccountDto
                .builder()
                .emailAddress( aAccountDO.getEmailAddress() )
                .accountNonExpired( aAccountDO.isAccountNonExpired() )
                .accountNonLocked( aAccountDO.isAccountNonLocked() )
                .authorities( getAuthoritiesNames( aAccountDO ) )
                .username( aAccountDO.getUsername() )
                .credentialsNonExpired( aAccountDO.isCredentialsNonExpired() )
                .enabled( aAccountDO.isEnabled() )
                .build();
    }

    private static void updateAuthoritiesSetAccount(
            Set< Authority > aAuthorities, Account aAccount ) {
        aAuthorities.forEach( aAuthority -> {
            aAuthority.setAuthoritiesOwner( aAccount );
            var identifier = generateIdentifier( aAuthority, authorityTypeSpecifier() );
            aAuthority.setUniqueObjectIdentifier( identifier );
        } );
    }

    private static Set< Authority > createAuthorities(
            Set< AuthorityDO > aAuthorities ) {
        return aAuthorities
                .stream()
                .map( authority -> {
                    var result = new Authority();
                    result.setName( authority.getAuthorityName() );
                    result.setUniqueObjectIdentifier( authority.getUniqueObjectIdentifier() );

                    return result;
                } )
                .collect( toUnmodifiableSet() );
    }

    private static Set< String > getAuthoritiesNames( AccountDO aAccountDO ) {
        return aAccountDO
                .getAuthorities()
                .stream()
                .map( AuthorityDO::getAuthorityName )
                .collect( toUnmodifiableSet() );
    }

    private static List< AuthorityDetails > getAuthorities(
            Collection< Authority > aAuthorities ) {
        requireNonNull( aAuthorities );
        return aAuthorities
                .stream()
                .map( authority -> new AuthorityDetails( authority.getName() ) )
                .collect( toUnmodifiableList() );
    }

}
