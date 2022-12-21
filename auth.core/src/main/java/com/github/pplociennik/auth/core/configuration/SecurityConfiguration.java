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

import com.github.pplociennik.auth.business.authentication.ports.outside.AccountSecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_ADMIN_ROLE;
import static com.github.pplociennik.auth.business.shared.authorization.RolesDefinition.AUTH_USER_ROLE;
import static com.github.pplociennik.auth.core.configuration.AuthSecurityConstants.*;


/**
 * Basic Spring Security configuration class.
 *
 * @author Created by: Pplociennik at 16.09.2021 18:09
 */
@Configuration
@Import( { SpringModulesConfiguration.class, AclMethodSecurityConfiguration.class } )
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };
    private final String USERS_BY_USERNAME_QUERY = "SELECT " + String.join( ",",
                                                                            AuthSchemaConstants.AUTH_USER_TABLE_USERNAME_COLUMN_NAME,
                                                                            AuthSchemaConstants.AUTH_USER_TABLE_PASSWORD_COLUMN_NAME,
                                                                            AuthSchemaConstants.AUTH_USER_TABLE_ENABLED_COLUMN_NAME ) + " FROM " + AuthSchemaConstants.AUTH_USER_TABLE_SCHEMA_NAME + " WHERE " + AuthSchemaConstants.AUTH_USER_TABLE_USERNAME_COLUMN_NAME + " = ?";
    private final String AUTHORITIES_BY_USERNAME_QUERY = "SELECT " + String.join( ",",
                                                                                  AuthSchemaConstants.AUTH_AUTHORITY_TABLE_USERNAME_COLUMN_NAME,
                                                                                  AuthSchemaConstants.AUTH_AUTHORITY_TABLE_AUTHORITY_COLUMN_NAME ) + " FROM " + AuthSchemaConstants.AUTH_AUTHORITY_TABLE_SCHEMA_NAME + " WHERE " + AuthSchemaConstants.AUTH_AUTHORITY_TABLE_USERNAME_COLUMN_NAME + " = ?";
    private final PasswordEncoder encoder;
    private final AccountSecurityDataService accountDetailsService;
    private final UsernamePasswordAuthenticationFilter jsonAuthenticationFilter;


    @Autowired
    SecurityConfiguration(
            final PasswordEncoder aEncoder, final AccountSecurityDataService aAccountDetailsService,
            UsernamePasswordAuthenticationFilter aJsonAuthenticationFilter ) {
        encoder = aEncoder;
        accountDetailsService = aAccountDetailsService;
        jsonAuthenticationFilter = aJsonAuthenticationFilter;
    }

    /**
     * Used by the default implementation of {@link #authenticationManager()} to attempt to obtain an
     * {@link AuthenticationManager}. If overridden, the {@link AuthenticationManagerBuilder} should be used to specify
     * the {@link AuthenticationManager}.
     *
     * <p>
     * The {@link #authenticationManagerBean()} method can be used to expose the resulting {@link AuthenticationManager}
     * as a Bean. The {@link #userDetailsServiceBean()} can be used to expose the last populated
     * {@link UserDetailsService} that is created with the {@link AuthenticationManagerBuilder} as a Bean. The
     * {@link UserDetailsService} will also automatically be populated on {@link HttpSecurity#getSharedObject(Class)}
     * for use with other {@link SecurityContextConfigurer} (i.e. RememberMeConfigurer )
     * </p>
     *
     * <p>
     * For example, the following configuration could be used to register in memory authentication that exposes an in
     * memory {@link UserDetailsService}:
     * </p>
     *
     * <pre>
     * &#064;Override
     * protected void configure(AuthenticationManagerBuilder auth) {
     * 	auth
     * 	// enable in memory based authentication with a user named
     * 	// &quot;user&quot; and &quot;admin&quot;
     * 	.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;).and()
     * 			.withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
     * }
     *
     * // Expose the UserDetailsService as a Bean
     * &#064;Bean
     * &#064;Override
     * public UserDetailsService userDetailsServiceBean() throws Exception {
     * 	return super.userDetailsServiceBean();
     * }
     *
     * </pre>
     *
     * @param auth
     *         the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure( final AuthenticationManagerBuilder auth ) throws Exception {
        auth
                .userDetailsService( accountDetailsService )
                .passwordEncoder( encoder );
//                .configure( getBasicJdbcAuthenticationManagerBuilder( auth ) );

    }

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses should not invoke this method by
     * calling super as it may override their configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * Any endpoint that requires defense against common vulnerabilities can be specified here, including public ones.
     * See {@link HttpSecurity#authorizeRequests} and the `permitAll()` authorization rule for more details on public
     * endpoints.
     *
     * @param http
     *         the {@link HttpSecurity} to modify
     * @throws Exception
     *         if an error occurs
     */
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
                .addFilterBefore( jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .exceptionHandling()
                .authenticationEntryPoint( new HttpStatusEntryPoint( HttpStatus.UNAUTHORIZED ) );

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers( AUTH_WHITELIST )
                .permitAll()
                .antMatchers( "/**" )
                .authenticated();
    }

    private AuthenticationManagerBuilder getBasicJdbcAuthenticationManagerBuilder(
            final AuthenticationManagerBuilder aAuth ) throws Exception {
        aAuth
                .jdbcAuthentication()
                .authoritiesByUsernameQuery( AUTHORITIES_BY_USERNAME_QUERY )
                .usersByUsernameQuery( USERS_BY_USERNAME_QUERY );
        return aAuth;
    }

}
