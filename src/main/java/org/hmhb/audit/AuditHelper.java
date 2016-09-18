package org.hmhb.audit;

import java.util.Date;

public interface AuditHelper {

    String getCallerIp();

    String getCurrentUser();

    Date getCurrentTime();

}
