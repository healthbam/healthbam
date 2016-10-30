package org.hmhb.https;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link Filter} to try to forward requests from http to https.
 */
public class PreferHttpsFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferHttpsFilter.class);

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
            /*
             * Code inspired by:
             * https://gist.github.com/qerub/8975333
             * http://stackoverflow.com/questions/9389211/using-filters-to-redirect-from-https-to-http
             */
            URI originalUri = new URI(request.getRequestURL().toString());

            LOGGER.debug("isSecure={}, port={}, originalUri={}", request.isSecure(), originalUri.getPort(), originalUri);

            /* If the request is http and isn't local (local is using a non-standard port), redirect to https. */
            if (!request.isSecure() && originalUri.getPort() == -1) {

                URI forwardUrl = new URI(
                        "https",
                        originalUri.getUserInfo(),
                        originalUri.getHost(),
                        originalUri.getPort(),
                        originalUri.getPath(),
                        originalUri.getQuery(),
                        null
                );

                response.sendRedirect(forwardUrl.toString());
            } else {
                chain.doFilter(request, response);
            }

        } catch (URISyntaxException e) {
            throw new ServletException("Failed to construct URI!", e);
        }

    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy called");
    }

}
