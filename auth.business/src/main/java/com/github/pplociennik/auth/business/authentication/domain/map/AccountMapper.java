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

import com.github.pplociennik.auth.business.authentication.domain.model.AccountSecurityCoreDO;
import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.common.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

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

    public static Account mapToEntity( @NonNull RegistrationDO aRegistrationDO, @NonNull String aHashedPassword ) {
        requireNonNull( aRegistrationDO );
        requireNonEmpty( aHashedPassword );
        var newAccount = Account.builder()
                .accountNonExpired( true )
                .accountNonLocked( true )
                .credentialsNonExpired( true )
                .enabled( true )
                .emailAddress( aRegistrationDO.getEmail() )
                .password( aHashedPassword )
                .username( aRegistrationDO.getUsername() )
                .build();

        newAccount.setAuthorities( createNewUserAuthority( newAccount ) );

        return newAccount;
    }

    private static List< AuthorityDetails > getAuthorities( Collection< Authority > aAuthorities ) {
        requireNonNull( aAuthorities );
        return aAuthorities
                .stream()
                .map( authority -> new AuthorityDetails( authority.getName() ) )
                .collect( toUnmodifiableList() );
    }

    private static Set< Authority > createNewUserAuthority( Account aAccount ) {
        return Set.of( Authority.builder()
                .name( AUTH_USER_ROLE.getName() )
                .authoritiesOwner( aAccount )
                .build() );
    }
}
