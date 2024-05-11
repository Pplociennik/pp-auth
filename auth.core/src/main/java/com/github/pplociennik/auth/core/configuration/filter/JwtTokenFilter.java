package com.github.pplociennik.auth.core.configuration.filter;

import com.github.pplociennik.auth.business.authentication.domain.model.AccountDO;
import com.github.pplociennik.auth.business.authentication.ports.AccountRepository;
import com.github.pplociennik.auth.business.authorization.domain.map.AuthorityMapper;
import com.github.pplociennik.auth.business.shared.system.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * A filter for JWT validation.
 *
 * @author Created by: Pplociennik at 11.05.2024 17:08
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @Autowired
    public JwtTokenFilter( JwtService jwtService, AccountRepository accountRepository ) {
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader( HttpHeaders.AUTHORIZATION );
        if ( isEmpty( header ) || !header.startsWith( "Authentication Details" ) ) {
            filterChain.doFilter( request, response );
            return;
        }

        // Get jwt token and validate
        final String token = header.split( " " )[ 1 ].trim();
        if ( !jwtService.validateToken( token ) ) {
            filterChain.doFilter( request, response );
            return;
        }

        // Get user identity and set it on the spring security context
        AccountDO account = accountRepository
                .findAccountById( jwtService.getUserIdFromJWT( token ) );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                account.getUsername(),
                account.getPassword(),
                account == null ?
                        Set.of()
                        : AuthorityMapper.mapToAuthorityDetails( account.getAuthorities() )
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails( request )
        );

        SecurityContextHolder.getContext().setAuthentication( authentication );
        filterChain.doFilter( request, response );
    }
}
