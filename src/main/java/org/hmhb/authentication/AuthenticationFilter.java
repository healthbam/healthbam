package org.hmhb.authentication;

import javax.annotation.Nonnull;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * A {@link Filter} to check auth headers and potentially reject an http
 * request or potentially allow & set user information.
 */
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final JwtAuthenticationService authenticationService;

    /**
     * Constructs a {@link AuthenticationFilter}.
     *
     * @param authenticationService the {@link AuthenticationService} to
     *                              delegate most of the work to
     */
    public AuthenticationFilter(
            @Nonnull JwtAuthenticationService authenticationService
    ) {
        LOGGER.debug("constructed");
        this.authenticationService = requireNonNull(authenticationService, "authenticationService cannot be null");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("init called");
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain
    ) throws IOException, ServletException {
        LOGGER.debug("doFilter called");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            authenticationService.validateAuthentication(request);
        } catch (Exception e) {
            LOGGER.error("Authentication failure!", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy called");
    }

}
