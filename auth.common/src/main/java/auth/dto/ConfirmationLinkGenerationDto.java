package auth.dto;

import com.github.pplociennik.commons.dto.BaseAbstractExtendableDto;
import lombok.*;

/**
 * A data object for transferring the data being used during the account's confirmation link generation.
 *
 * @author Created by: Pplociennik at 20.04.2023 17:59
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConfirmationLinkGenerationDto extends BaseAbstractExtendableDto {

    private String emailAddress;
}
