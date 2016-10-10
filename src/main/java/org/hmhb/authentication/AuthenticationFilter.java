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

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final JwtAuthenticationService authenticationService;

    public AuthenticationFilter(
            @Nonnull JwtAuthenticationService authenticationService
    ) {
        this.authenticationService = requireNonNull(authenticationService, "authenticationService cannot be null");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init called");
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain
    ) throws IOException, ServletException {
        LOGGER.info("doFilter called");

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
        LOGGER.info("destroy called");
    }

}
