package com.github.pplociennik.auth.business.shared.system;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A service providing system time management. Provides functionalities for time/time zones' management and conversion.
 * The system time zone is being stored in and read from the system properties. If it is not, the default system time
 * zone is "UTC".
 *
 * @author Created by: Pplociennik at 14.08.2022 20:53
 */
public interface TimeService {

    /**
     * Returns the system time zone. The time zone should be set in the system properties. In case if it is not, the
     * method returns a default system time zone "UTC".
     *
     * @return the system time zone
     */
    ZoneId getSystemZoneId();

    /**
     * Returns current system date and time with system time zone.
     *
     * @return current zoned date time
     */
    ZonedDateTime getCurrentSystemDateTime();
}
