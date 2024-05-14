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

import com.github.pplociennik.auth.core.configuration.filter.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_ADMIN_ROLE;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.core.configuration.AuthSecurityConstants.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;


/**
 * Basic Spring Security configuration class.
 *
 * @author Created by: Pplociennik at 16.09.2021 18:09
 */
@Configuration
@EnableWebSecurity
@Import( { AclMethodSecurityConfiguration.class } )
@EnableMethodSecurity( prePostEnabled = true, securedEnabled = true, jsr250Enabled = true )
class SecurityConfiguration {

    private static final String[] AUTH_WHITELIST = { EMPTY };

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // Used by Spring Security if CORS is enabled.
    @Bean
    public FilterRegistrationBean platformCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configAutenticacao = new CorsConfiguration();
        configAutenticacao.setAllowCredentials( true );
        configAutenticacao.setAllowedOriginPatterns( Collections.singletonList( "*" ) );
        configAutenticacao.addAllowedHeader( "Authorization" );
        configAutenticacao.addAllowedHeader( "Content-Type" );
        configAutenticacao.addAllowedHeader( "Accept" );
        configAutenticacao.addAllowedMethod( "POST" );
        configAutenticacao.addAllowedMethod( "GET" );
        configAutenticacao.addAllowedMethod( "DELETE" );
        configAutenticacao.addAllowedMethod( "PUT" );
        configAutenticacao.addAllowedMethod( "OPTIONS" );
        configAutenticacao.setMaxAge( 3600L );
        source.registerCorsConfiguration( "/**", configAutenticacao );

        FilterRegistrationBean bean = new FilterRegistrationBean( new CorsFilter( source ) );
        bean.setOrder( -110 );
        return bean;
    }

    @Bean
    @Order( 1 )
    SecurityFilterChain authFilterChain( HttpSecurity aHttp ) throws Exception {

        // Set session management to stateless
        aHttp.sessionManagement( ( sessionManagement ) -> sessionManagement.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );

        // Set unauthorized requests exception handler
        aHttp
                .exceptionHandling( ( exceptionHandling ) -> exceptionHandling.authenticationEntryPoint( ( request, response, ex ) -> {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                    );
                } ) );

        // Add JWT token filter
        aHttp.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return aHttp
                .securityMatcher( ROOT_URI, AUTH_LOGIN_URI, AUTH_LOGOUT_URI, AUTH_REGISTRATION_URI,
                        AUTH_ACCOUNT_CONFIRMATION_URI )
                .authorizeHttpRequests( authorize -> authorize
                        .anyRequest()
                        .permitAll() )
                .csrf( AbstractHttpConfigurer::disable )
                .build();
    }

    @Bean
    @Order( 2 )
    SecurityFilterChain adminFilterChain( HttpSecurity aHttp ) throws Exception {
        return aHttp
                .securityMatcher( AUTH_ADMIN_URI )
                .authorizeHttpRequests( authorize -> authorize
                        .anyRequest()
                        .hasRole( AUTH_ADMIN_ROLE.getName() ) )
                .csrf( AbstractHttpConfigurer::disable )
                .build();
    }

    @Bean
    @Order( 3 )
    SecurityFilterChain userFilterChain( HttpSecurity aHttp ) throws Exception {
        return aHttp
                .securityMatcher( AUTH_USER_URI )
                .authorizeHttpRequests( authorize -> authorize
                        .anyRequest()
                        .hasAnyRole( AUTH_ADMIN_ROLE.getName(), AUTH_USER_ROLE.getName() ) )
                .csrf( AbstractHttpConfigurer::disable )
                .build();
    }

    @Bean
    @Order( 4 )
    SecurityFilterChain whiteListedChain( HttpSecurity aHttp ) throws Exception {
        return aHttp
                .securityMatcher( AUTH_WHITELIST )
                .authorizeHttpRequests( authorize -> authorize
                        .anyRequest()
                        .permitAll() )
                .csrf( AbstractHttpConfigurer::disable )
                .build();
    }

    @Bean
    SecurityFilterChain defaultChain( HttpSecurity aHttp ) throws Exception {
        return aHttp
                .authorizeHttpRequests( authorize -> authorize
                        .anyRequest()
                        .authenticated() )
                .csrf( AbstractHttpConfigurer::disable )
                .build();
    }

}
