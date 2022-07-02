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

import com.github.pplociennik.auth.common.auth.dto.RegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.pplociennik.auth.business.authentication.domain.map.RegistrationMapper.mapToDO;
import static com.github.pplociennik.auth.business.authentication.domain.map.RegistrationMapperTestDataSupplier.prepareSimpleRegistrationDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link RegistrationMapper}.
 *
 * @author Created by: Pplociennik at 23.04.2022 12:50
 */
class RegistrationMapperTest {

    private RegistrationDto TEST_REGISTRATION_DTO;

    @BeforeEach
    void prepareTestData() {
        TEST_REGISTRATION_DTO = prepareSimpleRegistrationDto();
    }

    @Test
    void shouldConvertProperlyToRegistrationDO_whenValidRegistrationDtoProvided() {
        var result = mapToDO( TEST_REGISTRATION_DTO );

        assertThat( result.getEmail() ).isEqualTo( TEST_REGISTRATION_DTO.getEmail() );
        assertThat( result.getUsername() ).isEqualTo( TEST_REGISTRATION_DTO.getUsername() );
        assertThat( result.getPassword() ).isEqualTo( TEST_REGISTRATION_DTO.getPassword() );
        assertThat( result.getRepeatedPassword() ).isEqualTo( TEST_REGISTRATION_DTO.getRepeatedPassword() );
    }

    @Test
    void shouldThrowNullPointerException_whenRegistrationDtoIsNull() {
        assertThatThrownBy( () -> mapToDO( null ) ).isInstanceOf( NullPointerException.class );
    }
}