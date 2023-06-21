package com.github.pplociennik.auth.business.authorization.domain.model;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import lombok.*;

import java.util.Objects;

/**
 * A domain object representing an Authority.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDO {
    private String authorityName;
    private AccountDO owner;

    @Override
    public int hashCode() {
        return Objects.hash( authorityName );
    }

    @Override
    public boolean equals( Object aO ) {
        if ( this == aO ) {
            return true;
        }
        if ( aO == null || getClass() != aO.getClass() ) {
            return false;
        }
        AuthorityDO that = ( AuthorityDO ) aO;
        return authorityName.equals( that.authorityName );
    }
}
