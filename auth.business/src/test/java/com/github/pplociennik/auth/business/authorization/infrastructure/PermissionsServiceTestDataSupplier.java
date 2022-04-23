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

import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;

/**
 * A data supplier for {@link PermissionsServiceTest}.
 *
 * @author Created by: Pplociennik at 23.04.2022 13:37
 */
class PermissionsServiceTestDataSupplier {

    public static final Class TEST_OBJECT_TYPE = TestTypedClass.class;
    public static final Long TEST_EXISTING_OBJECT_ID = 1L;
    public static final String TEST_NON_EXISTING_OBJECT_ID = "999";


    static ObjectIdentity prepareTestExistingObjectIdentity() {
        return new ObjectIdentityImpl( TEST_OBJECT_TYPE, TEST_EXISTING_OBJECT_ID );
    }

    static ObjectIdentity prepareTestNonExistingObjectIdentity() {
        return new ObjectIdentityImpl( TEST_OBJECT_TYPE, TEST_NON_EXISTING_OBJECT_ID );
    }

    static Acl prepareTestExistingAcl() {
        var grantedAuthority = new AuthorityDetails( "USER" );
        var authorizationStrategy = new AclAuthorizationStrategyImpl( grantedAuthority );

        return new AclImpl( prepareTestExistingObjectIdentity(), TEST_EXISTING_OBJECT_ID, authorizationStrategy, new ConsoleAuditLogger() );
    }

    static Acl prepareTestNonExistingAcl() {
        var grantedAuthority = new AuthorityDetails( "USER" );
        var authorizationStrategy = new AclAuthorizationStrategyImpl( grantedAuthority );

        return new AclImpl( prepareTestExistingObjectIdentity(), TEST_NON_EXISTING_OBJECT_ID, authorizationStrategy, new ConsoleAuditLogger() );
    }
}
