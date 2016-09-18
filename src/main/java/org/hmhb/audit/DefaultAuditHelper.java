package org.hmhb.audit;

import java.util.Date;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuditHelper implements AuditHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuditHelper.class);

    @Timed
    @Override
    public String getCallerIp() {
        LOGGER.debug("getCallerIp called");
        return "127.0.0.1";
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
