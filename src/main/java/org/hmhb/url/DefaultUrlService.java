package org.hmhb.url;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.hmhb.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link UrlService}.
 */
@Service
public class DefaultUrlService implements UrlService {

    private final HttpServletRequest request;
    private final ConfigService configService;

    /**
     * An injectable constructor.
     *
     * @param request the {@link HttpServletRequest} to get protocol and port
     *                info
     * @param configService the {@link org.hmhb.config.ConfigService} to get
     *                      config info
     */
    @Autowired
    public DefaultUrlService(
            @Nonnull HttpServletRequest request,
            @Nonnull ConfigService configService
    ) {
        this.request = requireNonNull(request, "request cannot be null");
        this.configService = requireNonNull(configService, "configService cannot be null");
    }

    @Timed
    @Override
    public String getUrlPrefix() {

        String configuredUrlPrefix = configService.getPublicConfig().getUrlPrefix();

        if (StringUtils.isNotBlank(configuredUrlPrefix)) {
            return configuredUrlPrefix;
        }

        String scheme = request.getScheme();
        String hostname = request.getServerName();
        int port = request.getServerPort();

        if (port != -1) {
            return scheme + "://" + hostname + ":" + port;
        } else {
            return scheme + "://" + hostname;
        }
    }

}
