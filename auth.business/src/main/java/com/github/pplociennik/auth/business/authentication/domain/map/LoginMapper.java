package com.github.pplociennik.auth.business.authentication.domain.map;

import auth.dto.AuthenticatedJWTDto;
import auth.dto.LoginDto;
import com.github.pplociennik.auth.business.authentication.domain.model.LoginDO;

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

    public static AuthenticatedJWTDto mapToAuthenticatedUserDto( String aJwt ) {
        return new AuthenticatedJWTDto( aJwt );
    }
}
