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

package com.github.pplociennik.auth.business.authorization.infrastructure;

import com.github.pplociennik.auth.business.authorization.ports.PermissionsService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;

import static com.github.pplociennik.auth.business.authorization.infrastructure.PermissionsServiceTestDataSupplier.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PermissionsService}.
 *
 * @author Created by: Pplociennik at 23.04.2022 13:23
 */
class PermissionsServiceTest {

    private static ObjectIdentity TEST_EXISTING_ACL_ID;
    private static ObjectIdentity TEST_VALID_NON_EXISTING_ACL_ID;
    private static final String TEST_VALID_USERNAME = "TestUsername";
    private static final TestTypedClass TEST_TYPED_CLASS_OBJECT = new TestTypedClass();

    private MutableAclService mutableAclService;
    private MutableAcl returnedAcl;

    private PermissionsService underTest;


    @BeforeEach
    void prepareMocksAndTestData() {

        TEST_EXISTING_ACL_ID = prepareTestExistingObjectIdentity();
        TEST_VALID_NON_EXISTING_ACL_ID = prepareTestNonExistingObjectIdentity();

        mockMutableAclService();
        underTest = new PermissionServiceImpl( mutableAclService );
    }

    private void mockMutableAclService() {

        mutableAclService = mock( MutableAclService.class );
        returnedAcl = mock( MutableAcl.class );

        when( mutableAclService.readAclById( TEST_EXISTING_ACL_ID ) ).thenReturn( returnedAcl );
        when( mutableAclService.readAclById( TEST_VALID_NON_EXISTING_ACL_ID ) ).thenReturn( null );

        doNothing().when( returnedAcl ).insertAce( anyInt(), any(), any(), anyBoolean() );
    }

    @Test
    void shouldGrantReadPermission_whenDataValid() {
        underTest.addPermission( TEST_VALID_USERNAME, TEST_OBJECT_TYPE, TEST_EXISTING_OBJECT_ID, BasePermission.READ );
    }

    @Test
    void shouldGrantReadPermission_whenObjectValid() {
        underTest.addPermission( TEST_VALID_USERNAME, TEST_TYPED_CLASS_OBJECT, BasePermission.READ );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsNullAndTypedDetailsGiven() {
        assertThatThrownBy( () -> underTest.addPermission( null, TEST_OBJECT_TYPE, TEST_EXISTING_OBJECT_ID, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenObjectTypeIsNullAndTypedDetailsGiven() {
        assertThatThrownBy( () -> underTest.addPermission( TEST_VALID_USERNAME, null, TEST_EXISTING_OBJECT_ID, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenObjectIdIsNullAndTypedDetailsGiven() {
        assertThatThrownBy( () -> underTest.addPermission( TEST_VALID_USERNAME, TEST_OBJECT_TYPE, null, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenPermissionIsNullAndTypedDetailsGiven() {
        assertThatThrownBy( () -> underTest.addPermission( TEST_VALID_USERNAME, TEST_OBJECT_TYPE, TEST_EXISTING_OBJECT_ID, null ) );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsNull() {
        assertThatThrownBy( () -> underTest.addPermission( null, TEST_TYPED_CLASS_OBJECT, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenObjectIsNull() {
        assertThatThrownBy( () -> underTest.addPermission( TEST_VALID_USERNAME, null, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenPermissionIsNull() {
        assertThatThrownBy( () -> underTest.addPermission( TEST_VALID_USERNAME, TEST_TYPED_CLASS_OBJECT, null ) );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsEmpty() {
        assertThatThrownBy( () -> underTest.addPermission( StringUtils.EMPTY, TEST_TYPED_CLASS_OBJECT, BasePermission.READ ) );
    }

    @Test
    void shouldThrowNullPointerException_whenUsernameIsEmptyAndTypedDetailsGiven() {
        assertThatThrownBy( () -> underTest.addPermission( StringUtils.EMPTY, TEST_OBJECT_TYPE, TEST_EXISTING_OBJECT_ID, BasePermission.READ ) );
    }
}