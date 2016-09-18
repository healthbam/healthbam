package org.hmhb.audit;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DefaultAuditHelper implements AuditHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuditHelper.class);

    private final HttpServletRequest request;

    @Autowired
    public DefaultAuditHelper(
            @Nonnull HttpServletRequest request
    ) {
        LOGGER.debug("constructed");
        this.request = requireNonNull(request, "request cannot be null");
    }

    @Timed
    @Override
    public String getCallerIp() {
        LOGGER.debug("getCallerIp called");
        return request.getRemoteAddr();
    }

    @Override
    public String getCurrentUser() {
        LOGGER.debug("getCurrentUser called");
        return "someone";
    }

    @Timed
    @Override
    public Date getCurrentTime() {
        LOGGER.debug("getCurrentTime called");
        return new Date();
    }

}
