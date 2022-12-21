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

package com.github.pplociennik.auth.db.entity.authentication;

import com.github.pplociennik.auth.db.entity.authorization.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * An Entity class being the representation of user's account. Stores the data being used during the authorization process.
 *
 * @author Created by: Pplociennik at 17.09.2021 20:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table( name = "AUTH_ACCOUNTS" )
public class Account {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "AUTH_ACCOUNT_GEN" )
    @SequenceGenerator( name = "AUTH_ACCOUNT_GEN", sequenceName = "SEQ_AUTH_ACCOUNT", allocationSize = 1 )
    @Column( name = "ID", nullable = false )
    private long id;

    /**
     * An email address.
     */
    @Column( name = "EMAIL", nullable = false, unique = true )
    private String emailAddress;

    /**
     * Account's unique username.
     */
    @Column( name = "USERNAME", nullable = false, unique = true )
    private String username;

    /**
     * Password.
     */
    @Column( name = "PASSWORD", nullable = false )
    private String password;

    /**
     * Account's creation date.
     */
    @Column( name = "CREATION_DATE", nullable = false, updatable = false )
    private ZonedDateTime creationDate;

    /**
     * Account's last login date.
     */
    @Column( name = "LAST_LOGIN_DATE" )
    private ZonedDateTime lastLoginDate;

    /**
     * The property determines if account is enabled, or it isn't.
     */
    @Column( name = "ENABLED", nullable = false )
    private boolean enabled;

    /**
     * The property determines if account is expired, or it isn't.
     */
    @Column( name = "ACC_NON_EXPIRED", nullable = false )
    private boolean accountNonExpired;

    /**
     * The property determines if credentials are expired, or they're not.
     */
    @Column( name = "CREDS_NON_EXPIRED", nullable = false )
    private boolean credentialsNonExpired;

    /**
     * The property determines if account is locked, or it isn't.
     */
    @Column( name = "ACC_NON_LOCKED", nullable = false )
    private boolean accountNonLocked;

    /**
     * Account's authorities.
     */
    @Column( name = "AUTHORITIES" )
    @OneToMany( mappedBy = "authoritiesOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    private Set< Authority > authorities;

    @Override
    public int hashCode() {
        return Objects.hash( id, emailAddress, username );
    }

    @Override
    public boolean equals( final Object aO ) {
        if ( this == aO ) {
            return true;
        }
        if ( aO == null || getClass() != aO.getClass() ) {
            return false;
        }
        Account account = ( Account ) aO;
        return id == account.id && emailAddress.equals( account.emailAddress ) && username.equals( account.username );
    }
}
