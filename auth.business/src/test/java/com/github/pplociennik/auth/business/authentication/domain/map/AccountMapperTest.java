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
import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.pplociennik.auth.business.authentication.data.AccountMapperTestDataSupplier.prepareSimpleAccountData;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToEntity;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToSecurityCoreDO;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Unit tests for {@link AccountMapper}.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:20
 */
class AccountMapperTest {

    @Test
    void shouldReturnValidSecurityCoreDO_whenValidAccountDataGiven() {

        // GIVEN
        var testAccount = prepareSimpleAccountData();
        var securityCoreDO = mapToSecurityCoreDO( testAccount );

        // WHEN
        // THEN
        assertThat( securityCoreDO.getUsername() ).isEqualTo( testAccount.getUsername() );
        assertThat( securityCoreDO.getPassword() ).isEqualTo( testAccount.getPassword() );
        assertThat( securityCoreDO.isAccountNonExpired() ).isEqualTo( testAccount.isAccountNonExpired() );
        assertThat( securityCoreDO.isAccountNonLocked() ).isEqualTo( testAccount.isAccountNonLocked() );
        assertThat( securityCoreDO.isEnabled() ).isEqualTo( testAccount.isEnabled() );
        assertThat( securityCoreDO.isCredentialsNonExpired() ).isEqualTo( testAccount.isCredentialsNonExpired() );
        assertThat( securityCoreDO.getAuthorities() ).anyMatch( authority -> {
            return testAccount
                    .getAuthorities()
                    .stream()
                    .anyMatch( a -> a
                            .getName()
                            .equals( authority.getAuthority() ) );
        } );
    }

    @Test
    void shouldReturnValidAccountEntity_whenAccountDOValid() {

        // GIVEN
        var username = "TEST_USERNAME";
        var email = "test@email.com";
        var enabled = true;
        var nonLocked = true;
        var nonExpired = true;
        var credsNonExpired = true;
        var authoritiesDOs = prepareTestAuthoritiesDOs();
        var accountDO = AccountDO
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authoritiesDOs )
                .build();
        authoritiesDOs.forEach( aAuthorityDO -> aAuthorityDO.setOwner( accountDO ) );

        // WHEN
        var result = AccountMapper.mapToEntity( accountDO );

        // THEN
        var authorities = prepareTestAuthorities();
        var expectedAccount = new Account();
        expectedAccount.setUsername( username );
        expectedAccount.setEmailAddress( email );
        expectedAccount.setAccountNonLocked( nonLocked );
        expectedAccount.setAccountNonExpired( nonExpired );
        expectedAccount.setCredentialsNonExpired( credsNonExpired );
        expectedAccount.setEnabled( enabled );
        expectedAccount.setAuthorities( authorities );
        authorities.forEach( aAuthority -> aAuthority.setAuthoritiesOwner( expectedAccount ) );
        assertThat( result )
                .usingRecursiveComparison()
                .isEqualTo( expectedAccount );
    }

    @Test
    void shouldReturnValidAccountDO_whenAccountValid() {

        // GIVEN
        var username = "TEST_USERNAME";
        var email = "test@email.com";
        var enabled = true;
        var nonLocked = true;
        var nonExpired = true;
        var credsNonExpired = true;
        var authorities = prepareTestAuthorities();
        var account = new Account();
        account.setUsername( username );
        account.setEmailAddress( email );
        account.setAccountNonLocked( nonLocked );
        account.setAccountNonExpired( nonExpired );
        account.setCredentialsNonExpired( credsNonExpired );
        account.setEnabled( enabled );
        account.setAuthorities( authorities );
        authorities.forEach( aAuthority -> aAuthority.setAuthoritiesOwner( account ) );

        // WHEN
        var result = AccountMapper.mapToDomain( account );

        // THEN
        var authoritiesDOs = prepareTestAuthoritiesDOs();
        var expectedAccountDO = AccountDO
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authoritiesDOs )
                .build();
        authoritiesDOs.forEach( aAuthorityDO -> aAuthorityDO.setOwner( expectedAccountDO ) );
        assertThat( result )
                .usingRecursiveComparison()
                .isEqualTo( expectedAccountDO );
    }

    @Test
    void shouldReturnValidAccountDO_whenAccountDtoValid() {

        // GIVEN
        var username = "TEST_USERNAME";
        var email = "test@email.com";
        var enabled = true;
        var nonLocked = true;
        var nonExpired = true;
        var credsNonExpired = true;
        var authorities = prepareTestAuthoritiesDto();
        var accountDto = AccountDto
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authorities )
                .build();

        // WHEN
        var result = AccountMapper.mapToDomain( accountDto );

        // THEN
        var authoritiesDOs = prepareTestAuthoritiesDOs();
        var expectedAccountDO = AccountDO
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authoritiesDOs )
                .build();
        authoritiesDOs.forEach( aAuthorityDO -> aAuthorityDO.setOwner( expectedAccountDO ) );
        assertThat( result )
                .usingRecursiveComparison()
                .isEqualTo( expectedAccountDO );
    }

    @Test
    void shouldReturnValidAccountDto_whenAccountDOValid() {

        // GIVEN
        var username = "TEST_USERNAME";
        var email = "test@email.com";
        var enabled = true;
        var nonLocked = true;
        var nonExpired = true;
        var credsNonExpired = true;
        var authoritiesDOs = prepareTestAuthoritiesDOs();
        var accountDO = AccountDO
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authoritiesDOs )
                .build();
        authoritiesDOs.forEach( aAuthorityDO -> aAuthorityDO.setOwner( accountDO ) );

        // WHEN
        var result = AccountMapper.mapToDto( accountDO );

        // THEN
        var authorities = prepareTestAuthoritiesDto();
        var expectedAAccountDto = AccountDto
                .builder()
                .username( username )
                .emailAddress( email )
                .accountNonLocked( nonLocked )
                .accountNonExpired( nonExpired )
                .credentialsNonExpired( credsNonExpired )
                .enabled( enabled )
                .authorities( authorities )
                .build();
        assertThat( result )
                .usingRecursiveComparison()
                .isEqualTo( expectedAAccountDto );
    }

    private Set< AuthorityDO > prepareTestAuthoritiesDOs() {
        var baseAuthority = AuthorityDO
                .builder()
                .authorityName( AUTH_USER_ROLE.getName() )
                .build();

        return Set.of( baseAuthority );
    }

    private Set< Authority > prepareTestAuthorities() {
        var baseAuthority = new Authority();
        baseAuthority.setName( AUTH_USER_ROLE.getName() );

        return Set.of( baseAuthority );
    }

    private Set< String > prepareTestAuthoritiesDto() {

        return Set.of( AUTH_USER_ROLE.getName() );
    }

}