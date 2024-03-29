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

package com.github.pplociennik.auth.business.authorization.ports;

import org.springframework.lang.NonNull;
import org.springframework.security.acls.model.Permission;

/**
 * A service managing users'/roles' permissions to the specified business objects. A component of Access Control List
 * (ACL) system.
 *
 * @author Created by: Pplociennik at 12.04.2022 21:26
 */
public interface PermissionsService {

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
    void addPermission(
            @NonNull String aUsername, @NonNull Class< ? > aType, Long aId, @NonNull Permission aPermission );

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
    void addPermission( @NonNull String aUsername, @NonNull Object aObject, @NonNull Permission aPermission );
}
