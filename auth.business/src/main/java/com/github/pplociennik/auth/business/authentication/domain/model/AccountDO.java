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

package com.github.pplociennik.auth.business.authentication.domain.model;

import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDO;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A Domain Object responsible for holding information being used during the authentication process.
 *
 * @author Created by: Pplociennik at 16.09.2021 17:29
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDO {

    /**
     * A object's database identifier.
     */
    private long id;

    /**
     * An unique object identifier.
     */
    private String uniqueObjectIdentifier;

    /**
     * An email address.
     */
    private String emailAddress;

    /**
     * Account's unique username.
     */
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * Account's creation date.
     */
    private ZonedDateTime creationDate;

    /**
     * Account's last modification date.
     */
    private ZonedDateTime lastModificationDate;

    /**
     * Account's last login date.
     */
    private ZonedDateTime lastLoginDate;

    /**
     * The property determines if account is enabled, or it isn't.
     */
    private boolean enabled;

    /**
     * The property determines if account is expired, or it isn't.
     */
    private boolean accountNonExpired;

    /**
     * The property determines if credentials are expired, or they're not.
     */
    private boolean credentialsNonExpired;

    /**
     * The property determines if account is locked, or it isn't.
     */
    private boolean accountNonLocked;

    /**
     * Account's authorities.
     */
    private Set< AuthorityDO > authorities;

    @Override
    public int hashCode() {
        return Objects.hash( emailAddress, username );
    }

    @Override
    public boolean equals( Object aO ) {
        if ( this == aO ) {
            return true;
        }
        if ( aO == null || getClass() != aO.getClass() ) {
            return false;
        }
        AccountDO accountDO = ( AccountDO ) aO;
        return emailAddress.equals( accountDO.emailAddress ) && username.equals( accountDO.username );
    }
}
