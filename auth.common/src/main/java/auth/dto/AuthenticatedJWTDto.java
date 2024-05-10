package auth.dto;

import lombok.*;

/**
 * A data transfer object representing the user authenticated in the system. Being returned to the client after successfully authentication.
 *
 * @author Created by: Pplociennik at 19.04.2023 19:40
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthenticatedJWTDto {

    private String authenticatedAccountToken;
}
