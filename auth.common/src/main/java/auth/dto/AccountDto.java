package auth.dto;

import com.github.pplociennik.commons.dto.BaseAbstractExtendableDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

/**
 * A Data Transfer Object representing data of the account object.
 *
 * @author Created by: Pplociennik at 01.07.2022 12:17
 */
@Getter
@Builder
@EqualsAndHashCode( callSuper = false )
public class AccountDto extends BaseAbstractExtendableDto {

    /**
     * An email address.
     */
    private String emailAddress;

    /**
     * Account's unique username.
     */
    private String username;

    /**
     * The property determines if account is enabled, or it isn't.
     */
    private boolean enabled;

    /**
     * The property determines if account is expired, or it isn't.
     */
    private boolean accountNonExpired;

    /**
     * The property determines if credentials are expired, or they're not.
     */
    private boolean credentialsNonExpired;

    /**
     * The property determines if account is locked, or it isn't.
     */
    private boolean accountNonLocked;

    /**
     * Account's authorities.
     */
    private Set< String > authorities;
}
