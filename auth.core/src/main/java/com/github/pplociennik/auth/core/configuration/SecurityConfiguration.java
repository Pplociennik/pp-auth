/*
 * MIT License
 *
 * Copyright (c) 2021 Przemysław Płóciennik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.pplociennik.auth.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_ADMIN_ROLE;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.core.configuration.AuthSecurityConstants.*;


/**
 * Basic Spring Security configuration class.
 *
 * @author Created by: Pplociennik at 16.09.2021 18:09
 */
@Configuration
@EnableWebSecurity
@Import( { SpringModulesConfiguration.class, AclMethodSecurityConfiguration.class } )
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( ROOT_URI, AUTH_LOGIN_URI, AUTH_LOGOUT_URI, AUTH_REGISTRATION_URI,
                              AUTH_ACCOUNT_CONFIRMATION_URI )
                .permitAll();
        http
                .authorizeRequests()
                .antMatchers( AUTH_ADMIN_URI )
                .hasRole( AUTH_ADMIN_ROLE.getName() );
        http
                .authorizeRequests()
                .antMatchers( AUTH_USER_URI )
                .hasAnyRole( AUTH_ADMIN_ROLE.getName(), AUTH_USER_ROLE.getName() );
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers( AUTH_WHITELIST )
                .permitAll()
                .antMatchers( "/**" )
                .authenticated();
    }

}
