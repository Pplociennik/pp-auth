package com.github.pplociennik.auth.common.auth.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * A Data Transfer Object representing data of the account object.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:17
 */
@Getter
@Builder
@EqualsAndHashCode
public class AccountDto implements Serializable {

    /**
     * The property determines if account is expired, or it isn't.
     */
    boolean accountNonExpired;
    /**
     * The property determines if credentials are expired, or they're not.
     */
    boolean credentialsNonExpired;
    /**
     * The property determines if account is locked, or it isn't.
     */
    boolean accountNonLocked;
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
     * The property determines if account is enabled, or it isn't.
     */
    private boolean enabled;
    /**
     * Account's authorities.
     */
    private Set< String > authorities;
}
