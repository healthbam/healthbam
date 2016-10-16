package org.hmhb.url;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultUrlService implements UrlService {

    private final HttpServletRequest request;
    private final Environment environment;

    @Autowired
    public DefaultUrlService(
            @Nonnull HttpServletRequest request,
            @Nonnull Environment environment
    ) {
        this.request = requireNonNull(request, "request cannot be null");
        this.environment = requireNonNull(environment, "environment cannot be null");
    }

    @Override
    public String getUrlPrefix() {

        String configuredUrlPrefix = environment.getProperty("hmhb.url.prefix");

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
