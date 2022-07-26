package com.github.pplociennik.auth.business.authentication.domain.model;

import com.github.pplociennik.auth.common.auth.AuthVerificationTokenType;
import lombok.*;

import java.time.Instant;

/**
 * A Domain Object being a representation of the verification token.
 *
 * @author Created by: Pplociennik at 01.07.2022 14:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class VerificationTokenDO {

    private long id;
    private AuthVerificationTokenType type;
    private AccountDO owner;
    private String token;
    private Instant expirationDate;
    private boolean isActive;
}
