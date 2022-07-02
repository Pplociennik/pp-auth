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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;

import static com.github.pplociennik.util.utility.CustomObjects.requireNonEmpty;
import static java.util.Objects.requireNonNull;

/**
 * A service managing users'/roles' permissions to the specified business objects. A component of Access Control List (ACL) system.
 *
 * @author Created by: Pplociennik at 12.04.2022 21:29
 */
class PermissionServiceImpl implements PermissionsService {

    private final MutableAclService aclService;

    @Autowired
    PermissionServiceImpl( @NonNull MutableAclService aAclService ) {
        aclService = aAclService;
    }


    /**
     * Adds specified permission for the user to the specific typed object.
     *
     * @param aUsername
     *         a user for which the permissions will be granted.
     * @param aType
     *         a type of object for which the permissions are being resolved.
     * @param aId
     *         an identifier od the specified object.
     * @param aPermission
     *         a permission which will be granted.
     */
    @Override
    public void addPermission( @NonNull String aUsername, @NonNull Class< ? > aType, @NonNull Long aId, @NonNull Permission aPermission ) {

        requireNonEmpty( aUsername );
        requireNonNull( aType );
        requireNonNull( aId );
        requireNonNull( aPermission );

        var objectIdentity = createObjectIdentity( aType, aId );
        var sid = createSid( aUsername );
        var acl = fetchOrCreateAcl( objectIdentity );

        insertAce( acl, aPermission, sid, true );
        aclService.updateAcl( acl );

    }

    /**
     * Adds specified permission for the user to the specific object.
     *
     * @param aUsername
     *         a user for which the permissions will be granted.
     * @param aObject
     *         an object for which the permission is going to be granted.
     * @param aPermission
     *         a permission which will be granted.
     */
    @Override
    public void addPermission( @NonNull String aUsername, @NonNull Object aObject, @NonNull Permission aPermission ) {

        requireNonEmpty( aUsername );
        requireNonNull( aObject );
        requireNonNull( aPermission );

        var objectIdentity = createObjectIdentity( aObject );
        var sid = createSid( aUsername );
        var acl = fetchOrCreateAcl( objectIdentity );

        insertAce( acl, aPermission, sid, true );
        aclService.updateAcl( acl );

    }

    private Acl insertAce( MutableAcl aAcl, Permission aPermission, PrincipalSid aSid, boolean aGranting ) {

        var acl = aAcl;
        var aclEntries = acl.getEntries();
        acl.insertAce( aclEntries.size(), aPermission, aSid, aGranting );

        return acl;
    }

    private MutableAcl fetchOrCreateAcl( ObjectIdentity aObjectIdentity ) {

        MutableAcl resultAcl;

        try {
            resultAcl = ( MutableAcl ) aclService.readAclById( aObjectIdentity );
        } catch ( NotFoundException aException ) {
            resultAcl = aclService.createAcl( aObjectIdentity );
        }

        return resultAcl;
    }

    private PrincipalSid createSid( String aUsername ) {
        return new PrincipalSid( aUsername );
    }

    private ObjectIdentity createObjectIdentity( Object aObject ) {
        return new ObjectIdentityImpl( aObject );
    }

    private ObjectIdentity createObjectIdentity( Class< ? > aType, Long aId ) {
        return new ObjectIdentityImpl( aType, aId );
    }
}
