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

import com.github.pplociennik.auth.business.authorization.domain.model.AuthorityDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * A class being the implementation of the {@link UserDetails} interface being responsible for managing the users' information.
 *
 * @author Created by: Pplociennik at 15.10.2021 20:10
 */
@AllArgsConstructor
@Data
public class AccountSecurityCoreDO implements UserDetails {

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
     * Account's unique username.
     */
    private String username;
    /**
     * Password.
     */
    private String password;
    /**
     * The property determines if account is enabled, or it isn't.
     */
    private boolean enabled;
    /**
     * Account's authorities.
     */
    private Collection< AuthorityDetails > authorities;


    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
