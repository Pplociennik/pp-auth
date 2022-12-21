package com.github.pplociennik.auth.db.entity.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.commons.persistence.BaseIdentifiableDataEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * An Entity class being the representation of the token being used for validation.
 *
 * @author Created by: Pplociennik at 01.07.2022 13:06
 */
@Getter
@Setter
@Entity
@Table( name = "AUTH_TOKENS" )
public class VerificationToken extends BaseIdentifiableDataEntity {

    /**
     * A type of the token.
     */
    @Column( name = "TOKEN_TYPE", nullable = false )
    private AuthVerificationTokenType type;

    /**
     * An owner of the token.
     */
    @OneToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "OWNER_ID", referencedColumnName = "ID" )
    private Account owner;

    /**
     * The value of the token.
     */
    @Column( name = "TOKEN_VALUE", nullable = false, unique = true, updatable = false )
    private String token;

    /**
     * A date and time of the token's expiration.
     */
    @Column( name = "EXPIRATION_DATETIME", nullable = false )
    private ZonedDateTime expirationDate;

    /**
     * A flag defining if the token is active or not.
     */
    @Column( name = "IS_ACTIVE" )
    private boolean isActive;
}
