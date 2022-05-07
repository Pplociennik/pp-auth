package com.github.pplociennik.auth.business.authorization.domain.model;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import lombok.*;

/**
 * A domain object representing an Authority.
 *
 * @author Created by: Pplociennik at 01.07.2022 15:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class AuthorityDO {

    private String authorityName;
    private AccountDO owner;
}
