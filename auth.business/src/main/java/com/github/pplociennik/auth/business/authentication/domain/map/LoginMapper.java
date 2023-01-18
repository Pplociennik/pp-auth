package com.github.pplociennik.auth.business.authentication.domain.map;

import auth.dto.AuthenticatedUserDto;
import auth.dto.LoginDto;
import com.github.pplociennik.auth.business.authentication.domain.model.LoginDO;
import org.springframework.security.core.Authentication;

/**
 * A mapper for conversion data being used during the signing in process.
 *
 * @author Created by: Pplociennik at 26.12.2022 15:22
 */
public class LoginMapper {

    public static LoginDO mapToDomain( LoginDto aLoginDto ) {
        if ( aLoginDto == null ) {
            return null;
        }

        return new LoginDO( aLoginDto.getUsernameOrEmail(), aLoginDto.getPassword() );
    }

    public static AuthenticatedUserDto mapToAuthenticatedUserDto( Authentication aAuthenticationObject, String aSessionId ) {
        var principal = ( ( String ) aAuthenticationObject.getPrincipal() );
        var token = ( ( String ) aAuthenticationObject.getCredentials() );
        return new AuthenticatedUserDto( aSessionId, principal, token );
    }
}
