package com.github.pplociennik.auth.business.authentication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * A domain object for the accounts' authentication.
 *
 * @author Created by: Pplociennik at 26.12.2022 15:16
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoginDO {

    private String usernameOrEmail;
    private String password;

    @Override
    public boolean equals( Object aO ) {
        if ( this == aO ) {
            return true;
        }
        if ( aO == null || getClass() != aO.getClass() ) {
            return false;
        }
        LoginDO loginDO = ( LoginDO ) aO;
        return Objects.equals( usernameOrEmail, loginDO.usernameOrEmail );
    }

    @Override
    public int hashCode() {
        return Objects.hash( usernameOrEmail );
    }

    @Override
    public String toString() {
        return "LoginDO{" + "usernameOrEmail='" + usernameOrEmail + '\'' + '}';
    }
}
