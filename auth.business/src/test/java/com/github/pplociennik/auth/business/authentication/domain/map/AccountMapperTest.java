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

import com.github.pplociennik.auth.business.authentication.domain.model.RegistrationDO;
import com.github.pplociennik.auth.db.entity.authentication.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToEntity;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapper.mapToSecurityCoreDO;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapperTestDataSupplier.prepareSimpleAccountData;
import static com.github.pplociennik.auth.business.authentication.domain.map.AccountMapperTestDataSupplier.prepareSimpleRegistrationDO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AccountMapper}.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:20
 */
class AccountMapperTest {

    private Account TEST_ACCOUNT;
    private RegistrationDO TEST_REGISTRATION_DO;
    private static final String TEST_HASHED_PASSWORD = "VeryLongTestHashedPasswordForConversion";

    @BeforeEach
    void prepareTestData() {
        TEST_ACCOUNT = prepareSimpleAccountData();
        TEST_REGISTRATION_DO = prepareSimpleRegistrationDO();
    }

    @Test
    void shouldReturnValidAccount_whenValidRegistrationDOAndHashedPasswordGiven() {
        var result = mapToEntity( TEST_REGISTRATION_DO, TEST_HASHED_PASSWORD );

        assertThat( result.getUsername() ).isEqualTo( TEST_ACCOUNT.getUsername() );
        assertThat( result.getPassword() ).isEqualTo( TEST_ACCOUNT.getPassword() );
        assertThat( result.getEmailAddress() ).isEqualTo( TEST_ACCOUNT.getEmailAddress() );
        assertThat( result.getAuthorities() ).anyMatch( authority -> {
            return TEST_ACCOUNT.getAuthorities().stream()
                    .anyMatch( a -> a.getName().equals( authority.getName() ) );
        } );
    }

    @Test
    void shouldReturnValidSecurityCoreDO_whenValidAccountDataGiven() {
        var securityCoreDO = mapToSecurityCoreDO( TEST_ACCOUNT );

        assertThat( securityCoreDO.getUsername() ).isEqualTo( TEST_ACCOUNT.getUsername() );
        assertThat( securityCoreDO.getPassword() ).isEqualTo( TEST_ACCOUNT.getPassword() );
        assertThat( securityCoreDO.isAccountNonExpired() ).isEqualTo( TEST_ACCOUNT.isAccountNonExpired() );
        assertThat( securityCoreDO.isAccountNonLocked() ).isEqualTo( TEST_ACCOUNT.isAccountNonLocked() );
        assertThat( securityCoreDO.isEnabled() ).isEqualTo( TEST_ACCOUNT.isEnabled() );
        assertThat( securityCoreDO.isCredentialsNonExpired() ).isEqualTo( TEST_ACCOUNT.isCredentialsNonExpired() );
        assertThat( securityCoreDO.getAuthorities() ).anyMatch( authority -> {
            return TEST_ACCOUNT.getAuthorities().stream()
                    .anyMatch( a -> a.getName().equals( authority.getAuthority() ) );
        } );
    }

    @Test
    void shouldThrowNullPointerException_whenRegistrationDOIsNull() {
        assertThatThrownBy( () -> mapToEntity( null, TEST_HASHED_PASSWORD ) ).isInstanceOf( NullPointerException.class );
    }

    @Test
    void shouldThrowNullPointerException_whenHashedPasswordIsNull() {
        assertThatThrownBy( () -> mapToEntity( TEST_REGISTRATION_DO, null ) ).isInstanceOf( NullPointerException.class );
    }
}