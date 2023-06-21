package com.github.pplociennik.auth.business.authentication.domain.model;

import lombok.*;

/**
 * A domain object representing user's request for the account confirmation link.
 *
 * @author Created by: Pplociennik at 21.06.2023 21:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ConfirmationLinkRequestDO {

    private String emailAddress;
}
