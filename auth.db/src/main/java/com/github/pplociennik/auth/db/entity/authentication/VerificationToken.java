package com.github.pplociennik.auth.db.entity.authentication;

import auth.AuthVerificationTokenType;
import com.github.pplociennik.commons.persistence.BaseIdentifiableDataEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * An Entity class being the representation of the token being used for validation.
 *
 * @author Created by: Pplociennik at 01.07.2022 13:06
 */
@Getter
@Setter
@Entity
@Table(name = "AUTH_TOKENS")
public class VerificationToken extends BaseIdentifiableDataEntity {

    /**
     * Number object id.
     */
    @Id
    @GeneratedValue(generator = "tokensSeq")
    @SequenceGenerator(name = "tokensSeq", sequenceName = "AUTH_TOKENS_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    protected long id;

    /**
     * A type of the token.
     */
    @Column(name = "TOKEN_TYPE", nullable = false)
    private AuthVerificationTokenType type;

    /**
     * An owner of the token.
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "ID")
    private Account owner;

    /**
     * The value of the token.
     */
    @Column(name = "TOKEN_VALUE", nullable = false, unique = true, updatable = false)
    private String token;

    /**
     * A date and time of the token's expiration.
     */
    @Column(name = "EXPIRATION_DATETIME", nullable = false)
    private ZonedDateTime expirationDate;

    /**
     * A flag defining if the token is active or not.
     */
    @Column(name = "IS_ACTIVE")
    private boolean active;
}
